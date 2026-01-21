package Server; // או server (שים לב לאות גדולה/קטנה בחבילה שלך)

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Client.ClientHandler; // <--- וודא שהאימפורט הזה נכון למיקום של ClientHandler

public class Server {
    public static final int PORT = 5001;

    // רשימת הלקוחות
    private ArrayList<ClientHandler> allClients = new ArrayList<>();

    public static void main(String[] args) {
        new Server().runServer();
    }

    public void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler newClient = new ClientHandler(socket, this);
                addClient(newClient);
                newClient.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addClient(ClientHandler client) {
        allClients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        allClients.remove(client);
        System.out.println("Client removed.");
    }

    // --- הוספה 1: החזרת הרשימה (בשביל הסינון לפי סניף) ---
    public synchronized ArrayList<ClientHandler> getConnectedClients() {
        return allClients;
    }

    // --- הוספה 2: חיפוש לפי שם משתמש (ולא לפי ID) ---
    public synchronized ClientHandler findConnectedClientByUsername(String username) {
        for (ClientHandler client : allClients) {
            // בודק שהלקוח מחובר (ביצע Login) ושהשם משתמש תואם
            if (client.getEmployee() != null && client.getEmployee().getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }

    // פונקציית עזר למנהל (נשארת כמו שהיא)
    public synchronized ClientHandler getChatPartner(String username) {
        ClientHandler user = findConnectedClientByUsername(username);
        if (user != null && user.getEmployee().isBusy()) {
            String partnerUsername = user.getEmployee().getCurrentChatId();
            return findConnectedClientByUsername(partnerUsername);
        }
        return null;
    }

    // הוספתי גם את זה למקרה שאתה עדיין משתמש בזה במקומות אחרים
    public synchronized void addConnectedClient(ClientHandler client) {
        // במקרה שלך הלקוח כבר ברשימה מרגע החיבור, אז הפונקציה הזו יכולה להיות ריקה
        // או לשמש ללוגיקה נוספת של "עכשיו הוא רשמית אונליין"
        System.out.println("User " + client.getEmployee().getName() + " is now active.");
    }
}