package Password;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import employees.Employee;
import employees.EmployeeData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PasswordResponse {

    public PasswordData password_policy;
    private static final String FILE_PATH = "src/data/password_policy.json";
    private static final Gson gson = new Gson();

    public static PasswordData getPasswordPolicy()
    {
        PasswordData d = new PasswordData(5,true,true,true,true);
        String json = gson.toJson(d);
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Error opening password policy file");
            return null;
        }
        try (FileReader reader = new FileReader(file)) {
            PasswordData response = gson.fromJson(reader,PasswordData.class);
            if (response == null)
            {
                //TODO: throw error
                System.out.println("Password policy might be empty");
                return null;
            }
            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
