package Discount;

import inventory.Product;
import java.util.List;
import java.util.Map;

public interface PromotionHandler {

        public double calculateDiscount(List<Product> cartItems, Map<String, Object> params);
        public double calculateAutomatedDiscount(List<Product> cartItems, Promotion promo);
}
