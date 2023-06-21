package DoAnJava.Webtest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DoAnJava.Webtest.Entity.TAI_KHOAN;
import DoAnJava.Webtest.Repository.TAI_KHOAN_Repository;
import DoAnJava.Webtest.Service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

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
            @RequestParam(name = "password") String mk) {
        TAI_KHOAN taikhoan = new TAI_KHOAN();
        taikhoan.setUsername(tk);
        taikhoan.setPassword(mk);
        System.out.println("taikhoan:" + tk);
        System.out.println("matkhau" + mk);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(taikhoan.getPassword());
        taikhoan.setPassword(encodedPassword);
        accountService.addUserWithRole(taikhoan);
        return "redirect:/login";
    }
}
