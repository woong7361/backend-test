package kr.co.polycube.backendtest.lotto.service;

import kr.co.polycube.backendtest.lotto.config.LottoConfigValue;
import kr.co.polycube.backendtest.lotto.domain.LottoEntity;
import kr.co.polycube.backendtest.lotto.dto.LottoNumberResponseDto;
import kr.co.polycube.backendtest.lotto.repository.LottoRepository;
import kr.co.polycube.backendtest.util.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LottoService {
    private final LottoRepository lottoRepository;
    private final LottoConfigValue lottoConfigValue;

    /**
     * 로또 발급
     * 랜덤한 로또 숫자를 발급한다.
     */
    public LottoNumberResponseDto issueLotto() {
        List<Integer> lottoNumbers = RandomUtils.getNotDuplicateRandomNumbers(
                lottoConfigValue.getLottoSize(),
                lottoConfigValue.getLottoStartValue(),
                lottoConfigValue.getLottoEndValue()
        );

        LottoEntity lottoEntity = new LottoEntity(lottoNumbers, lottoConfigValue.getLottoSize());
        lottoRepository.save(lottoEntity);

        return LottoNumberResponseDto.of(lottoNumbers);
    }
}
