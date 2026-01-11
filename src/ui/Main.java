package ui;

import domain.users.*;
import domain.customers.*;
import repository.EmployeeRepository;
import repository.CustomerRepository;
import service.EmployeeService;
import service.CustomerService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("===== תחילת בדיקת מערכת ניהול רשת חנויות =====");

        // אתחול שירותים (Services)
        EmployeeService employeeService = new EmployeeService(new EmployeeRepository());
        CustomerService customerService = new CustomerService(new CustomerRepository());

        // --- בדיקת עובדים ---
        System.out.println("\n--- בדיקת עובדים ---");

        // הוספת עובדים חדשים
        employeeService.addEmployee(new Cashier("E001", "יוסי כהן", "050-1111111", "סניף חולון"));
        employeeService.addEmployee(new Seller("E002", "דני לוי", "050-2222222", "מחלקת בגדי גברים"));

        // בדיקת מניעת כפילות ID לעובד
        System.out.println("[בדיקת כפילות]: מנסה להוסיף עובד עם ID קיים (E001)...");
        employeeService.addEmployee(new Cashier("1", "יוסי כהן ", "0501234567", "BR1"));

        // הדפסת רשימת עובדים
        System.out.println("\nרשימת עובדים נוכחית:");
        employeeService.getAllEmployees().forEach(System.out::println);

        // --- בדיקת לקוחות ---
        System.out.println("\n--- בדיקת לקוחות ---");

        // הוספת לקוחות מסוגים שונים
        customerService.addCustomer(new NewCustomer("C100", "ישראל ישראלי", "054-0000000", 0.0)); // 0% הנחה
        customerService.addCustomer(new VIPCustomer("C200", "שרה המלכה", "052-5555555", 0.2));   // 20% הנחה

        // בדיקת מניעת כפילות ID ללקוח
        System.out.println("[בדיקת כפילות]: מנסה להוסיף לקוח עם ID קיים (C100)...");
        customerService.addCustomer(new ReturningCustomer("C100", "לקוח מתחזה", "111", 0.1));

        // הצגת לקוחות ובדיקת לוגיקת הנחה
        System.out.println("\nרשימת לקוחות ובדיקת הנחות (על מוצר שעולה 200 ש\"ח):");
        for (Customer c : customerService.getAllCustomers()) {
            double originalPrice = 200.0;
            double discountedPrice = c.applyDiscount(originalPrice);
            System.out.println(c + " -> מחיר אחרי הנחה: " + discountedPrice);
        }

        // --- בדיקת מחיקה ---
        System.out.println("\n--- בדיקת מחיקה ---");

        // מחיקת עובד
        System.out.println("מוחק עובד E002...");
        employeeService.removeEmployee("E002");

        // מחיקת לקוח
        System.out.println("מוחק לקוח C100...");
        customerService.removeCustomer("C100");

        // --- הצגת תמונת מצב סופית ---
        System.out.println("\n--- תמונת מצב סופית במערכת ---");

        System.out.println("עובדים שנותרו:");
        employeeService.getAllEmployees().forEach(System.out::println);

        System.out.println("\nלקוחות שנותרו:");
        customerService.getAllCustomers().forEach(System.out::println);

        System.out.println("\n===== הבדיקה הסתיימה בהצלחה =====");
    }
}