package kr.co.polycube.backendtest.lotto.domain;

import jakarta.persistence.*;
import kr.co.polycube.backendtest.error.exception.CustomException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;

import static kr.co.polycube.backendtest.error.message.DefaultErrorMessage.LOTTO_ISSUE_ERROR;

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

    public LottoEntity(List<Integer> lottoNumbers, int size) {
        try {
            for (int i = 1; i <= size; i++) {
                Field field = this.getClass().getDeclaredField("number" + i);
                field.setAccessible(true);
                field.set(this, lottoNumbers.get(i-1));
            }
        } catch (Exception e) {
            throw new CustomException(LOTTO_ISSUE_ERROR);
        }
    }
}
