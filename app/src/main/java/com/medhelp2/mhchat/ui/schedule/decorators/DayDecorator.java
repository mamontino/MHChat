package com.medhelp2.mhchat.ui.schedule.decorators;


import android.content.Context;
import android.graphics.drawable.Drawable;

import com.medhelp2.mhchat.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;


public class DayDecorator implements DayViewDecorator
{
    public static final int DAY_MODE_MANY = 1;
    public static final int DAY_MODE_FEW = 2;
    public static final int DAY_MODE_NOT = 3;
    public static final int DAY_MODE_NO = 4;

    private Context context;
    private final CalendarDay day;
    private int dayMode;
    private final Drawable bgDrawableGreen;
    private final Drawable bgDrawableYellow;
    private final Drawable bgDrawableRed;

    public int getDayMode()
    {
        return dayMode;
    }

    public DayDecorator(Context context, CalendarDay day, int dayMode)
    {
        this.context = context;

        this.day = day;
        this.dayMode = dayMode;
        bgDrawableGreen = context.getResources().getDrawable(R.drawable.date_item_default);
        bgDrawableYellow = context.getResources().getDrawable(R.drawable.date_orange);
        bgDrawableRed = context.getResources().getDrawable(R.drawable.date_red);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day)
    {
        return this.day.equals(day);
    }

    @Override
    public void decorate(DayViewFacade view)
    {
        if (dayMode == DAY_MODE_MANY)
        {
            view.setBackgroundDrawable(bgDrawableGreen);
        } else if (dayMode == DAY_MODE_FEW)
        {
            view.setBackgroundDrawable(bgDrawableYellow);
        } else if (dayMode == DAY_MODE_NO)
        {
            view.setBackgroundDrawable(bgDrawableRed);
        } else if (dayMode == DAY_MODE_NOT)
        {
            view.setDaysDisabled(true);
        }
    }
}