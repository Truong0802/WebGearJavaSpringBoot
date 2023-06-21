package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Collection;

@Data
@Entity
@Table(name="CHUC_VU")
public class CHUC_VU {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaCV ;
    @Column(length = 50)
    @NotBlank(message ="CV không đc để trống")
    private String ChucVu;

    @OneToMany(mappedBy = "MaCV",cascade = CascadeType.ALL)
    private Collection<NHAN_VIEN> nhan_viens;
}
