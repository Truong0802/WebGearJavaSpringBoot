package DoAnJava.Webtest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Repository.SAN_PHAM_Repository;
import DoAnJava.Webtest.Service.ProductService;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String search(Model model, @RequestParam(name = "key") String key,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Page<SAN_PHAM> products = productService.GetProductBySearch(key, page, size);
        model.addAttribute("key", key);
        model.addAttribute("listproduct", products);
        model.addAttribute("totalPages", products.getTotalPages());
        return "Product/Search.html";
    }
}
