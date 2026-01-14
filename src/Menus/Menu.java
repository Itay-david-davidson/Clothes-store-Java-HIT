package Menus;

import java.util.Scanner;

/**
 * basic menu abstract class. in order to show each menu easily
 */
public abstract class Menu {

    protected static final Scanner s = new Scanner(System.in);

    public abstract void show();
}
