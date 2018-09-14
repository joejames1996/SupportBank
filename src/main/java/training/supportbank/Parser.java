package training.supportbank;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Parser
{
    public static Date parseRowDate(String s, String pattern) throws Exception
    {
        Date d = null;
        try
        {
            SimpleDateFormat form = new SimpleDateFormat(pattern);
            d = form.parse(s);
        }
        catch(Exception e)
        {
            throw new Exception("Could not parse date: " + s);
        }
        return d;
    }

    public static float parseRowAmount(String s) throws Exception
    {
        float a;
        try
        {
            a = Float.parseFloat(s);
        }
        catch(NumberFormatException e)
        {
            throw new Exception("Could not parse amount: " + s);
        }
        return a;
    }
}
