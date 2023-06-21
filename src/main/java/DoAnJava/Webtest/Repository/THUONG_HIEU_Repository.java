package DoAnJava.Webtest.Repository;

import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.THUONG_HIEU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface THUONG_HIEU_Repository extends JpaRepository<THUONG_HIEU, Integer> {
}
