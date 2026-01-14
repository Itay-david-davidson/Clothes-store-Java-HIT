package Menus;


/**
 * main menu. in order to get to all other menus and actions
 */
public class MainMenu extends Menu {

    private static MainMenu mainMenuInstance;

    private MainMenu()
    {
        super();
    }

    public static MainMenu getInstance()
    {
        if (mainMenuInstance == null)
        {
            mainMenuInstance = new MainMenu();
        }
        return mainMenuInstance;
    }

    @Override
    public void show() {
        System.out.println("Please enter the number corresponding to the menu you want to enter:");
        System.out.println("1. Admin screen (priviliged users only)");
        System.out.println("2. Inventory management");
        System.out.println("3. Customer management");
        System.out.println("4. Report management");
        System.out.println("5. Worker management");
        System.out.println("6. Chat management");
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
