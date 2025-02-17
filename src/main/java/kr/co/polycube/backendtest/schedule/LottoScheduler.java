package kr.co.polycube.backendtest.schedule;

import kr.co.polycube.backendtest.lotto.config.LottoConfigValue;
import kr.co.polycube.backendtest.util.RandomUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LottoScheduler {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;
    private final LottoConfigValue lottoConfigValue;

    /**
     * 매주 일요일마다 로또 번호 추첨후 등수 저장
     */
    @Scheduled(cron = "${lotto.schedule.draw-lotto.cron}")
    public void drawLotto() throws Exception {
        String winningLottoNumbers = getJoinedLottoNumbersString();
        String dateTime = LocalDateTime.now().toString();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("attr", winningLottoNumbers)
                .addString("dateTime", dateTime)
                .toJobParameters();

        log.info("winning lotto number is : {} , time: {}", winningLottoNumbers, dateTime);

        jobLauncher.run(jobRegistry.getJob(lottoConfigValue.getDrawLottoJob()), jobParameters);

    }

    private String getJoinedLottoNumbersString() {
        List<String> winningLottoNumbers = RandomUtils.getNotDuplicateRandomNumbers(
                        lottoConfigValue.getLottoSize(),
                        lottoConfigValue.getLottoStartValue(),
                        lottoConfigValue.getLottoEndValue())
                .stream()
                .map(String::valueOf)
                .toList();

        return String.join(" ", winningLottoNumbers);
    }
}
