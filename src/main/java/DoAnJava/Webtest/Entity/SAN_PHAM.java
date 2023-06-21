package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.Set;

@Data
@Entity
@Table(name = "SAN_PHAM")
public class SAN_PHAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MaSP;
    @Column(name = "TenSP", length = 70)
    private String TenSP;
    @Column(name = "SoLuong")
    private Integer SoLuong;
    @Column(name = "Gia")
    private Double Gia;
    @Column(name = "GioiThieuSP")
    private String GioiThieuSP;
    @Column(name = "HinhAnhSP")
    private String HinhAnhSP;
    @Column(name = "HinhAnhCT1")
    private String HinhAnhCT1;
    @Column(name = "HinhAnhCT2")
    private String HinhAnhCT2;
    @Column(name = "HinhAnhCT3")
    private String HinhAnhCT3;
    @Column(name = "HinhAnhCT4")
    private String HinhAnhCT4;
    @Column(name = "SLTruyCap")
    private Integer SLTruyCap;
    @Column(name = "ID")
    private Integer ID;

    @ManyToOne
    @JoinColumn(name = "MaLoai", referencedColumnName = "MaLoai")
    private LOAI_SP MaLoai;

    @ManyToOne
    @JoinColumn(name = "MaTH", referencedColumnName = "MaTH")
    private THUONG_HIEU MaTH;

    @ManyToMany(mappedBy = "likedSan_Phams")
    private Set<HOA_DON> HOA_DONs;

    // @ManyToMany(mappedBy = "CART")
    // private Set<KHACH_HANG>KHACH_HANGs;
}
