package kr.co.polycube.backendtest.lotto.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class LottoConfigValue {
    /**
     * 로또 숫자 크기
     */
    @Value("${lotto.size}")
    private int lottoSize;

    /**
     * 가장 작은 로또 번호
     */
    @Value("${lotto.start-value}")
    private int lottoStartValue;

    /**
     * 가장 큰 로또 번호
     */
    @Value("${lotto.end-value}")
    private int lottoEndValue;

    /**
     * draw lotto job name
     */
    @Value("${lotto.schedule.draw-lotto.name}")
    private String drawLottoJob;

    /**
     * draw lotto 청크 크기
     */
    @Value("${lotto.schedule.draw-lotto.chunk-size}")
    private int chunkSize;

    /**
     * draw lotto 청크 크기
     */
    @Value("${lotto.schedule.draw-lotto.page-size}")
    private int pageSize;

}
