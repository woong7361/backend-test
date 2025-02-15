package kr.co.polycube.backendtest.util;

import kr.co.polycube.backendtest.user.domain.UserEntity;
import kr.co.polycube.backendtest.user.repository.UserRepository;

import java.util.Random;

public class UserUtils {

    public static UserEntity saveRandomUserBy(UserRepository userRepository) {
        UserEntity userEntity = new UserEntity(createRandomString(10));

        return userRepository.save(userEntity);
    }

    public static String createRandomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
