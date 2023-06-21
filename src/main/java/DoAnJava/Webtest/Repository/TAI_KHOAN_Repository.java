package DoAnJava.Webtest.Repository;

import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.TAI_KHOAN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TAI_KHOAN_Repository extends JpaRepository<TAI_KHOAN, String> {
    // dung de truy van sql
    @Query("SELECT u FROM TAI_KHOAN u WHERE u.username = :username")
    public TAI_KHOAN getUserByUsername(@Param("username") String username);
}
