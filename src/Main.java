import employees.*;
import java.util.Scanner;
import Menus.MenuInit;
public class Main
{
    public static void main(String[] args) {
        String address = "localhost";
        int port = 5001;
        Scanner s = new Scanner(System.in);
        Employee currEmployee = null;
        while (currEmployee == null)
        {
            System.out.println("Please enter username and password to login");
            String username = s.next();
            String password = s.next();
            currEmployee = login(username,password);

        }
        MenuInit.mainMenu.show();



        s.close();
    }

    //TODO: add connection to JSON to check for employee and return them
    public static Employee login(String username,String password)
    {
        return null;
    }

}
