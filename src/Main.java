import employees.*;
import java.util.Scanner;
public class Main
{
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Employee currEmployee = null;
        while (currEmployee == null)
        {
            System.out.println("Please enter username and password to login");
            String username = s.next();
            String password = s.next();
            currEmployee = login(username,password);

        }

        System.out.println("Please enter the number corresponding to the menu you want to enter:");
        System.out.println("1. Admin screen (priviliged users only)");
        System.out.println("2. Inventory management");
        System.out.println("3. Customer management");
        System.out.println("4. Report management");
        System.out.println("5. Worker management");
        System.out.println("6. Chat management");
        System.out.println("Enter q to quit");
        char chosenSymbol = '0';
        while (chosenSymbol != 'q')
        {
            chosenSymbol = s.next().charAt(0);
            switch (chosenSymbol)
            {
                //TODO: enter rest of screens
                default -> System.out.println("Character unrecognized. Please try again:");
            }
            
        }
 
        s.close();
    }

    //TODO: add connection to JSON to check for employee and return them
    public static Employee login(String username,String password)
    {
        return null;
    }

}