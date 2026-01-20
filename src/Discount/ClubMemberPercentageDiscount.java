package Discount;
import inventory.Product;
import java.util.List;
import java.util.Map;

public class ClubMemberPercentageDiscount implements PromotionHandler{

    @Override
    public double calculateAutomatedDiscount(List<Product> cartItems, Promotion promo){
        double discount;

        if (promo == null || !promo.enabled || promo.params == null)
            return 0;
        else
            discount = calculateDiscount(cartItems, promo.params);

        return discount;
    }

    @Override
    public double calculateDiscount(List<Product> cartItems, Map<String, Object> params) {
        try {

            boolean isClubMember = (boolean) params.getOrDefault("isClubMember", false);

            if (!isClubMember)
                return 0;


            double discountRate = ((Number) params.get("discountRate")).doubleValue();

            double totalAmount = 0;
            for (Product item : cartItems) {
                totalAmount += item.getPrice();
            }

            return totalAmount * discountRate;

        } catch (NullPointerException | ClassCastException e) {
            return 0;
        }
    }
}
