package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Table(name = "LOAI_SP")
public class LOAI_SP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaLoai;
    @Column(name = "TenLoai")
    private String TenLoai;

    @OneToMany(mappedBy = "MaLoai",cascade = CascadeType.ALL)
    private Collection<SAN_PHAM> SAN_PHAMs;
}
