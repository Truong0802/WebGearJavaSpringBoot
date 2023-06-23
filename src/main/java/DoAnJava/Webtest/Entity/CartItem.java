package DoAnJava.Webtest.Entity;

import lombok.Data;

@Data
public class CartItem {
    // Product info
    private Integer id;
    private String name;
    private String image;
    private long price;
    private long total1price;
    // Quantity
    private int quantity;
}
