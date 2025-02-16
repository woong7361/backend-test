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
public class WinnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lotto_id", nullable = false)
    private LottoEntity lotto;

    @Column(nullable = false)
    private Integer rank;

    public WinnerEntity(LottoEntity lotto, Integer rank) {
        this.lotto = lotto;
        this.rank = rank;
    }
}
