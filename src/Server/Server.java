package Server;

import Client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 5001;

    // רשימה של מי שמחובר כרגע אונליין (בשביל הצ'אט)
    private ArrayList<ClientHandler> connectedClients = new ArrayList<>();

    public static void main(String[] args) {
        new Server().runServer();
    }

    public void runServer() {
        // אין צורך לטעון דאטה בייס כאן! ה-Service טוען אותו בכל פעם שצריך.

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                // יוצרים הנדלר. הוא עדיין לא ברשימת המחוברים עד שיעשה Login
                ClientHandler newClient = new ClientHandler(socket, this);
                newClient.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- ניהול מחוברים (רק מי שאונליין) ---

    public synchronized void addConnectedClient(ClientHandler client) {
        connectedClients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        connectedClients.remove(client);
        System.out.println("Client disconnected.");
    }

    public synchronized ClientHandler findConnectedClientByUsername(String username) {
        for (ClientHandler client : connectedClients) {
            if (client.getEmployee() != null && client.getEmployee().getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }

    public synchronized ClientHandler getChatPartner(String username) {
        ClientHandler user = findConnectedClientByUsername(username);
        if (user != null && user.getEmployee().isBusy()) {
            return findConnectedClientByUsername(user.getEmployee().getCurrentChatId());
        }
        return null;
    }
}