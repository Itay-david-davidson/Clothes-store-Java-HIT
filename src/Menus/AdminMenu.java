package Menus;

//TODO: difference between this and employee menu?
/**
 * Admin menu, to choose which employee to add, view or delete
 */
public class AdminMenu extends Menu{
    private static AdminMenu adminMenuInstance;

    private AdminMenu()
    {
        super();
    }

    public static AdminMenu getInstance()
    {
        if (adminMenuInstance == null)
        {
            adminMenuInstance = new AdminMenu();
        }
        return adminMenuInstance;
    }
    @Override
    public void show()
    {
        System.out.println("Please enter the number corresponding to the action you want to do:");
        System.out.println("1. Add employee");
        System.out.println("2. View employee");
        System.out.println("3. Delete employee");
        System.out.println("Enter q to quit");
        char chosenSymbol = '0';
        while (chosenSymbol != 'q')
        {
            chosenSymbol = s.next().charAt(0);
            switch (chosenSymbol)
            {
                case '6' ->
                {
                }
                //TODO: enter rest of screens
                default -> System.out.println("Character unrecognized. Please try again:");
            }

        }
    }
}
