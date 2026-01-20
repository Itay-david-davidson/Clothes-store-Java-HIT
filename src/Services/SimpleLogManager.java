package Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.Date; //   ייבוא של כלי תאריך
import java.util.List;

public class SimpleLogManager {
    private static final String FILE_PATH = "src/data/logs.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void writeToLog(String personName, String action, String description) {
        List<String> allLogs = loadLogs();

        // 2. יצירת חותמת זמן פשוטה
        String currentTime = new Date().toString();

        // 3. הוספת הזמן לתחילת השורה
        String newEntry = "[" + currentTime + "] " + personName + " | Action: " + action + " | Info: " + description;

        allLogs.add(newEntry);
        // TRY סוגר את המשאב (הקובץ) בסיום גםאם יש שגיאה
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(allLogs, writer);
        }
        //חריגה אם קובץ לא קיים או דיסק מלא או נתיב שגוי אי זה אובייקט החריגה
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //מתודה שטוענת את הלוגים הישנים מהקובץ גייסון ממירה אותם למחרוזת ומחזירה אותם
    private static List<String> loadLogs() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, new TypeToken<List<String>>(){}.getType());
        } // אם קובץ פגום תוכנית לא מפסיקה וממשיכה כרגיל
        catch (IOException e) {
            return new ArrayList<>();
        }
    }
}