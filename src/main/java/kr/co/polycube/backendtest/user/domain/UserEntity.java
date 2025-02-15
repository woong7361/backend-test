package kr.co.polycube.backendtest.user.domain;

import jakarta.persistence.*;
import kr.co.polycube.backendtest.user.dto.UserCreateRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    public UserEntity(String name) {
        this.name = name;
    }

    /**
     * PUT 방식으로 user를 update한다.
     */
    public void putUpdate(UserCreateRequestDto updateRequest) {
        this.name = updateRequest.getName();
    }
}
