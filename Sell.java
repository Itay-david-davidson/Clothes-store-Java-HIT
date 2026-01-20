package Sell;
import customers.*;
import Discount.*;
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

    //Empty constructor to create a guest
    public Sell(){
        Scanner s = new Scanner(System.in);
        this.cart = new LinkedList<>();
        this.customerName = "guest";
        System.out.print("Enter your phone number: ");
        this.phone = s.nextLine();
        System.out.println();
        System.out.print("Enter your email: ");
        this.email = s.nextLine();
        System.out.println();
    }

    //The constructor is getting a customer and calculating the discount amount via the customer type
    public Sell(Customer customer){
        this.cart = new LinkedList<>();
        this.customerName = customer.getName();
        this.phone = customer.getPhoneNumber();
        this.d = new Discount();
        this.totalprice = 0;
        this.customer = customer;
    }

    public double getTotalprice(){
        return this.totalprice;
    }

    public LinkedList<CartItem> getCart(){
        return this.cart;
    }

    public void addProduct(CartItem c){
        if (c.getProduct().getQuantity() == 0){
            System.out.println("This product is out of stock!");
        }
        else {
            for (CartItem item : cart)
                if (item.getProduct().equals(c.getProduct())) {
                    item.addQuantity(1);
                    this.priceBeforDiscount += c.getProduct().getPrice();
                    System.out.println(item.getProduct().getName() + " successfully add to the cart!");
                    this.totalprice = d.calculatePriceAfterDiscount(cart, this.customer.getType());
                    return;
                }

            this.cart.add(c);
            this.priceBeforDiscount += c.getProduct().getPrice();
            this.totalprice = d.calculatePriceAfterDiscount(cart, this.customer.getType());
            System.out.println(c.getProduct().getName() + " successfully add to the cart!");
            this.totalprice = d.calculatePriceAfterDiscount(cart, this.customer.getType());
        }

    }

    //The function check if the product exist in the cart, if it exists it will remove from the cart
    public void deleteProduct(CartItem item){
        Scanner s = new Scanner(System.in);
        int remove;

        if (item.getQuantity() == 1){
            cart.remove(item);
            this.priceBeforDiscount -= this.priceBeforDiscount - item.getPrice();
            this.totalprice = d.calculatePriceAfterDiscount(cart, this.customer.getType());
            System.out.println(item.getProduct().getName() + " removed from cart!");
        }
        else if (item.getQuantity() > 1) {
            System.out.print("You have " + item.getQuantity() + " from this item, enter the quantity to remove: ");
            remove = s.nextInt();
            System.out.println();
            item.reduceQuantity(remove);

            if (item.getQuantity() == 0)
                cart.remove(item);
            this.priceBeforDiscount -= this.priceBeforDiscount - remove * item.getPrice();
        }

        this.totalprice = d.calculatePriceAfterDiscount(cart, this.customer.getType());
    }

    public void showCart(){
        double price = 0;
        System.out.println("Your Cart:");
        System.out.println("-----------");

        for (CartItem item : cart){
            System.out.println("- " + item.getProduct().getName() + "\t" + item.getQuantity() + "\t" + item.getProduct().getPrice() * item.getQuantity() +
                    "\t"+ item.getPrice());
            price += item.getProduct().getPrice();
        }
        System.out.println("Total charge before discount: " + this.priceBeforDiscount + "₪");
        System.out.println("Total to be charge after discount: " + this.totalprice + "₪");
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

