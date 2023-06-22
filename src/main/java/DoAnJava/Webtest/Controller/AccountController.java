package DoAnJava.Webtest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DoAnJava.Webtest.Entity.KHACH_HANG;
import DoAnJava.Webtest.Entity.TAI_KHOAN;
import DoAnJava.Webtest.Repository.KHACH_HANG_Repository;
import DoAnJava.Webtest.Repository.TAI_KHOAN_Repository;
import DoAnJava.Webtest.Service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private KHACH_HANG_Repository khach_HANG_Repository;

    @GetMapping("/login")
    public String loginfrm() {
        return "Account/login.html";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Perform logout logic here
        // Clear any authentication related session attributes
        request.getSession().invalidate();
        // Redirect to the login page or any other desired page
        return "redirect:/?logout";
    }

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("user", new TAI_KHOAN());
        return "Account/signup";
    }

    @PostMapping("/signup/")
    public String handelRegister(Model model, @RequestParam(name = "username") String tk,
            @RequestParam(name = "password") String mk, @RequestParam(name = "fullname") String HoTen,
            @RequestParam(name = "email") String email, @RequestParam(name = "phonenumber") String phonenumber,
            @RequestParam(name = "address") String address) {
        TAI_KHOAN taikhoan = new TAI_KHOAN();
        taikhoan.setUsername(tk);
        taikhoan.setPassword(mk);
        KHACH_HANG khachhang = new KHACH_HANG();
        khachhang.setUsername(taikhoan);
        khachhang.setDiaChi(address);
        khachhang.setEmail(email);
        khachhang.setHoTenKH(HoTen);
        khachhang.setSDT(phonenumber);
        System.out.println("taikhoan:" + tk);
        System.out.println("matkhau" + mk);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(taikhoan.getPassword());
        taikhoan.setPassword(encodedPassword);
        accountService.addUserWithRole(taikhoan, khachhang);
        return "redirect:/login";
    }

    @GetMapping("/thong-tin-ca-nhan") // Thông tin khách hàng
    public String infoOfUser(Model model, @RequestParam(name = "uid") String username) {
        System.out.println("uid: " + username);
        KHACH_HANG a = accountService.infomation(username);
        System.out.println("MaKH: " + a.getMaKH());
        System.out.println("HoTenKH: " + a.getHoTenKH());
        System.out.println("tk: " + a.getUsername().getUsername());
        System.out.println("mk: " + a.getUsername().getPassword());
        model.addAttribute("infolist", accountService.infomation(username));
        return "Account/infomation.html";

    }
}
