package Password;

import java.io.Serializable;

public class PasswordData implements Serializable {
    public int minLength;
    public boolean requireUppercase;
    public boolean requireLowercase;
    public boolean requireSpecial;
    public boolean requireDigit;

}
