package Client; // חבילה Client

import Server.Server; // ייבוא של השרת
import com.google.gson.Gson;
import employees.Employee;
// נמחק: import Client.ClientActions; -> לא צריך, אנחנו באותה תיקייה

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
    public static final Gson gson = new Gson();

    private ArrayList<ClientHandler> currentChatPartners = new ArrayList<>();
    public final ClientActions actions; // עכשיו הוא מכיר אותו כי הם שכנים

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.actions = new ClientActions(this, server);
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                String[] commandParts = line.split(":", 2);
                String command = commandParts[0].trim();
                String data = (commandParts.length > 1) ? commandParts[1] : "";

                if (command.isEmpty()) continue;

                // 1. בדיקת התחברות
                if (this.employee == null && !command.equals("LOGIN")) {
                    out.println("SYSTEM: Access Denied. Please Login.");
                    continue;
                }

                // 2. ניסיון ביצוע פקודה
                boolean isCommand = actions.parseAction(command, data);

                // 3. אם זו הודעה
                if (!isCommand) {
                    if (actions.isInChat()) {
                        broadcastMessage(line);
                    } else {
                        out.println("SYSTEM: Unknown command or you are not in a chat.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client Disconnected");
        } finally {
            if (this.employee != null) server.removeClient(this);
            try { socket.close(); } catch (Exception _) {}
        }
    }

    public void broadcastMessage(String msg) {
        for(ClientHandler p : currentChatPartners) {
            p.sendMessage(employee.getName() + ": " + msg);
        }
    }

    public void sendMessage(String msg) { out.println(msg); }
    public ArrayList<ClientHandler> getCurrentChatPartners() { return currentChatPartners; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
}