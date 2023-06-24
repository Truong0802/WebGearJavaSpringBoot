package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity
@Table(name = "NHAN_VIEN")
public class NHAN_VIEN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaNV;

    @Column(name = "HoTen", length = 30)
    @Size(max = 30)
    private String HoTen;
    @Column(name = "SDT", length = 10)
    private String SDT;
    @Column(name = "DiaChi", length = 100)
    private String DiaChi;
    @Column(name = "Email", length = 50)
    private String Email;

    // @ManyToOne
    // @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    // private role role_id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private TAI_KHOAN username;
    @ManyToOne
    @JoinColumn(name = "MaCV", referencedColumnName = "MaCV")
    private CHUC_VU MaCV;

}
