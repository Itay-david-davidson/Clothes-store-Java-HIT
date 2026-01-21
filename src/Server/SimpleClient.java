package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        // התחברות לשרת המקומי בפורט 5001
        try (Socket socket = new Socket("localhost", 5001);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connected to server!");

            // יצירת Thread נפרד שיקשיב להודעות מהשרת וידפיס אותן
            new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println("\n[SERVER]: " + serverMsg);
                        System.out.print("Your message: "); // סתם כדי שיהיה נוח בעין
                    }
                } catch (Exception e) {
                    System.out.println("Server disconnected.");
                }
            }).start();

            // הלופ הראשי - קורא מהמקלדת ושולח לשרת
            while (true) {
                String userInput = scanner.nextLine();
                out.println(userInput);
                if (userInput.equalsIgnoreCase("exit")) break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}