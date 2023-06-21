package DoAnJava.Webtest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import DoAnJava.Webtest.Entity.LOAI_SP;
import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Service.HomeService;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private HomeService homeService;

    // @GetMapping("")
    // public String index(Model model) {
    // model.addAttribute("listproduct", homeService.GetAllProduct());
    // return "User/index.html";
    // }
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listproductBP", homeService.GetAllProductFromType(1));
        model.addAttribute("listproductCMT", homeService.GetAllProductFromType(2));
        return "User/index.html";
    }
}
