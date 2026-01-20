package Services; // הקובץ נמצא בתיקיית Services

import com.google.gson.Gson; // כלי להפיכת אובייקטים לטקסט JSON
import com.google.gson.GsonBuilder; // כלי לעיצוב ה-JSON (רווחים ושורות חדשות)
import java.io.*; // כלי קריאה וכתיבה של קבצים
import java.util.HashMap; // מבנה נתונים של מפתח (שם מוצר) וערך (מספר)
import java.util.Map; // הממשק הכללי למפות (Maps)

public class SimpleStatsManager {
    // 1. הנתיב לקובץ שבו נשמור את הסטטיסטיקות
    private static final String FILE_PATH = "src/data/statistics.json";

    // 2. יצירת אובייקט GSON שכותב בצורה יפה ומסודרת
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // הפונקציה המרכזית: מקבלת שם מוצר ומעדכנת את המונה שלו
    public static void updateProductSale(String productName) {
        // א. טעינת הנתונים הקיימים מהקובץ גייסון על ידי פונקציה loadstats לתוך משתנה "מפה" (טבלה של שם וכמות)
        Map<String, Double> stats = loadStats();//פונקציה ממומשת למטה

        // ב. בדיקה: האם שם המוצר כבר קיים בטבלה?
        if (stats.containsKey(productName)) {
            // אם קיים: ניקח את הכמות הנוכחית ונוסיף לה 1, למה השתמשנו בדאבל? כי GSON מתרגם מספרים מגייסון כדאבל (עשרוני)
            double currentCount = stats.get(productName);
            stats.put(productName, currentCount + 1);
        } else {
            // אם לא קיים: ניצור שורה חדשה עם המספר 1
            stats.put(productName, 1.0);
        }

        // ג. שמירת הטבלה המעודכנת חזרה לקובץ ה-JSON
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(stats, writer); // המרת הטבלה לטקסט JSON וכתיבה
        }
        catch (IOException e) {
            System.out.println("Error saving stats: " + e.getMessage());
        }
    }

    // פונקציית עזר\מתודת עזר: קוראת את הקובץ והופכת אותו חזרה לטבלה או מילון (Map)
    private static Map<String, Double> loadStats() {
        // פייל הוא אובייקט שמייצג נתיב לקובץ , לא את הקובץ עצמו
        File file = new File(FILE_PATH);
        // אם הקובץ לא קיים, נחזיר טבלה חדשה וריקה חוסך אקספשיין מיותר אם לא קיים קובץ
        if (!file.exists()) return new HashMap<>();
// טריי סוגר את המשאבים בסיום אוטומטי
        try (FileReader reader = new FileReader(file)) {
            // GSON הופך את הטקסט מהקובץ למבנה של Map (מפתח וערך)
            Map<String, Double> result = gson.fromJson(reader, HashMap.class);
            return result != null ? result : new HashMap<>();
        }
        catch (IOException e) {
            return new HashMap<>(); // במקרה של שגיאה, נחזיר טבלה ריקה
        }
    }
}