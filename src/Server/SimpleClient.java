package server; // או Client, תלוי איפה שמרת אותו

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

            // שינוי 1: ניסוח מדויק יותר - מחוברים לרשת, אבל צריך להתחבר למערכת
            System.out.println("--- Network Connected ---");
            System.out.println("Please enter LOGIN command to access the system.");

            // יצירת Thread נפרד שיקשיב להודעות מהשרת וידפיס אותן
            new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = in.readLine()) != null) {
                        // הדפסה נקייה של הודעת השרת בלי תוספות מיותרות
                        System.out.println("\n[SERVER]: " + serverMsg);

                        // שינוי 2: מחקתי מפה את השורה: System.out.print("Your message: ");
                        // זה מה שעשה לך את הבלגן במסך. עכשיו זה נקי.
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
            System.out.println("Could not connect to server (Is it running?).");
        }
    }
}