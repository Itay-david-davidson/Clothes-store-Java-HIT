package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 5001;

    // רשימת הלקוחות
    private static ArrayList<ClientHandler> allClients = new ArrayList<>();

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

    // מוצא לקוח לפי ID (כדי לחבר שיחות)
    public synchronized static ClientHandler findClientById(String id) {
        for (ClientHandler client : allClients) {
            if (client.getEmployee() != null && client.getEmployee().getID().equals(id)) {
                return client;
            }
        }
        return null;
    }

    // עוזר למנהל למצוא את השותף לשיחה
    public synchronized ClientHandler getChatPartner(String employeeId) {
        ClientHandler user = findClientById(employeeId);
        // אם העובד קיים ועסוק, בודקים עם מי הוא מדבר (שמור ב-CurrentChatId)
        if (user != null && user.getEmployee().isBusy()) {
            String partnerID = user.getEmployee().getCurrentChatId();
            return findClientById(partnerID);
        }
        return null;
    }
}