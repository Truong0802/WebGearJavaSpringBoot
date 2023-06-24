package DoAnJava.Webtest.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import DoAnJava.Webtest.Entity.CT_HD;
import DoAnJava.Webtest.Entity.CT_HDCompositeKey;
import DoAnJava.Webtest.Entity.CartItem;
import DoAnJava.Webtest.Entity.HOA_DON;
import DoAnJava.Webtest.Entity.KHACH_HANG;
import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Repository.CT_HD_Repository;
import DoAnJava.Webtest.Repository.HOA_DON_Repository;
import DoAnJava.Webtest.Repository.SAN_PHAM_Repository;
import jakarta.transaction.Transactional;

@Service
@SessionScope
public class CartService {
    @Autowired
    SAN_PHAM_Repository san_PHAM_Repository;
    @Autowired
    CT_HD_Repository ct_HD_Repository;

    private List<CartItem> cartItems = new ArrayList<>();

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void removeFromCart(int productId) {
        cartItems.removeIf(cartItem -> cartItem.getId().equals(productId));
    }

    public void addToCart(SAN_PHAM product, int quantity) {
        CartItem findCart = cartItems.stream()
                .filter(item -> item.getId().equals(product.getMaSP()))
                .findFirst().orElse(null);
        SAN_PHAM productcheckSL = san_PHAM_Repository.findById(product.getMaSP()).stream().findFirst().orElse(null);
        if (findCart != null) {
            if (findCart.getQuantity() < productcheckSL.getSoLuong()) {
                findCart.setQuantity(findCart.getQuantity() + 1);
            }
            findCart.setTotal1price(findCart.getPrice() * findCart.getQuantity());
        } else {
            System.out.print("case item = null");
            findCart = new CartItem();
            findCart.setQuantity(quantity);
            findCart.setId(product.getMaSP());
            findCart.setName(product.getTenSP());
            findCart.setImage(product.getHinhAnhSP());
            findCart.setPrice(Double.valueOf(product.getGia()).longValue());
            findCart.setTotal1price(findCart.getPrice() * findCart.getQuantity());
            cartItems.add(findCart);
        }
    }

    public void updateCartItem(int productId, int quantity) {
        CartItem findCart = cartItems.stream()
                .filter(item -> item.getId().equals(productId))
                .findFirst().orElse(null);
        SAN_PHAM productcheckSL = san_PHAM_Repository.findAll().stream()
                .filter(x -> x.getTenSP().toLowerCase().contains(findCart.getName().toLowerCase()))
                .findFirst().orElse(null);
        if (findCart != null) {
            if (findCart.getQuantity() < productcheckSL.getSoLuong() && quantity > 0) {
                findCart.setQuantity(quantity);
                findCart.setTotal1price(findCart.getPrice() * findCart.getQuantity());
            } else {
                if (quantity == 0) {
                    int id = productcheckSL.getMaSP();
                    removeFromCart(id);
                }

            }
        }
    }

    @Autowired
    private HOA_DON_Repository hoa_DON_Repository;

    @Transactional
    public void orderCart(KHACH_HANG user) {
        // Create a new Order
        HOA_DON order = new HOA_DON();
        LocalDate now = LocalDate.now();
        LocalDateTime dtnow = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String mahdConvert = user.getUsername().getUsername() + dtnow.format(dateFormatter);
        order.setMaHD(mahdConvert);
        order.setNgayLap(now);
        String begin = "Đang chuẩn bị";
        order.setTinhTrangDH(begin);
        String waitingPayment = "Chưa thanh toán";
        order.setTinhTrangTT(waitingPayment);
        order.setMaKH(user);
        // Iterate over cart items and create OrderDetails
        List<CT_HD> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            CT_HD orderDetail = new CT_HD();
            CT_HDCompositeKey compositeKey = new CT_HDCompositeKey();
            SAN_PHAM sp = san_PHAM_Repository.findAll().stream()
                    .filter(x -> x.getTenSP().toLowerCase().contains(cartItem.getName().toLowerCase())).findFirst()
                    .orElse(null);
            if (sp.getSLTruyCap() == null) {
                sp.setSLTruyCap(0 + 1);
            } else {
                sp.setSLTruyCap(sp.getSLTruyCap() + 1);
            }

            sp.setSoLuong(sp.getSoLuong() - cartItem.getQuantity());
            compositeKey.setMaHD(order.getMaHD());
            compositeKey.setMaSP(sp.getMaSP());
            orderDetail.setID(compositeKey);
            orderDetail.setMaHD(order);
            orderDetail.setMaSP(sp);
            orderDetail.setSoLuong(cartItem.getQuantity());
            double converted = (double) cartItem.getPrice();
            orderDetail.setTongGia(converted);
            orderDetail.setNgayMua(now);
            orderDetails.add(orderDetail);
            Long totalPrice = cartItems.stream()
                    .mapToLong(x -> x.getPrice() * x.getQuantity())
                    .sum();
            double convertTotal = (double) totalPrice;
            order.setTongHoaDon(convertTotal);
        }
        // Save the order to the database
        hoa_DON_Repository.save(order);

        // Set order details in the order
        for (CT_HD ct_HD : orderDetails) {
            ct_HD_Repository.save(ct_HD);
        }
        // Clear the cart
        clearCart();
    }

    public List<CT_HD> getlistcthd(KHACH_HANG user) {
        LocalDateTime dtnow = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String mahdConvert = user.getUsername().getUsername() + dtnow.format(dateFormatter);
        List<CT_HD> list = ct_HD_Repository.findAll().stream()
                .filter(x -> x.getID().getMaHD().equals(mahdConvert)).toList();
        return list;
    }
}