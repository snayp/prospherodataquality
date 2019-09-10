package me.datalight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by dsh on 29.09.2018.
 */
public class  Utils {

    public static Date parseDateGMT(String date, String format) throws ParseException {
        SimpleDateFormat  formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.parse(date);
    }

    public static String formatDate(Date date, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static java.sql.Date sqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }
}
