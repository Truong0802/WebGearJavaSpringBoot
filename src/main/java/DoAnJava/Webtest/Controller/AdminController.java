package DoAnJava.Webtest.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.HOA_DON;
import DoAnJava.Webtest.Entity.KHACH_HANG;
import DoAnJava.Webtest.Entity.LOAI_SP;
import DoAnJava.Webtest.Entity.NHAN_VIEN;
import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Entity.TAI_KHOAN;
import DoAnJava.Webtest.Entity.THUONG_HIEU;
import DoAnJava.Webtest.Repository.LOAI_SP_Repository;
import DoAnJava.Webtest.Repository.THUONG_HIEU_Repository;
import DoAnJava.Webtest.Service.AdminService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin-page")
@Secured("ADMIN")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    LOAI_SP_Repository loai_SP_Repository;
    @Autowired
    THUONG_HIEU_Repository thuong_HIEU_Repository;

    @GetMapping("")
    public String TrangChuQuanLy(Model model) {
        Long total = adminService.TongDoanhThu();
        model.addAttribute("TongDoanhThu", total);
        Long tongSL = adminService.SoLuongMatHang();
        model.addAttribute("TongSLMH", tongSL);
        Long TongTC = adminService.SoLuongTruyCap();
        model.addAttribute("TongTruyCap", TongTC);
        List<HOA_DON> AllOrder = adminService.DanhSachHoaDon();
        model.addAttribute("listHD", AllOrder);
        return "Admin/admin-index.html";
    }

    @GetMapping("/danh-sach-nhan-vien")
    public String EmployAddFrm(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Page<NHAN_VIEN> list = adminService.GetAllDanhSachNhanVien(page, size);
        model.addAttribute("listNV", list);
        model.addAttribute("totalPages", list.getTotalPages());
        return "Admin/employee-admin.html";
    }

    @GetMapping("/tim-kiem-nhan-vien")
    public String SearchEmployFrm(Model model, @RequestParam(name = "key") String key) {
        NHAN_VIEN nv = adminService.findByNhanVien(key);
        model.addAttribute("listNV", nv);
        return "Admin/employee-admin-search.html";
    }

    @GetMapping("/dang-ky-nhan-vien")
    public String AddEmployFrm(Model model) {
        return "Admin/employee-add-admin.html";
    }

    @PostMapping("/dang-ky-nhan-vien")
    public String AddEmploy(@RequestParam(name = "hotennv") String hoten,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String pass, @RequestParam(name = "sdt") String sdt,
            @RequestParam(name = "diachi") String diachi, @RequestParam(name = "email") String email) {

        TAI_KHOAN tk = new TAI_KHOAN();
        tk.setUsername(username);
        tk.setPassword(pass);
        NHAN_VIEN nv = new NHAN_VIEN();
        nv.setUsername(tk);
        nv.setHoTen(hoten);
        nv.setSDT(sdt);
        nv.setDiaChi(diachi);
        nv.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(tk.getPassword());
        tk.setPassword(encodedPassword);
        adminService.addNVWithRole(tk, nv);
        return "redirect:/admin-page/danh-sach-nhan-vien";
    }

    @GetMapping("/delete-nhan-vien")
    public String DelNV(@RequestParam(name = "uid") int MaNV) {
        adminService.DelNhanVien(MaNV);
        return "redirect:/admin-page/danh-sach-nhan-vien";
    }

    @GetMapping("/danh-sach-khach-hang")
    public String DsKH(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Page<KHACH_HANG> listkh = adminService.DSKH(page, size);
        model.addAttribute("listKH", listkh);
        model.addAttribute("totalPages", listkh.getTotalPages());
        return "Admin/customers-admin.html";
    }

    @GetMapping("/thong-tin-khach-hang")
    public String KHinfo(Model model, @RequestParam(name = "id") int uid) {
        KHACH_HANG kh = adminService.findKH(uid);
        model.addAttribute("infoKH", kh);
        return "Admin/customers-admin2.html";
    }

    @GetMapping("/danh-muc-san-pham")
    public String AllCategory(Model model) {
        List<THUONG_HIEU> brand = adminService.GetAllBrand();
        List<LOAI_SP> type = adminService.GetAllType();
        model.addAttribute("BrandList", brand);
        model.addAttribute("TypeList", type);
        return "Admin/category-admin";
    }

    @GetMapping("/deletetype")
    public String DelType(@RequestParam(name = "id") int idType) {
        adminService.DelType(idType);
        return "redirect:/admin-page/danh-muc-san-pham";
    }

    @GetMapping("/deletebrand")
    public String DelBrand(@RequestParam(name = "id") int idBrand) {
        adminService.DelBrand(idBrand);
        return "redirect:/admin-page/danh-muc-san-pham";
    }

    @GetMapping("/add-new-brand")
    public String AddNewBrandFrm(Model model) {
        List<LOAI_SP> type = adminService.GetAllType();
        model.addAttribute("TypeList", type);
        return "Admin/brand-add-admin.html";
    }

    @PostMapping("/add-new-brand")
    public String AddNewBrand(@RequestParam(name = "nameth") String brandname) {
        System.out.println(brandname);
        adminService.AddNewBrand(brandname);
        return "redirect:/admin-page/danh-muc-san-pham";
    }

    @GetMapping("/add-new-type")
    public String AddNewTypeFrm() {
        return "Admin/category-add-admin.html";
    }

    @PostMapping("/add-new-type")
    public String AddNewType(@RequestParam(name = "tenloai") String nameType) {
        adminService.AddNewType(nameType);
        return "redirect:/admin-page/danh-muc-san-pham";
    }

    @GetMapping("/danh=sach-san-pham")
    public String ShowAllListProduct(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        Page<SAN_PHAM> listsp = adminService.GetAllProduct(page, size);
        model.addAttribute("listSP", listsp);
        model.addAttribute("totalPages", listsp.getTotalPages());
        return "Admin/Product/product-admin.html";
    }

    @GetMapping("/delete-product")
    public String DelProduct(@RequestParam(name = "id") int id) {
        adminService.DelSP(id);
        return "redirect:/admin-page/danh=sach-san-pham";
    }

    @GetMapping("/product-add")
    public String AddProductFrm(Model model) {
        List<LOAI_SP> listType = adminService.GetAllType();
        model.addAttribute("TypeList", listType);
        List<THUONG_HIEU> listTh = adminService.GetAllBrand();
        model.addAttribute("BrandList", listTh);
        return "Admin/Product/product-add-admin";
    }

    @PostMapping("/add-new-product")
    public String AddProduct(@RequestParam(name = "tensp") String TenSP, @RequestParam(name = "type") int typeID,
            @RequestParam(name = "brand") int brandID, @RequestParam(name = "gia") Double gia,
            @RequestParam(name = "GioithieuSP") String GioiThieuSP, @RequestParam(name = "SLton") int slt,
            @RequestParam MultipartFile imageProduct) {

        SAN_PHAM newProduct = new SAN_PHAM();
        if (TenSP != null) {
            newProduct.setTenSP(TenSP);
        }
        if (gia != null) {
            newProduct.setGia(gia);
        }
        if (typeID != 0) {
            LOAI_SP findloai = loai_SP_Repository.findById(typeID).get();
            newProduct.setMaLoai(findloai);
        }
        if (brandID != 0) {
            THUONG_HIEU findbrand = thuong_HIEU_Repository.findById(brandID).get();
            newProduct.setMaTH(findbrand);
        }
        if (GioiThieuSP != null) {
            newProduct.setGioiThieuSP(GioiThieuSP);
        }
        newProduct.setSLTruyCap(0);
        newProduct.setSoLuong(slt);
        if (imageProduct != null && imageProduct.getSize() > 0) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                String newImageFile = UUID.randomUUID() + ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setHinhAnhSP(newImageFile);
                System.out.println(newProduct.getHinhAnhSP());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        adminService.add(newProduct);
        return "redirect:/admin-page/danh=sach-san-pham";
    }

    @GetMapping("/editProduct")
    public String EditFrm(Model model, @RequestParam(name = "id") int id) {
        SAN_PHAM sp = adminService.FindSPEdit(id);
        model.addAttribute("infoSPedit", sp);
        List<LOAI_SP> listType = adminService.GetAllType();
        model.addAttribute("TypeList", listType);
        List<THUONG_HIEU> listTh = adminService.GetAllBrand();
        model.addAttribute("BrandList", listTh);
        return "Admin/Product/product-edit-admin.html";
    }

    @PostMapping("/edit-product/{id}")
    public String Edit(@PathVariable int id, SAN_PHAM editProduct,
            @RequestParam MultipartFile imageProduct) {
        editProduct.setMaSP(id);
        SAN_PHAM findimg = adminService.FindSPEdit(id);
        if (imageProduct != null && imageProduct.getSize() > 0) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                String newImageFile = UUID.randomUUID() + ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                editProduct.setHinhAnhSP(newImageFile);
                System.out.println(editProduct.getHinhAnhSP());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            editProduct.setHinhAnhSP(findimg.getHinhAnhSP());
            System.out.println(editProduct.getHinhAnhSP());
        }

        adminService.edit(editProduct);
        return "redirect:/admin-page/danh=sach-san-pham";
    }

    @GetMapping("/hoa-don")
    public String hoadon(@RequestParam(name = "id") String id, Model model) {
        HOA_DON getmahd = adminService.checkhoadon(id);
        model.addAttribute("info", getmahd);
        List<CT_HD> getlistsp = adminService.getCTHD(getmahd);
        model.addAttribute("BoughtList", getlistsp);
        Double total = getmahd.getTongHoaDon();
        model.addAttribute("totalprice", total);
        return "Admin/Product/invoice-admin.html";
    }
}
