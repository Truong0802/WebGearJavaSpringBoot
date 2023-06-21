package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
@Table(name = "THUONG_HIEU")
public class THUONG_HIEU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaTH;
    @Column(name = "TenTH")
    private String TenTH;

    @OneToMany(mappedBy = "MaTH",cascade = CascadeType.ALL)
    private Collection<SAN_PHAM> SAN_PHAMs;

}
