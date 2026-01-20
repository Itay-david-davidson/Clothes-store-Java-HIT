package Discount;
import inventory.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PromotionDiscounts implements PromotionHandler{

   @Override
   public double calculateAutomatedDiscount(List<Product> cartItems, Promotion promo) {
       if (promo == null || !promo.enabled || promo.name == null || promo.params == null)
           return 0;
       if (cartItems == null)
           return 0;
       if (promo.name.contains("1+1") || promo.name.contains("Buy 4 pay for 2"))
           return buyNtM(cartItems, promo.params);
       else if (promo.name.contains("Second item"))
           return SecondHalfPrice(cartItems, promo.params);
       else if (promo.name.contains("Club member")) {
           ClubMemberPercentageDiscount h = new ClubMemberPercentageDiscount();
           return h.calculateAutomatedDiscount(cartItems, promo);
       }

       return 0;
   }

    @Override
    public double calculateDiscount(List<Product> cartItems, Map<String, Object> params) {
        return 0;
    }

    public double buyNtM(List<Product> cartItems, Map<String, Object> params) {
        try {
            int buyQty = ((Number) params.get("buyNtM")).intValue();
            int payQty = ((Number) params.get("payQty")).intValue();

            if (cartItems.size() < buyQty) return 0;

            int numDeals = cartItems.size() / buyQty;
            int totalFreeItems = numDeals * (buyQty - payQty);

            List<Product> sortedList = new ArrayList<>(cartItems);
            sortedList.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));

            double discount = 0;
            for (int i = 0; i < totalFreeItems; i++) {
                discount += sortedList.get(i).getPrice();
            }
            return discount;
        } catch (Exception e) { return 0; }
    }

    public double SecondHalfPrice(List<Product> cartItems, Map<String, Object> params) {
        try {
            int buyQty = 2;
            int numDeals = cartItems.size() / buyQty;
            List<Product> sortedList = new ArrayList<>(cartItems);
            sortedList.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));

            double discount = 0;
            double percentOff = ((Number) params.get("percentOff")).doubleValue();

            for (int i = 0; i < numDeals; i++) {
                discount += (sortedList.get(i).getPrice() * percentOff);
            }
            return discount;
        } catch (Exception e) { return 0; }
    }
}
