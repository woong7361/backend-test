package kr.co.polycube.backendtest.exception.user.repository;

import kr.co.polycube.backendtest.exception.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
