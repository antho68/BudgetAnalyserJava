package org.aba;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseUtils
{
    public static String getDateFormatted(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String getDateFormatteddd_MM_YYYY(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_YYYY");
        return dateFormat.format(date);
    }

    public static String getDateFormattedddPointMMPointYYYY(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        return dateFormat.format(date);
    }

    public static String getDoubleFormatted(Double number)
    {
        DecimalFormat df = new DecimalFormat ("#.##") ;
        return df.format(number);
    }
}
