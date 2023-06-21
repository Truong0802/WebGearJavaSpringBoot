package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.time.LocalDate;

@Entity
public class CT_HD {

    @EmbeddedId
    private CT_HDCompositeKey ID;

    @ManyToOne
    @MapsId("MaHD")
    @JoinColumn(name = "MaHD")
    HOA_DON MaHD;

    @ManyToOne
    @MapsId("MaSP")
    @JoinColumn(name = "MaSP")
    SAN_PHAM MaSP;

    @Column(name = "SoLuong")
    private Integer SoLuong;
    @Column(name = "TongGia")
    private Double TongGia;
    @Column(name = "NgayMua")
    @Valid()
    private LocalDate NgayMua;

}
