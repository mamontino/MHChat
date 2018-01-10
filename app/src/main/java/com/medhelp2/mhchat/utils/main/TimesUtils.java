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

    public static Date getDateFromTime(String time)
    {
        DateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try
        {
            return format.parse(time);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Long getMillisFromTime(String time)
    {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try
        {
            Date d = f.parse(time);
            return d.getTime();
        } catch (ParseException e)
        {
            e.printStackTrace();
            return 0L;
        }
    }

    public static Long getMillisFromServer(String time)
    {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try
        {
            Date d = f.parse(time);
            return d.getTime();
        } catch (ParseException e)
        {
            e.printStackTrace();
            return 0L;
        }
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

    public static String transformStringDate(String admDate)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try
        {
            date = format.parse(admDate);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        assert date != null;
        long dataLong = date.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(new Date(dataLong));
    }

    public static Long getMillisFromVisit(String admDate)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try
        {
            Date d = format.parse(admDate);
            return d.getTime();
        } catch (ParseException e)
        {
            e.printStackTrace();
            return 0L;
        }
    }

    public static int getTypeYear(int year)
    {
        switch (year)
        {
            case 1:
            case 21:
            case 31:
            case 41:
            case 51:
            case 61:
            case 71:
            case 81:
            case 91:
                return AppConstants.TYPE_YEAR_ONE;
            case 2:
            case 3:
            case 4:
            case 22:
            case 23:
            case 24:
            case 32:
            case 33:
            case 34:
            case 42:
            case 43:
            case 44:
            case 52:
            case 53:
            case 54:
            case 62:
            case 63:
            case 64:
            case 72:
            case 73:
            case 74:
            case 82:
            case 83:
            case 84:
            case 92:
            case 93:
            case 94:
                return AppConstants.TYPE_YEAR_TWO;
            default:
                return AppConstants.TYPE_YEAR_THREE;
        }
    }
}