package kr.co.polycube.backendtest.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.polycube.backendtest.error.exception.DataNotFoundException;
import kr.co.polycube.backendtest.error.message.DefaultErrorMessage;
import kr.co.polycube.backendtest.testutil.TestRandomUtils;
import kr.co.polycube.backendtest.testutil.UserUtils;
import kr.co.polycube.backendtest.user.domain.UserEntity;
import kr.co.polycube.backendtest.user.dto.UserCreateRequestDto;
import kr.co.polycube.backendtest.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    public void clearUserData() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("사용자 등록")
    public class RegisterUser {
        String CREATE_USER_URI = "/users";

        @DisplayName("정상 등록")
        @Test
        public void success() throws Exception {
            //given
            String name = "name";
            UserCreateRequestDto createRequest = new UserCreateRequestDto(name);

            //when
            //then
            mockMvc.perform(
                            MockMvcRequestBuilders.post(CREATE_USER_URI)
                                    .content(objectMapper.writeValueAsString(createRequest))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("id").exists());
        }

        /**
         * HttpMessageNotReadableException 발생 필요하다면 advice에 적용
         */
        @DisplayName("비어있는 body")
        @Test
        public void nullInput() throws Exception {
            //given
            //when
            //then
            mockMvc.perform(
                            MockMvcRequestBuilders.post(CREATE_USER_URI)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(result ->
                            Assertions.assertThat(result.getResolvedException())
                                    .isInstanceOf(HttpMessageNotReadableException.class))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }

        /**
         * DataIntegrityViolationException 발생 필요하다면 advice에 적용
         */
        @DisplayName("잘못된 json 입력")
        @Test
        public void invalidInput() throws Exception {
            //given
            UserCreateRequestDto invalidInput = new UserCreateRequestDto(null);

            //when
            //then
            mockMvc.perform(
                            MockMvcRequestBuilders.post(CREATE_USER_URI)
                                    .content(objectMapper.writeValueAsString(invalidInput))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(result ->
                            Assertions.assertThat(result.getResolvedException())
                                    .isInstanceOf(DataIntegrityViolationException.class))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        }


    }


    @Nested
    @DisplayName("사용자 조회")
    public class GetUser {
        String GET_USER_URL = "/users/{id}";
        @DisplayName("정상 조회")
        @Test
        public void success() throws Exception {
            //given
            UserEntity targetUser = UserUtils.saveRandomUserBy(userRepository);

            //when
            //then
            mockMvc.perform(MockMvcRequestBuilders.get(GET_USER_URL, targetUser.getId()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(targetUser.getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(targetUser.getName()));
        }

        @DisplayName("존재하지 않는 회원 조회시 400 에러")
        @Test
        public void notExistUser() throws Exception {
            //given
            int notExistUserId = 100;
            //when
            //then
            mockMvc.perform(MockMvcRequestBuilders.get(GET_USER_URL, notExistUserId))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("reason")
                            .value(DefaultErrorMessage.USER_NOT_FOUND.getMessage()))
                    .andExpect(result -> Assertions.assertThat(result.getResolvedException())
                            .isInstanceOf(DataNotFoundException.class));
        }
    }

    @Nested
    @DisplayName("사용자 수정")
    public class UpdateUser {
        String UPDATE_USER_URL = "/users/{id}";
        @DisplayName("정상 수정")
        @Test
        public void success() throws Exception {
            //given
            UserEntity targetUser = UserUtils.saveRandomUserBy(userRepository);
            UserCreateRequestDto updateRequest = new UserCreateRequestDto(TestRandomUtils.createRandomString(10));

            //when
            //then
            mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_USER_URL, targetUser.getId())
                            .content(objectMapper.writeValueAsString(updateRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(targetUser.getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updateRequest.getName()));
        }

        @DisplayName("존재하지 않는 회원 조회시 400 에러")
        @Test
        public void notExistUser() throws Exception {
            //given
            int notExistUserId = 100;
            UserCreateRequestDto updateRequest = new UserCreateRequestDto(TestRandomUtils.createRandomString(10));

            //when
            //then
            mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_USER_URL, notExistUserId)
                            .content(objectMapper.writeValueAsString(updateRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("reason")
                            .value(DefaultErrorMessage.USER_NOT_FOUND.getMessage()))
                    .andExpect(result -> Assertions.assertThat(result.getResolvedException())
                            .isInstanceOf(DataNotFoundException.class));
        }

        @DisplayName("잘못된 입력값")
        @Test
        public void invalidInput() throws Exception {
            //given
            UserEntity targetUser = UserUtils.saveRandomUserBy(userRepository);
            UserCreateRequestDto updateRequest = new UserCreateRequestDto(null);

            //when
            //then
            mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_USER_URL, targetUser.getId())
                            .content(objectMapper.writeValueAsString(updateRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(result ->
                            Assertions.assertThat(result.getResolvedException())
                                    .isInstanceOf(DataIntegrityViolationException.class))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }

    }

}