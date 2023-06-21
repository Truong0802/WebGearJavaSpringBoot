package DoAnJava.Webtest.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "HOA_DON")
public class HOA_DON {
    @Id
    private String MaHD;
    @Column(name = "NgayLap")
    private LocalDate NgayLap;
    @Column(name = "TongHoaDon")
    private Float TongHoaDon;
    @Column(name = "TinhTrangTT", length = 50)
    private String TinhTrangTT;
    @Column(name = "TinhTrangDH", length = 50)
    private String TinhTrangDH;
    @Column(name = "Voucher", length = 50)
    private String Voucher;

    @ManyToOne
    @JoinColumn(name = "MaNV", referencedColumnName = "MaNV")
    private NHAN_VIEN MaNV;
    @ManyToOne
    @JoinColumn(name = "MaKH", referencedColumnName = "MaKH")
    private KHACH_HANG MaKH;

    @ManyToMany
    @JoinTable(name = "CT_HD", joinColumns = @JoinColumn(name = "MaHD"), inverseJoinColumns = @JoinColumn(name = "MaSP"))
    private Set<SAN_PHAM> likedSan_Phams;
}
