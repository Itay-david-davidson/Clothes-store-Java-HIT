package Server;

import com.google.gson.Gson;
import employees.Employee;
import employees.EmployeeData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;
    private Employee employee;
    private static final Gson gson = new Gson();

    // רשימת הנמענים שאני שולח להם הודעות
    private ArrayList<ClientHandler> currentChatPartners = new ArrayList<>();

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {

                // 1. התחברות (Login)
                if (line.startsWith("login:")) {
                    String json = line.substring(6);
                    EmployeeData data = gson.fromJson(json, EmployeeData.class);
                    this.employee = Employee.fromData(data);

                    System.out.println(this.employee.getName() + " connected.");
                    continue;
                }

                if (this.employee == null) continue;

                // 2. התחלת שיחה עם עובד אחר
                if (line.startsWith("START_CHAT:")) {
                    String targetId = line.substring(11);
                    startChatWith(targetId);
                    continue;
                }

                // 3. מנהל מצטרף לשיחה
                if (line.startsWith("JOIN:")) {
                    String targetId = line.substring(5);
                    joinExistingChat(targetId);
                    continue;
                }


                if (!currentChatPartners.isEmpty()) {
                    broadcastMessage(line); // <--- זה התיקון
                } else {
                    out.println("SYSTEM: You are not in a chat.");
                }
            }
        } catch (IOException e) {
            System.out.println("Connection Error");
        } finally {
            server.removeClient(this);
            try { socket.close(); } catch (Exception e) {}
        }
    }

    // --- לוגיקה פנימית ---

    private void startChatWith(String targetId) {
        ClientHandler partner = server.findClientById(targetId);

        if (partner == null) {
            out.println("SYSTEM: User not found.");
            return;
        }

        this.addToChat(partner);
        partner.addToChat(this);

        this.employee.setBusy(true);
        this.employee.setCurrentChatId(targetId);

        partner.getEmployee().setBusy(true);
        partner.getEmployee().setCurrentChatId(this.employee.getID());

        out.println("SYSTEM: Chat started.");
        partner.sendMessage("SYSTEM: Chat started with " + this.employee.getName());
    }

    private void joinExistingChat(String targetId) {
        ClientHandler user = server.findClientById(targetId);

        if (user != null && user.getEmployee().isBusy()) {
            ClientHandler otherUser = server.getChatPartner(targetId);

            if (otherUser != null) {
                this.addToChat(user);
                this.addToChat(otherUser);
                user.addToChat(this);
                otherUser.addToChat(this);

                out.println("SYSTEM: You joined the chat.");
                broadcastMessage("Manager joined the chat.");
            }
        } else {
            out.println("SYSTEM: Cannot join (User not found or not busy).");
        }
    }

    public synchronized void addToChat(ClientHandler partner) {
        if (!currentChatPartners.contains(partner)) {
            currentChatPartners.add(partner);
        }
    }

    private void broadcastMessage(String msg) {
        for (ClientHandler partner : currentChatPartners) {
            partner.sendMessage(this.employee.getName() + ": " + msg);
        }
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public Employee getEmployee() { return employee; }
}