package kr.co.polycube.backendtest.lotto.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class LottoConfigValue {
    /**
     * 로또 숫자의 크기
     */
    @Value("${lotto.size}")
    private int lottoSize;

    /**
     * 가장 작은 로또의 수
     */
    @Value("${lotto.start-value}")
    private int lottoStartValue;

    /**
     * 가장 큰 로또의 수
     */
    @Value("${lotto.end-value}")
    private int lottoEndValue;


    /**
     * 가장 큰 로또의 수
     */
    @Value("${lotto.schedule.draw-lotto}")
    private String drawLottoJob;

}
