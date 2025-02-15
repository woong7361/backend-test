package kr.co.polycube.backendtest.user.dto;

import kr.co.polycube.backendtest.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCreateRequestDto {
    private String name;

    public UserEntity toEntity() {
        return new UserEntity(name);
    }
}
