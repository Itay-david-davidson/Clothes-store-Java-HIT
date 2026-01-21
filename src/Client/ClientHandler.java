package Client;

import Server.Server;
import com.google.gson.Gson;
import employees.Employee;
import employees.EmployeeData;
import Services.EmployeeService; // <--- שים לב ל-Import החדש של הסרוויס שלך

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
    public final ClientActions actions;

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
                String[] command = line.split(":", 2);
                if (command.length == 1) {
                    out.println("Error: Empty command!");
                } else {
                    if (!command[0].equals("LOGIN") && !command[0].equals("CREATE_EMPLOYEE") && this.employee == null) {
                        out.println("SYSTEM: Please login first.");
                        continue;
                    }
                    if (!actions.parseAction(command[0], command[1]) && !currentChatPartners.isEmpty()) {
                        broadcastMessage(line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Connection Error");
        } finally {
            if (this.employee != null) server.removeClient(this);
            try { socket.close(); } catch (Exception _) {}
        }
    }

    public void broadcastMessage(String msg) { for(ClientHandler p : currentChatPartners) p.sendMessage(employee.getName() + ": " + msg); }
    public void sendMessage(String msg) { out.println(msg); }

    public ArrayList<ClientHandler> getCurrentChatPartners() {
        return currentChatPartners;
    }

    public PrintWriter out() {
        return out;
    }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}