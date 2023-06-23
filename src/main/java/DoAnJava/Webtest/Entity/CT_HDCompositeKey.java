package DoAnJava.Webtest.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CT_HDCompositeKey implements Serializable {
    @Column(name = "MaHD")
    String MaHD;

    @Column(name = "MaSP")
    Integer MaSP;
}
