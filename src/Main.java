import Password.PasswordData;
import Password.PasswordResponse;
import employees.*;

import java.io.File;
import java.util.Scanner;
import Menus.MenuInit;

public class Main {
    public static void main(String[] args) {
        // TODO: connect with server
        String address = "localhost";
        int port = 8888;
        Scanner s = new Scanner(System.in);
        Employee currEmployee = null;
        while (currEmployee == null) {
            System.out.println("Please enter username to login");
            String username = s.next();
            System.out.println("password?");
            String password = s.next();
            Services.EmployeeService.Login(username, password);

            // PasswordData pd = new PasswordData(username, password);
            // TODO: send to server to check login

        }
        MenuInit.mainMenu.show();

        s.close();
    }

}
