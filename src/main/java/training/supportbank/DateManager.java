package training.supportbank;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManager
{
    public static Date parseOLEDate(String OLEDate) throws Exception
    {
        double d = Double.parseDouble(OLEDate);
        double mantissa = d - (long)d;
        double hour = mantissa * 24;
        double min = (hour - (long)hour) * 60;
        double sec = (min - (long)min) * 60;

        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        Date baseDate = form.parse("30/12/1899");
        Calendar c = Calendar.getInstance();
        c.setTime(baseDate);
        c.add(Calendar.DATE, (int)d);
        c.add(Calendar.HOUR, (int)hour);
        c.add(Calendar.MINUTE, (int)min);
        c.add(Calendar.SECOND, (int)sec);

        return c.getTime();
    }
}
