import employees.*;
import java.util.Scanner;
import Menus.MenuInit;
public class Main
{
    public static void main(String[] args) {
        //TODO: connect with server
        String address = "localhost";
        int port = 5001;
        Scanner s = new Scanner(System.in);
        Employee currEmployee = null;
        while (currEmployee == null)
        {
            System.out.println("Please enter username and password to login");
            String username = s.next();
            String password = s.next();
            //TODO: send to server to check login

        }
        MenuInit.mainMenu.show();



        s.close();
    }


}
