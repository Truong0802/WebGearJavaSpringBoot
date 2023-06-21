package DoAnJava.Webtest.Repository;

import DoAnJava.Webtest.Entity.CHUC_VU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CHUC_VU_Repository extends JpaRepository<CHUC_VU, Integer> {
}
