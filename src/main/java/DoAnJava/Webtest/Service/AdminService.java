package DoAnJava.Webtest.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import DoAnJava.Webtest.Entity.CHUC_VU;
import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.CT_HDCompositeKey;
import DoAnJava.Webtest.Entity.HOA_DON;
import DoAnJava.Webtest.Entity.KHACH_HANG;
import DoAnJava.Webtest.Entity.LOAI_SP;
import DoAnJava.Webtest.Entity.NHAN_VIEN;
import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Entity.TAI_KHOAN;
import DoAnJava.Webtest.Entity.THUONG_HIEU;
import DoAnJava.Webtest.Entity.role;
import DoAnJava.Webtest.Repository.CT_HD_Repository;
import DoAnJava.Webtest.Repository.HOA_DON_Repository;
import DoAnJava.Webtest.Repository.KHACH_HANG_Repository;
import DoAnJava.Webtest.Repository.LOAI_SP_Repository;
import DoAnJava.Webtest.Repository.NHAN_VIEN_Repository;
import DoAnJava.Webtest.Repository.SAN_PHAM_Repository;
import DoAnJava.Webtest.Repository.TAI_KHOAN_Repository;
import DoAnJava.Webtest.Repository.THUONG_HIEU_Repository;
import DoAnJava.Webtest.Repository.role_Repository;
import jakarta.transaction.Transactional;

@Service
public class AdminService {
    @Autowired
    HOA_DON_Repository hoa_DON_Repository;
    @Autowired
    SAN_PHAM_Repository san_PHAM_Repository;
    @Autowired
    NHAN_VIEN_Repository nhan_VIEN_Repository;
    @Autowired
    TAI_KHOAN_Repository tai_KHOAN_Repository;
    @Autowired
    role_Repository role_Repository;
    @Autowired
    KHACH_HANG_Repository khach_HANG_Repository;
    @Autowired
    THUONG_HIEU_Repository thuong_HIEU_Repository;
    @Autowired
    LOAI_SP_Repository loai_SP_Repository;
    @Autowired
    CT_HD_Repository ct_HD_Repository;

    public Long TongDoanhThu() {
        Double total = hoa_DON_Repository.findAll().stream()
                .mapToDouble(x -> x.getTongHoaDon()).sum();
        Long totalToLong = Double.valueOf(total).longValue();
        return totalToLong;
    }

    public Long SoLuongMatHang() {
        Long tongSP = san_PHAM_Repository.findAll().stream().mapToInt(s -> s.getMaSP()).count();
        return tongSP;
    }

    public Long SoLuongTruyCap() {
        Long TongTruyCap = san_PHAM_Repository.findAll().stream().mapToLong(l -> l.getSLTruyCap()).sum();
        return TongTruyCap;
    }

    public List<HOA_DON> DanhSachHoaDon() {
        List<HOA_DON> list = hoa_DON_Repository.findAll();
        List<HOA_DON> limitedOrder = new ArrayList<>();
        for (int i = 0; i < 10 && i < list.size(); i++) {
            limitedOrder.add(list.get(i));
        }
        return limitedOrder;
    }

    public Page<NHAN_VIEN> GetAllDanhSachNhanVien(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<NHAN_VIEN> liste = nhan_VIEN_Repository.findAll(pageRequest);
        return liste;
    }

    public NHAN_VIEN findByNhanVien(String key) {
        NHAN_VIEN nvsearch = nhan_VIEN_Repository.findAll().stream()
                .filter(p -> p.getHoTen().toLowerCase().contains(key.toLowerCase())).findFirst().orElseThrow();
        return nvsearch;
    }

    public void add(TAI_KHOAN newUser) {
        tai_KHOAN_Repository.save(newUser);
    }

    @Transactional
    public void addNVWithRole(TAI_KHOAN user, NHAN_VIEN NV) {
        TAI_KHOAN savedUser = tai_KHOAN_Repository.save(user);

        // Tìm role có ID là 1
        role role = role_Repository.findById(2).orElseThrow(() -> new RuntimeException("Role not found"));
        savedUser.getRoles().add(role);
        tai_KHOAN_Repository.save(savedUser);

        nhan_VIEN_Repository.save(NV);
    }

    public void DelNhanVien(int id) {
        NHAN_VIEN nv = nhan_VIEN_Repository.findById(id).get();
        TAI_KHOAN tk = tai_KHOAN_Repository.findAll().stream()
                .filter(x -> x.getUsername().equals(nv.getUsername().getUsername()))
                .findFirst().orElseThrow();
        tai_KHOAN_Repository.deleteById(tk.getUsername());
    }

    public Page<KHACH_HANG> DSKH(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return (Page<KHACH_HANG>) khach_HANG_Repository.findAll(pageRequest);
    }

    public KHACH_HANG findKH(int uid) {
        return (KHACH_HANG) khach_HANG_Repository.findAll().stream()
                .filter(u -> u.getMaKH() == uid)
                .findFirst().orElseThrow(null);
    }

    public List<THUONG_HIEU> GetAllBrand() {
        return (List<THUONG_HIEU>) thuong_HIEU_Repository.findAll();
    }

    public List<LOAI_SP> GetAllType() {
        return (List<LOAI_SP>) loai_SP_Repository.findAll();
    }

    public void DelType(int id) {
        loai_SP_Repository.deleteById(id);
    }

    public void DelBrand(int id) {
        thuong_HIEU_Repository.deleteById(id);
    }

    public void AddNewBrand(String BrandName) {
        THUONG_HIEU th = new THUONG_HIEU();
        th.setTenTH(BrandName);
        thuong_HIEU_Repository.save(th);
    }

    public void AddNewType(String TypeName) {
        LOAI_SP th = new LOAI_SP();
        th.setTenLoai(TypeName);
        loai_SP_Repository.save(th);
    }

    public Page<SAN_PHAM> GetAllProduct(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<SAN_PHAM> liste = san_PHAM_Repository.findAll(pageRequest);
        return liste;
    }

    public void DelSP(int id) {
        san_PHAM_Repository.deleteById(id);
    }

    public void add(SAN_PHAM sp) {
        san_PHAM_Repository.save(sp);
    }

    public SAN_PHAM FindSPEdit(int sp) {
        return (SAN_PHAM) san_PHAM_Repository.findById(sp).stream().findFirst().orElse(null);
    }

    public void edit(SAN_PHAM editProduct) {
        SAN_PHAM find = FindSPEdit(editProduct.getMaSP());
        if (find != null) {
            /// or implement clon()
            find.setTenSP(editProduct.getTenSP());
            find.setGia(editProduct.getGia());
            find.setGioiThieuSP(editProduct.getGioiThieuSP());
            find.setMaLoai(editProduct.getMaLoai());
            find.setMaTH(editProduct.getMaTH());
            find.setSoLuong(editProduct.getSoLuong());
            find.setHinhAnhSP(editProduct.getHinhAnhSP());
            san_PHAM_Repository.save(find);
        }
    }

    public HOA_DON checkhoadon(String id) {
        return hoa_DON_Repository.findAll().stream().filter(x -> x.getMaHD().equals(id)).findFirst().orElseThrow();
    }

    public List<CT_HD> getCTHD(HOA_DON maHD) {
        List<CT_HD> listCT = ct_HD_Repository.findAll().stream()
                .filter(x -> x.getID().getMaHD().equals(maHD.getMaHD())).toList();
        return listCT;
    }
}
