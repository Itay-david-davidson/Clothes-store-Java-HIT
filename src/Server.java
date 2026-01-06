package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server
{
    private static final int PORT = 1111;

    private HashMap<String, String> m_activeEmplyees = new HashMap<>();

    private Queue<String> m_waitingQueue = new LinkedList<>();

    private List<String> m_availableEmpolyees = new ArrayList<>();


    public void StartServer()
    {
        try(ServerSocket serxverSocket = new ServerSocket(PORT))
        {
            System.out.println("Chat has started on port: " + PORT);
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(clientSocket, this);
                new Thread(client).start();
            }

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean IsEmployeeAlreadyInTheServer(String i_EmployeeName, String i_ComputerID)
    {
        if(m_activeEmployees.containsKey(i_EmployeeName))
        {
            System.out.println("Employee is already logged in from another computer");
            return false;
        }
        m_activeEmployees.put(i_EmployeeName, i_ComputerID);
        return true;
    }


    public void notifyEmployeeAvailable(String i_Employee)
    {
        if(!m_waitingQueue.isEmpty())
        {
            String NextEmployee = m_waitingQueue.poll();
            System.out.println(i_Employee + " Employee: " + NextEmployee + "is waiting");
        }
        else m_availableEmpolyees.add(i_Employee);
    }

}



