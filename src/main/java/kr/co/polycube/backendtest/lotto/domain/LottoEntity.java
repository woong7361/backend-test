package kr.co.polycube.backendtest.lotto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LottoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_1", nullable = false)
    private Integer number1;
    @Column(name = "number_2", nullable = false)
    private Integer number2;
    @Column(name = "number_3", nullable = false)
    private Integer number3;
    @Column(name = "number_4", nullable = false)
    private Integer number4;
    @Column(name = "number_5", nullable = false)
    private Integer number5;
    @Column(name = "number_6", nullable = false)
    private Integer number6;

    public LottoEntity(List<Integer> lottoNumbers) {
        this.number1 = lottoNumbers.get(0);
        this.number2 = lottoNumbers.get(1);
        this.number3 = lottoNumbers.get(2);
        this.number4 = lottoNumbers.get(3);
        this.number5 = lottoNumbers.get(4);
        this.number6 = lottoNumbers.get(5);
    }

    /**
     * 당첨 로또번호를 받아 로또 번호의 등수를 반환한다.
     * 1등 2등 3등 4등 5등이 존재하고
     * 1등은 당첨 번호와 모두 일치, 2등은 1개를 제외하고 일치, n등은 n-1개를 제외하고 일치한다.
     *
     * 등수 제외라면 -1을 반환한다.
     */
    public Integer getRank(List<Integer> winningLottoNumbers) {
        List<Integer> lottoNumbers = List.of(
                number1, number2, number3, number4, number5, number6);

        int matchNumberCount = 0;
        HashSet<Integer> winningLottoNumberSet = new HashSet<>(winningLottoNumbers);
        for (Integer lottoNumber : lottoNumbers) {
            if (winningLottoNumberSet.contains(lottoNumber)) {
                matchNumberCount += 1;
            }
        }

        if (matchNumberCount > winningLottoNumbers.size() - 5) {
            return winningLottoNumbers.size() - matchNumberCount + 1;
        } else {
            return -1;
        }
    }
}
