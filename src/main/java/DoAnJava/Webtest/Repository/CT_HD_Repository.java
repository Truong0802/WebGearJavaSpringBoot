package DoAnJava.Webtest.Repository;

import DoAnJava.Webtest.Entity.CHUC_VU;
import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.CT_HDCompositeKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CT_HD_Repository extends JpaRepository<CT_HD, CT_HDCompositeKey> {
}
