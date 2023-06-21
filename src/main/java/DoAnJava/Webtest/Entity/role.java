package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer role_id;
    @Column
    private String name;
    /*
     * @OneToMany(mappedBy = "role_id", cascade = CascadeType.ALL)
     * private Collection<KHACH_HANG> KHACH_HANGS;
     * 
     * @OneToMany(mappedBy = "role_id", cascade = CascadeType.ALL)
     * private Collection<NHAN_VIEN> NHAN_VIENs;
     */

    // @OneToMany(mappedBy = "role_id", cascade = CascadeType.ALL)
    // private Set<TAI_KHOAN> Roles = new HashSet<>();
}
