package DoAnJava.Webtest.Repository;

import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.NHAN_VIEN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NHAN_VIEN_Repository extends JpaRepository<NHAN_VIEN, Integer> {
}
