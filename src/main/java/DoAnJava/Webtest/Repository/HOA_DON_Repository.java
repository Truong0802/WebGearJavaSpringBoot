package DoAnJava.Webtest.Repository;

import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.HOA_DON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HOA_DON_Repository extends JpaRepository<HOA_DON, Integer> {
}
