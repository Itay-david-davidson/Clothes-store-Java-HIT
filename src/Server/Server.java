package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import Client.*;

public class Server {
    private static final int PORT = 8888;
    private List<ClientHandler> m_NumberOfEmployeesInChat = new ArrayList<>();

    public void StartServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat has started on port: " + PORT);
            while(m_NumberOfEmployeesInChat.size() < 2)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Employee Joined"); 

                ClientHandler client = new ClientHandler(clientSocket, this);
                m_NumberOfEmployeesInChat.add(client);
                new Thread(client).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public synchronized void broadcast(String i_Message, ClientHandler i_Sender) {
        for(ClientHandler receiver : m_NumberOfEmployeesInChat)
        {
            if(receiver != i_Sender)
            {
                receiver.SendMessage(i_Message);
            }
        }
    }
}
