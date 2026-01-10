package Menus;

import java.util.Scanner;

public abstract class Menu {

    //TODO: for every subclass, implement scanner better
    protected static final Scanner s = new Scanner(System.in);

    public abstract void show();
}
