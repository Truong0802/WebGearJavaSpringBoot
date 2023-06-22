package DoAnJava.Webtest.Repository;

import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.KHACH_HANG;
import DoAnJava.Webtest.Entity.TAI_KHOAN;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KHACH_HANG_Repository extends JpaRepository<KHACH_HANG, Integer> {
}
