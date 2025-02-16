package kr.co.polycube.backendtest.testutil;

import kr.co.polycube.backendtest.user.domain.UserEntity;
import kr.co.polycube.backendtest.user.repository.UserRepository;

import static kr.co.polycube.backendtest.testutil.TestRandomUtils.createRandomString;

public class UserUtils {

    public static UserEntity saveRandomUserBy(UserRepository userRepository) {
        UserEntity userEntity = new UserEntity(createRandomString(10));

        return userRepository.save(userEntity);
    }
}
