package kr.co.polycube.backendtest.exception.user.service;

import kr.co.polycube.backendtest.exception.user.domain.UserEntity;
import kr.co.polycube.backendtest.exception.user.dto.UserCreateRequestDto;
import kr.co.polycube.backendtest.exception.user.dto.UserCreateResponseDto;
import kr.co.polycube.backendtest.exception.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
