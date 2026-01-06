package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket;
    private Server server;
    private PrintWriter OutputStream;
    private String EmployeeName;

    public Client(Socket socket, Server server)
    {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            OutputStream = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = reader.readLine()) != null)
            {
                System.out.println("Client says: " + message);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}