package Discount;
import inventory.Product;

import java.util.*;

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

            if (buyQty <= 0 || payQty < 0 || payQty > buyQty) return 0;

            Object prodObj = params.get("products");
            if (!(prodObj instanceof List<?>))
                return 0;


            List<?> prodListRaw = (List<?>) prodObj;
            if (prodListRaw.isEmpty())
                return 0;


            java.util.HashSet<Integer> allowedIds = new java.util.HashSet<>();
            for (Object o : prodListRaw) {
                if (o instanceof Number n) allowedIds.add(n.intValue());
            }
            if (allowedIds.isEmpty()) return 0;

            List<Product> filtered = new ArrayList<>();
            for (Product p : cartItems) {
                if (allowedIds.contains(p.getBranchId())) {
                    filtered.add(p);
                }
            }

            if (filtered.size() < buyQty) return 0;

            int numDeals = filtered.size() / buyQty;
            int totalFreeItems = numDeals * (buyQty - payQty);

            List<Product> sortedList = new ArrayList<>(filtered);
            sortedList.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));

            double discount = 0;
            for (int i = 0; i < totalFreeItems; i++) {
                discount += sortedList.get(i).getPrice();
            }
            return discount;

        } catch (Exception e) {
            return 0;
        }
    }

    public double SecondHalfPrice(List<Product> cartItems, Map<String, Object> params) {
        try {
            double percentOff = ((Number) params.get("percentOff")).doubleValue();
            if (percentOff < 0 || percentOff > 1) return 0;

            Object prodObj = params.get("products");
            if (!(prodObj instanceof List<?>))
                return 0;

            List<?> prodListRaw = (List<?>) prodObj;
            if (prodListRaw.isEmpty())
                return 0;

            java.util.HashSet<Integer> allowedIds = new java.util.HashSet<>();
            for (Object o : prodListRaw)
                if (o instanceof Number n) allowedIds.add(n.intValue());

            if (allowedIds.isEmpty())
                return 0;

            List<Product> filtered = new ArrayList<>();
            for (Product p : cartItems)
                if (allowedIds.contains(p.getBranchId()))
                    filtered.add(p);


            if (filtered.size() < 2)
                return 0;

            List<Product> sorted = new ArrayList<>(filtered);
            sorted.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));

            double discount = 0;
            for (int i = 1; i < sorted.size(); i += 2)
                discount += sorted.get(i).getPrice() * percentOff;

            return discount;
        } catch (Exception e) {
            return 0;
        }
    }
}
