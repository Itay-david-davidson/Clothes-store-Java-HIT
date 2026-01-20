package Services;

import java.io.FileWriter; // כלי לכתיבת טקסט לקובץ
import java.io.IOException; // טיפול בשגיאות כתיבה
import java.util.Map; // מבנה הנתונים של הסטטיסטיקות

public class SimpleReportManager {
    // הנתיב שבו נשמור את הדוח הסופי (הקובץ שייפתח ב-Word)
    private static final String REPORT_PATH = "src/data/store_report.doc";

    public static void createSalesReport() {
        //  נטען את הסטטיסטיקות שהכנו במחלקה הקודמת (SimpleStatsManager)
        Map<String, Double> stats = SimpleStatsManager.loadStats();

        //  ניצור משתנה של טקסט (StringBuilder) זה כמו דף שאנחנו מוסיפים לו שורות
        // היתרון שבמחלקה הזאת אפשר לערוך את המחרוזת (mutable) בשונה מסטרינג רגיל
        StringBuilder reportText = new StringBuilder();

        // הוספת כותרת לדוח
        reportText.append("=== CLOTHES STORE SALES REPORT ===\n");
        reportText.append("Generated on: " + new java.util.Date() + "\n");
        reportText.append("----------------------------------\n\n");

        //  נעבור שורה-שורה על הטבלה שלנו (המוצר והכמות) raw זה בעצם זוג מתוך המילון
        for (Map.Entry<String, Double> row : stats.entrySet()) {
            String productName = row.getKey(); // שם המוצר
            double quantity = row.getValue() ; // הכמות

            // הוספת השורה לדוח
            reportText.append("Product: ").append(productName)
                    .append(" | Units Sold: ").append(quantity)
                    .append("\n");
        }

        reportText.append("\n----------------------------------\n");
        reportText.append("End of Report.");

        // שמירת כל הטקסט הזה לקובץ TRY סוגר את המשאבים בסיום גם אם יש שגיאה
        try (FileWriter writer = new FileWriter(REPORT_PATH)) {
            writer.write(reportText.toString()); // כותבים את כל ה-StringBuilder לקובץ writer שהוא אובייקט המייצג קובץ
            System.out.println("Success: Report generated at " + REPORT_PATH);
        }
        catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }//ככה התוכנית לא תקרוס אם יש בעיה בקובץ או חסר מקום או כל בעיה אחרת בכתיבה לקובץ
    }
}