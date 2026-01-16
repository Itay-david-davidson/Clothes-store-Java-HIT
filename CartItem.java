package Sell;
import Product.*;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product){
        this.product = product;
        this.quantity = 1;
    }

    public double getPrice(){
        return this.product.getPrice() * quantity;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void addQuantity(int num){
        this.quantity += num;
    }

    public void reduceQuantity(int num){
        if (this.quantity - num < 0)
            System.out.println("Error! You can't remove more than the quantity that you have!");
        else{
            this.quantity -= num;
            System.out.println("You chose to remove " + num + " products!\nThere are " + this.quantity + " quantity from the product");
        }

    }

    public Product getProduct(){
        return this.product;
    }

}
