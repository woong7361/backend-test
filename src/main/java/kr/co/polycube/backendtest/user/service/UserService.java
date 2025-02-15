package kr.co.polycube.backendtest.user.service;

import kr.co.polycube.backendtest.error.exception.DataNotFoundException;
import kr.co.polycube.backendtest.user.domain.UserEntity;
import kr.co.polycube.backendtest.user.dto.UserCreateRequestDto;
import kr.co.polycube.backendtest.user.dto.UserCreateResponseDto;
import kr.co.polycube.backendtest.user.dto.UserDetailResponseDto;
import kr.co.polycube.backendtest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.polycube.backendtest.error.message.DefaultErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    /**
     * 사용자 등록
     */
    @Transactional
    public UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {
        UserEntity savedUser = userRepository.save(userCreateRequestDto.toEntity());
        log.debug("user save success, user id: {}", savedUser.getId());

        return UserCreateResponseDto.from(savedUser);
    }

    /**
     * 사용자 조회
     */
    public UserDetailResponseDto getUser(Long id) {
        UserEntity findUser = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND, id));

        return UserDetailResponseDto.from(findUser);
    }

    /**
     * 사용자 수정
     * put 방식 update로 유저 생성과 동일한 dto를 받아 id를 제외한 모든 파라미터를 바꾼다.
     */
    @Transactional
    public UserDetailResponseDto updateUser(UserCreateRequestDto updateRequest, Long id) {
        UserEntity findUser = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND, id));

        findUser.putUpdate(updateRequest);

        return UserDetailResponseDto.from(findUser);
    }
}
