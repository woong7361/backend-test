package kr.co.polycube.backendtest.lotto.controller;

import kr.co.polycube.backendtest.lotto.config.LottoConfigValue;
import kr.co.polycube.backendtest.lotto.repository.LottoRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class LottoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LottoConfigValue lottoConfigValue;

    @Autowired
    LottoRepository lottoRepository;

    @AfterEach
    public void clearLottoData() {
        lottoRepository.deleteAll();
    }

    @Nested
    @DisplayName("로또 발급")
    public class IssueLottoTest {

        final String LOTTO_ISSUE_URI = "/lottos";
        @DisplayName("정상 발급")
        @Test
        public void success() throws Exception {
            //given
            //when
            //then
            mockMvc.perform(MockMvcRequestBuilders.post(LOTTO_ISSUE_URI))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.numbers").isArray())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.numbers",
                            Matchers.hasSize(lottoConfigValue.getLottoSize())));
        }
    }
}