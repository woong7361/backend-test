package kr.co.polycube.backendtest.error.advice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static kr.co.polycube.backendtest.error.message.DefaultErrorMessage.REQUEST_URL_NOT_FOUND;


@SpringBootTest
@AutoConfigureMockMvc
class DefaultControllerAdviceTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("존재하지 않는 URL 탐색시 404 에러")
    @Test
    public void notExistUrl() throws Exception{
        //given
         String notExistUrl = "/not/exist/url";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(notExistUrl))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("reason").value(REQUEST_URL_NOT_FOUND.getMessage()));
    }

    @DisplayName("존재하지만 잘못된 HTTP METHOD 접근시")
    @Test
    public void notExistHttpMethod() throws Exception{
        //given
        String existUrl = "/users";

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.patch(existUrl))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("reason").value(REQUEST_URL_NOT_FOUND.getMessage()));
    }

}