package kr.co.polycube.backendtest.lotto.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;


class LottoEntityTest {

    @Nested
    @DisplayName("로또 등수 반환 테스트")
    public class GetRankTest {
        @DisplayName("등수가 존재할때")
        @Test
        public void existRank() throws Exception {
            //given
            List<Integer> winningLottoNumbers = List.of(1, 2, 3, 4, 5, 6);
            LottoEntity rank1Lottos = new LottoEntity(List.of(1, 2, 3, 4, 5, 6));
            LottoEntity rank2Lottos = new LottoEntity(List.of(1, 2, 3, 4, 5, 100));
            LottoEntity rank3Lottos = new LottoEntity(List.of(1, 2, 3, 4, 100, 200));
            LottoEntity rank4Lottos = new LottoEntity(List.of(1, 2, 3, 100, 200, 300));
            LottoEntity rank5Lottos = new LottoEntity(List.of(1, 2, 100, 200, 300, 400));

            //when
            Integer rank1 = rank1Lottos.getRank(winningLottoNumbers);
            Integer rank2 = rank2Lottos.getRank(winningLottoNumbers);
            Integer rank3 = rank3Lottos.getRank(winningLottoNumbers);
            Integer rank4 = rank4Lottos.getRank(winningLottoNumbers);
            Integer rank5 = rank5Lottos.getRank(winningLottoNumbers);
            //then

            Assertions.assertThat(rank1).isEqualTo(1);
            Assertions.assertThat(rank2).isEqualTo(2);
            Assertions.assertThat(rank3).isEqualTo(3);
            Assertions.assertThat(rank4).isEqualTo(4);
            Assertions.assertThat(rank5).isEqualTo(5);
        }

        /**
         * 5등 이후로는 등수가 존재하지 않는다.
         * 6개 기준 1개 이하로 일치하는 경우 등수가 존재하지 않는다.
         */
        @DisplayName("등수가 존재하지 않을때")
        @Test
        public void notExistRank() throws Exception {
            //given
            List<Integer> winningLottoNumbers = List.of(1, 2, 3, 4, 5, 6);
            LottoEntity notRanked1 = new LottoEntity(List.of(1, 200, 300, 400, 500, 600));
            LottoEntity notRanked2 = new LottoEntity(List.of(100, 200, 300, 400, 500, 600));

            //when
            Integer rank1 = notRanked1.getRank(winningLottoNumbers);
            Integer rank2 = notRanked2.getRank(winningLottoNumbers);
            //then

            Assertions.assertThat(rank1).isEqualTo(-1);
            Assertions.assertThat(rank2).isEqualTo(-1);
        }
    }
}