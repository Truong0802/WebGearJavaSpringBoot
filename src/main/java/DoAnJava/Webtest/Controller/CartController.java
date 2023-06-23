package DoAnJava.Webtest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.CartItem;
import DoAnJava.Webtest.Entity.KHACH_HANG;
import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Entity.TAI_KHOAN;
import DoAnJava.Webtest.Repository.SAN_PHAM_Repository;
import DoAnJava.Webtest.Service.AccountService;
import DoAnJava.Webtest.Service.CartService;
import DoAnJava.Webtest.Service.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private SAN_PHAM_Repository san_PHAM_Repository;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountService accountService;

    @GetMapping("")
    public String getCartItems(Model model, HttpSession session) {
        List<CartItem> cartItems = cartService.getCartItems();
        model.addAttribute("cartItems", cartItems);
        // Calculate the total price
        Long totalPrice = cartItems.stream()
                .mapToLong(cartItem -> cartItem.getPrice() * cartItem.getQuantity())
                .sum();
        model.addAttribute("totalPrice", totalPrice);
        // Calculate cart count
        session.setAttribute("cartCount", cartItems.size());
        return "Product/cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable("id") int productId, @RequestParam(name = "quantity") int quantity) {
        SAN_PHAM product = productService.get(productId);
        if (product != null) {
            if (quantity <= product.getSoLuong()) {

            } else {
                quantity = product.getSoLuong();
            }
            cartService.addToCart(product, quantity);
        }
        return "redirect:/cart";
    }

    @GetMapping("/clear/{id}")
    public String deletefromCart(@PathVariable("id") int productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("idCart") int cartId,
            @RequestParam("quantityCart") int quantity) {
        System.out.println(cartId);
        cartService.updateCartItem(cartId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/payment")
    public String Order(Model model, HttpSession session) {
        // Get the currently logged-in user
        List<CartItem> cartItems = cartService.getCartItems();
        Long totalPrice = cartItems.stream()
                .mapToLong(cartItem -> cartItem.getPrice() * cartItem.getQuantity())
                .sum();
        model.addAttribute("totalPrice", totalPrice);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // TAI_KHOAN user = (TAI_KHOAN) authentication.getName();
        KHACH_HANG findUser = accountService.infomation(authentication.getName());
        cartService.orderCart(findUser);
        // Redirect to a success page or return a success message
        List<CT_HD> list = cartService.getlistcthd(findUser);
        for (CT_HD ct_HD : list) {
            ct_HD.getID().getMaHD();
        }
        model.addAttribute("cartItems", list);
        model.addAttribute("infoCustomer", findUser);
        session.setAttribute("cartCount", cartItems.size());
        return "Pay/payment.html";
    }

    @GetMapping("/complete-phase")
    public String HoanThanhDatHang() {
        return "Pay/paysuccess.html";
    }
}
