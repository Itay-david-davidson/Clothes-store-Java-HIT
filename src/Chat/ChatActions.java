package Chat;

import java.util.*;

public class ChatActions {

    private HashMap<String, String> m_activeEmployees = new HashMap<>();

    private Queue<String> m_waitingQueue = new LinkedList<>();

    private List<String> m_availableEmpolyees = new ArrayList<>();

    public boolean IsEmployeeAlreadyInTheServer(String i_EmployeeName, String i_ComputerID) {
        if (m_activeEmployees.containsKey(i_EmployeeName)) {
            System.out.println("Employee is already logged in from another computer");
            return false;
        }
        m_activeEmployees.put(i_EmployeeName, i_ComputerID);
        return true;
    }

    public void notifyEmployeeAvailable(String i_Employee) {
        if (!m_waitingQueue.isEmpty()) {
            String NextEmployee = m_waitingQueue.poll();
            System.out.println(i_Employee + " Employee: " + NextEmployee + "is waiting");
        } else m_availableEmpolyees.add(i_Employee);
    }
}
