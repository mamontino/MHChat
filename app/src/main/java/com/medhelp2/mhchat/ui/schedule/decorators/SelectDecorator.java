package com.medhelp2.mhchat.ui.schedule.decorators;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.medhelp2.mhchat.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;


/**
 * Использовать кастомный селектор
 */

public class SelectDecorator implements DayViewDecorator
{
    private final Drawable drawable;

    public SelectDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.date_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
