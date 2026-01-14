package Menus;

/**
 * Customer menu, to choose which customer to add or view
 */
public class CustomerMenu extends Menu{
    private static CustomerMenu customerMenuInstance;

    private CustomerMenu()
    {
        super();
    }

    public static CustomerMenu getInstance()
    {
        if (customerMenuInstance == null)
        {
            customerMenuInstance = new CustomerMenu();
        }
        return customerMenuInstance;
    }

    @Override
    public void show()
    {
        System.out.println("Please enter the number corresponding to the action you want to do:");
        System.out.println("1. Add customer");
        System.out.println("2. View customer");
        System.out.println("Enter q to quit");
        char chosenSymbol = '0';
        while (chosenSymbol != 'q')
        {
            chosenSymbol = s.next().charAt(0);
            switch (chosenSymbol)
            {
                case '1' ->
                {

                }
                //TODO: enter rest of screens
                default -> System.out.println("Character unrecognized. Please try again:");
            }

        }
    }
}
