package kr.co.polycube.backendtest.lotto.controller;

import kr.co.polycube.backendtest.lotto.dto.LottoNumberResponseDto;
import kr.co.polycube.backendtest.lotto.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LottoController {

    private final LottoService lottoService;

    /**
     * 로또 발급
     * 랜덤한 6개의 로또 숫자를 반환한다.
     */
    @PostMapping("/lottos")
    public ResponseEntity<LottoNumberResponseDto> issueLotto() {
        LottoNumberResponseDto response = lottoService.issueLotto();

        return ResponseEntity.ok(response);
    }
}
