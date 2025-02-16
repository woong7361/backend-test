package kr.co.polycube.backendtest.schedule;

import kr.co.polycube.backendtest.lotto.domain.LottoEntity;
import kr.co.polycube.backendtest.lotto.domain.WinnerEntity;
import kr.co.polycube.backendtest.lotto.repository.LottoRepository;
import kr.co.polycube.backendtest.lotto.repository.WinnerRepository;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest(
        properties = {
                "lotto.schedule.cron=0/30 * * * * ?",
                "lotto.start-value=1",
                "lotto.end-value=6"
        }
)
class LottoSchedulerTest {
    @Autowired
    LottoRepository lottoRepository;
    @Autowired
    WinnerRepository winnerRepository;
    @Autowired
    LottoScheduler lottoScheduler;

    @AfterEach
    public void clearLottoData() {
        winnerRepository.deleteAll();
        lottoRepository.deleteAll();
    }

    /**
     * lotto.start-value=1, lotto.end-value=6 테스트 설정값이 이므로
     * 당첨 로또 번호는 항상 [1, 2, 3, 4, 5, 6]이다.
     */
    @Nested
    @DisplayName("Lotto 스케줄링 테스트")
    public class LottoScheduleTest {
        @DisplayName("스케줄링 테스트")
        @Test
        public void success() throws Exception {
            //given
            LottoEntity rank1 = lottoRepository.save(new LottoEntity(List.of(1, 2, 3, 4, 5, 6)));
            LottoEntity rank2 = lottoRepository.save(new LottoEntity(List.of(2, 3, 4, 5, 6, 7)));
            LottoEntity rank3 = lottoRepository.save(new LottoEntity(List.of(3, 4, 5, 6, 7, 8)));
            LottoEntity rank5 = lottoRepository.save(new LottoEntity(List.of(5, 6, 7, 8, 9, 10)));
            LottoEntity notRanked = lottoRepository.save(new LottoEntity(List.of(100, 200, 300, 400, 500, 600)));

            //when
            Awaitility.await()
                    .untilAsserted(() -> lottoScheduler.drawLotto());

            //then
            List<WinnerEntity> winners = winnerRepository.findAll();

            Assertions.assertThat(winners.size()).isEqualTo(4);
            Assertions.assertThat(winners.stream().filter(w -> w.getRank().equals(1)).count()).isEqualTo(1L);
            Assertions.assertThat(winners.stream().filter(w -> w.getRank().equals(2)).count()).isEqualTo(1L);
            Assertions.assertThat(winners.stream().filter(w -> w.getRank().equals(3)).count()).isEqualTo(1L);
            Assertions.assertThat(winners.stream().filter(w -> w.getRank().equals(4)).count()).isEqualTo(0L);
            Assertions.assertThat(winners.stream().filter(w -> w.getRank().equals(5)).count()).isEqualTo(1L);
        }
    }

}