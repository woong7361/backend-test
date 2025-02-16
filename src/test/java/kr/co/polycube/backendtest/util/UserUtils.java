package kr.co.polycube.backendtest.util;

import kr.co.polycube.backendtest.user.domain.UserEntity;
import kr.co.polycube.backendtest.user.repository.UserRepository;

import java.util.Random;

import static kr.co.polycube.backendtest.util.RandomUtils.createRandomString;

public class UserUtils {

    public static UserEntity saveRandomUserBy(UserRepository userRepository) {
        UserEntity userEntity = new UserEntity(createRandomString(10));

        return userRepository.save(userEntity);
    }
}
