package Discount;
import Sell.CartItem;
import com.google.gson.Gson;
import inventory.Product;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Discount {
    private DiscountConfig disc;
    private final String path = "data/discounts.json";

    public Discount(){
        loadConfig(path);
    }

    public void loadConfig(String filePath) {
        try (Reader reader = new java.io.InputStreamReader(
                java.util.Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath)))) {
            Gson gson = new Gson();
            this.disc = gson.fromJson(reader, DiscountConfig.class);
            System.out.println("Configuration loaded successfully!");
        } catch (Exception e) {
            System.out.println("Error loading JSON: " + e.getMessage());
            this.disc = null;
        }
    }

    public double calculatePriceAfterDiscount(List<CartItem> cart, String customerType) {
        if (this.disc == null)
            return 0;

        double originalPrice = 0;
        List<Product> flattenedItems = new ArrayList<>();

        for (CartItem item : cart) {
            originalPrice += item.getProduct().getPrice() * item.getQuantity();
            for (int i = 0; i < item.getQuantity(); i++) {
                flattenedItems.add(item.getProduct());
            }
        }

        double totalDiscount = 0;

        if (disc.customerDiscounts != null && customerType != null) {
            CustomerDiscounts policy = disc.customerDiscounts.get(customerType);
            if (policy != null && policy.enabled) {
                totalDiscount += originalPrice * policy.percentage;
            }
        }

        // Promotions
        PromotionDiscounts promoHandler = new PromotionDiscounts();
        if (disc.promotions != null) {
            for (Promotion promo : disc.promotions) {
                if (promo != null && promo.enabled) {
                    totalDiscount += promoHandler.calculateAutomatedDiscount(flattenedItems, promo);
                }
            }
        }

        return Math.max(0, originalPrice - totalDiscount);
    }
}
