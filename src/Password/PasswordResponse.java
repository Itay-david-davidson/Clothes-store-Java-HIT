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

    public Map<String,PasswordData> password_policy;
    private static final String FILE_PATH = "data/password_policy.json";
    private static final Gson gson = new Gson();

    public static PasswordData getPasswordPolicy()
    {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Error opening password policy file");
            return null;
        }
        try (FileReader reader = new FileReader(file)) {
            PasswordResponse response = gson.fromJson(reader,PasswordResponse.class);
            if (response == null)
            {
                //TODO: throw error
                System.out.println("Password policy might be empty");
                return null;
            }
            return response.password_policy.get("password_policy");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
