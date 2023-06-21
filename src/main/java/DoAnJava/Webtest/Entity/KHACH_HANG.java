package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "KHACH_HANG")
public class KHACH_HANG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaKH;

    @Column(name = "HoTen", length = 30)
    @Size(max = 30)
    private String HoTenKH;
    @Column(name = "SDT", length = 10)
    private String SDT;
    @Column(name = "DiaChi", length = 100)
    private String DiaChi;
    @Column(name = "Email", length = 50)
    private String Email;

    /*
     * @ManyToOne
     * 
     * @JoinColumn(name = "role_id", referencedColumnName = "role_id")
     * private role role_id;
     */

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private TAI_KHOAN username;

    // @ManyToMany
    // @JoinTable(name = "CART",joinColumns = @JoinColumn(name =
    // "MaKH"),inverseJoinColumns = @JoinColumn(name = "MaSP"))
    // private Set<SAN_PHAM> likedSan_Phams;
}
