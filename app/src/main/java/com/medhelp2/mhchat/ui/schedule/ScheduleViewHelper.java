package com.medhelp2.mhchat.ui.schedule;

import com.medhelp2.mhchat.data.model.DateResponse;
import com.medhelp2.mhchat.data.model.ScheduleResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;


public interface ScheduleViewHelper extends MvpView
{
    void setupCalendar(DateResponse today, List<ScheduleResponse> response);

    void updateCalendar(String day, List<ScheduleResponse> response);

    void showErrorScreen();
}
