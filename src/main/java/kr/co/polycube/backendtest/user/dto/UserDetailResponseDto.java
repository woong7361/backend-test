package kr.co.polycube.backendtest.user.dto;

import kr.co.polycube.backendtest.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDetailResponseDto {
    private Long id;
    private String name;

    public static UserDetailResponseDto from(UserEntity userEntity) {
        return new UserDetailResponseDto(userEntity.getId(), userEntity.getName());
    }
}
