package kr.co.polycube.backendtest.user.dto;

import kr.co.polycube.backendtest.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCreateResponseDto {
    private Long id;

    public static UserCreateResponseDto from(UserEntity userEntity) {
        return new UserCreateResponseDto(userEntity.getId());
    }
}
