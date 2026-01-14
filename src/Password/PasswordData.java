package Password;

import java.io.Serializable;

public class PasswordData{
    public int minLength;
    public boolean requireUppercase;
    public boolean requireLowercase;
    public boolean requireSpecial;
    public boolean requireDigit;

    public PasswordData(int minLength,boolean requireUppercase,boolean requireLowercase,boolean requireSpecial,boolean requireDigit)
    {
        this.minLength = minLength;
        this.requireUppercase = requireUppercase;
        this.requireLowercase = requireLowercase;
        this.requireSpecial = requireSpecial;
        this.requireDigit = requireDigit;
    }
    public PasswordData(){}

}
