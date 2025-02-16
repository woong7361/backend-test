package kr.co.polycube.backendtest.filter;

import kr.co.polycube.backendtest.error.exception.CustomException;
import kr.co.polycube.backendtest.error.message.DefaultErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static kr.co.polycube.backendtest.util.RandomUtils.createRandomString;

@SpringBootTest
@AutoConfigureMockMvc
class InvalidUrIFilterTest {
    @Autowired
    MockMvc mockMvc;

    @Nested
    @DisplayName("INVALID URL 필터")
    public class InvalidUrlFilterTest {

        @DisplayName("올바르지 못한 URL 입력시 400 반환")
        @ParameterizedTest
        @ValueSource(strings = {
                "/users/3?name=test!!",
                "/invalid/@#/url",
                "/invalid@#/!@url@#?name=test",
        })
        public void success(String arg) throws Exception {
            //given
            //when
            //then
            mockMvc.perform(MockMvcRequestBuilders.get(arg))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("reason")
                            .value(DefaultErrorMessage.INVALID_URL.getMessage()));
        }
    }
}