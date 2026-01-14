package Menus;

/**
 * menu to choose which employee type to add
 */
public class AddEmployeeMenu extends Menu{
    private static AddEmployeeMenu AddEmployeeMenuInstance;

    private AddEmployeeMenu()
    {
        super();
    }

    public static AddEmployeeMenu getInstance()
    {
        if (AddEmployeeMenuInstance == null)
        {
            AddEmployeeMenuInstance = new AddEmployeeMenu();
        }
        return AddEmployeeMenuInstance;
    }

    @Override
    public void show() {
        System.out.println("Please enter the number corresponding to the employee you want to add:");
        System.out.println("1. Seller employee");
        System.out.println("2. Manager employee");
        System.out.println("3. Register employee");
        System.out.println("Enter q to quit");
        char chosenSymbol = '0';
        while (chosenSymbol != 'q') {
            chosenSymbol = s.next().charAt(0);
            switch (chosenSymbol) {

            }
        }
    }
}
