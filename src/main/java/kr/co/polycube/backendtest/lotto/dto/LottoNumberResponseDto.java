package kr.co.polycube.backendtest.lotto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class LottoNumberResponseDto {
    private List<Integer> numbers;

    public static LottoNumberResponseDto of(List<Integer> lottoNumbers) {
        return new LottoNumberResponseDto(lottoNumbers);
    }
}
