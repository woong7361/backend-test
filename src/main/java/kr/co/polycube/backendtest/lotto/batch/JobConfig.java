package kr.co.polycube.backendtest.lotto.batch;

import kr.co.polycube.backendtest.lotto.config.LottoConfigValue;
import kr.co.polycube.backendtest.lotto.domain.LottoEntity;
import kr.co.polycube.backendtest.lotto.domain.WinnerEntity;
import kr.co.polycube.backendtest.lotto.repository.LottoRepository;
import kr.co.polycube.backendtest.lotto.repository.WinnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final LottoRepository lottoRepository;
    private final WinnerRepository winnerRepository;
    private final LottoConfigValue lottoConfigValue;

    @Bean
    public Job drawLottoJob() {
        return new JobBuilder(lottoConfigValue.getDrawLottoJob(), jobRepository)
                .start(firstStep())
                .build();
    }

    @Bean
    @JobScope
    public Step firstStep() {
        return new StepBuilder("firstStep", jobRepository)
                .<LottoEntity, WinnerEntity>chunk(lottoConfigValue.getChunkSize(), platformTransactionManager)
                .reader(lottoReader())
                .processor(middleProcessor(null))
                .writer(winnerWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<LottoEntity> lottoReader() {
        log.debug("read issued lotto data");
        return new RepositoryItemReaderBuilder<LottoEntity>()
                .name("lottoReader")
                .pageSize(lottoConfigValue.getPageSize())
                .methodName("findAll")
                .repository(lottoRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }


    @Bean
    @StepScope
    public ItemProcessor<LottoEntity, WinnerEntity> middleProcessor(@Value("#{jobParameters[attr]}") String attr) {
        List<Integer> winningLottoNumbers = Arrays.stream(attr.split(" "))
                .map(Integer::parseInt)
                .toList();

        return new ItemProcessor<LottoEntity, WinnerEntity>() {
            @Override
            public WinnerEntity process(LottoEntity item) throws Exception {
                Integer rank = item.getRank(winningLottoNumbers);
                if (rank.equals(-1)) {
                    return null;
                }

                return  new WinnerEntity(item, rank);
            }
        };
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<WinnerEntity> winnerWriter() {
        log.debug("write lotto winner data");
        return new RepositoryItemWriterBuilder<WinnerEntity>()
                .repository(winnerRepository)
                .methodName("save")
                .build();
    }

}
