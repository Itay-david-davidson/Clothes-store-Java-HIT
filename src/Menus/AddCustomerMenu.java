package Menus;

public class AddCustomerMenu extends Menu {
    private static AddCustomerMenu AddCustomerMenuInstance;

    private AddCustomerMenu()
    {
        super();
    }

    public static AddCustomerMenu getInstance()
    {
        if (AddCustomerMenuInstance == null)
        {
            AddCustomerMenuInstance = new AddCustomerMenu();
        }
        return AddCustomerMenuInstance;
    }

    @Override
    public void show()
    {
        //TODO:Add customers to JSON
        System.out.println("Please enter the number corresponding to the customer you want to enter:");
        System.out.println("1. New Customer");
        System.out.println("2. Returning customer");
        System.out.println("3. VIP customer");
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
