package kr.co.polycube.backendtest.user.controller;

import kr.co.polycube.backendtest.user.dto.UserCreateRequestDto;
import kr.co.polycube.backendtest.user.dto.UserCreateResponseDto;
import kr.co.polycube.backendtest.user.dto.UserDetailResponseDto;
import kr.co.polycube.backendtest.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 사용자 조회 엔드포인트
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetailResponseDto> getUser(@PathVariable Long id) {
        UserDetailResponseDto response = userService.getUser(id);

        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 수정 엔드포인트
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDetailResponseDto> updateUser(
            @RequestBody UserCreateRequestDto updateRequest,
            @PathVariable Long id) {
        UserDetailResponseDto response = userService.updateUser(updateRequest, id);

        return ResponseEntity.ok(response);
    }
}
