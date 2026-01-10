package Menus;

public class EmployeeMenu extends Menu{
    private static EmployeeMenu employeeMenuInstance;

    private EmployeeMenu()
    {
        super();
    }

    public static EmployeeMenu getInstance()
    {
        if (employeeMenuInstance == null)
        {
            employeeMenuInstance = new EmployeeMenu();
        }
        return employeeMenuInstance;
    }

    @Override
    public void show()
    {
        System.out.println("Please enter the number corresponding to the action you want to do:");
        System.out.println("1. Add employee");
        System.out.println("2. View enployee");
        System.out.println("3. Delete employee");
        System.out.println("Enter q to quit");
        char chosenSymbol = '0';
        while (chosenSymbol != 'q') {
            chosenSymbol = s.next().charAt(0);
            switch (chosenSymbol) {


                //TODO: enter rest of screens
                default -> System.out.println("Character unrecognized. Please try again:");

            }
        }
    }
}
