package kr.co.polycube.backendtest.lotto.repository;

import kr.co.polycube.backendtest.lotto.domain.LottoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LottoRepository extends JpaRepository<LottoEntity, Long> {
}
