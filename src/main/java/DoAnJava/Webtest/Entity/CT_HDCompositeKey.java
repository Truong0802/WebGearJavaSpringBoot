package DoAnJava.Webtest.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CT_HDCompositeKey implements Serializable {
    @Column(name = "MaHD")
    Integer MaHD;

    @Column(name = "MaSP")
    Integer MaSP;
}
