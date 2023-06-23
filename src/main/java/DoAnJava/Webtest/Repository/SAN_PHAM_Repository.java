package DoAnJava.Webtest.Repository;

import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.LOAI_SP;
import DoAnJava.Webtest.Entity.SAN_PHAM;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SAN_PHAM_Repository extends JpaRepository<SAN_PHAM, Integer> {
}
