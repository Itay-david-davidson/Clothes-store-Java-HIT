package Sell;
import customers.*;
import Discount.*;
import inventory.Product;
import java.util.LinkedList;
import java.util.Scanner;


/*
This class handles  the sells of products for customers.
It is adding to the cart and calculate the discount to the customer.
 */

public class Sell {
    private double totalprice, priceBeforDiscount, discout;  //The discount set by the customer's type, we are getting the customer in the constructor
    private LinkedList<CartItem> cart;
    private String customerName, phone, email;
    private Discount d;
    private Customer customer;

    //The constructor is getting a customer and calculating the discount amount via the customer type
    public Sell(Customer customer){
        this.cart = new LinkedList<>();
        this.customerName = customer.getName();
        this.phone = customer.getPhoneNumber();
        this.d = new Discount();
        this.totalprice = 0;
        this.customer = customer;
    }

    public Sell(){
        this.cart = new LinkedList<>();
        this.customerName = "Guest";
        this.phone = "No Phone";
        this.d = new Discount();
        this.totalprice = 0;
        this.customer = null;
    }

    private CartItem CreateCartItem(Product product, int quantity){
        CartItem cartItem = new CartItem(product, quantity);
        return cartItem;
    }

    public double getTotalprice(){
        return this.totalprice;
    }

    public LinkedList<CartItem> getCart(){
        return this.cart;
    }

    public void addProduct(Product product, int quantity){
        if (quantity <= 0)
            return;

        if (product.getQuantity() < quantity) {
            System.out.println("Not enough stock!");
            return;
        }

        for (CartItem item : cart) {
            if (item.getProduct().equals(product)) {
                item.addQuantity(quantity);
                this.priceBeforDiscount += product.getPrice() * quantity;

                System.out.println(product.getName() + " successfully added to the cart!");
                return;
            }
        }

        CartItem ci = new CartItem(product, quantity);
        this.cart.add(ci);
        this.priceBeforDiscount += product.getPrice() * quantity;
        System.out.println(product.getName() + " successfully added to the cart!");

    }

    //The function check if the product exist in the cart, if it exists it will remove from the cart
    public void deleteProduct(Product product){
        Scanner s = new Scanner(System.in);
        int remove;

        CartItem item = null;
        for (CartItem i : cart)
            if (product.getBranchId() == i.getProduct().getBranchId()) {
                item = i;
                break;
            }

        if (item.getQuantity() == 1){
            cart.remove(item);
            this.priceBeforDiscount -= item.getProduct().getPrice();

            this.totalprice = d.calculatePriceAfterDiscount(
                    cart,
                    (this.customer != null ? this.customer.getType() : null)
            );

            System.out.println(item.getProduct().getName() + " removed from cart!");
        }
        else if (item.getQuantity() > 1) {
            System.out.print("You have " + item.getQuantity() + " from this item, enter the quantity to remove: ");
            remove = s.nextInt();
            System.out.println();

            boolean ok = item.reduceQuantity(remove);
            if (ok) {
                if (item.getQuantity() == 0)
                    cart.remove(item);

                this.priceBeforDiscount -= remove * item.getProduct().getPrice();
                System.out.println("The product deleted");
            }
        }
    }

    public void showCart(){
        double price = 0;
        System.out.println("Your Cart:");
        System.out.println("-----------");

        System.out.printf("%-15s | %-5s | %-10s | %-10s%n", "Product", "Qty", "Price", "Total");
        for (CartItem item : cart) {
            System.out.printf("- %-13s | %-5d | %-10.2f | %-10.2f%n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice(),
                    item.getPrice());
        }

        System.out.println("----------------------------------------------------------");
        String type = this.customer != null ? this.customer.getType() : null;
        System.out.println("Total charge before discount: " + this.priceBeforDiscount + "₪");
        System.out.println("Total to be charge after discount: " + d.calculatePriceAfterDiscount(cart, type) + "₪");
    }

    //This function is selling the product,
    public void sell(){
        Scanner s = new Scanner(System.in);
        String payment, creditNumber, cvv, expiration;
        System.out.print("How would you like to pay " + this.totalprice + "₪ " + "(Credit Card, cash): ");
        payment = s.nextLine();
        System.out.println();

        if (payment.equalsIgnoreCase("Credit Card")){
            System.out.print("Enter the full number of your credit card: ");
            creditNumber = s.nextLine();
            System.out.println();
            System.out.print("Enter the expiration date: ");
            expiration = s.nextLine();
            System.out.println();
            System.out.print("Enter the CVV number: ");
            cvv = s.nextLine();

            System.out.print("Credit card information received!");
        }
        else{
            System.out.println("Cash received!");
        }

        for (CartItem item : cart){
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        System.out.println("Thanks you for shopping!");
    }
}
