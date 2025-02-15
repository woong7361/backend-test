package kr.co.polycube.backendtest.exception.user.controller;

import kr.co.polycube.backendtest.exception.user.dto.UserCreateRequestDto;
import kr.co.polycube.backendtest.exception.user.dto.UserCreateResponseDto;
import kr.co.polycube.backendtest.exception.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 사용자 등록 엔드포인트
     */
    @PostMapping("/users")
    public ResponseEntity<UserCreateResponseDto> createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        UserCreateResponseDto response = userService.createUser(userCreateRequestDto);

        return ResponseEntity.ok(response);
    }
}
