package com.medhelp2.mhchat.utils.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimesUtils
{
    private TimesUtils()
    {
    }

    public static String getTime(String data)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try
        {
            date = format.parse(data);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        assert date != null;
        long dataLong = date.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(new Date(dataLong));
    }

    public static Date getDateSchedule(String data)
    {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try
        {
            return format.parse(data);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateSchedule(Date data)
    {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return format.format(data);
    }
}