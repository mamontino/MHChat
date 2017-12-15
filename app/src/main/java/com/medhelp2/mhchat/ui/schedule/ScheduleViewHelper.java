package com.medhelp2.mhchat.ui.schedule;

import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.DateResponse;
import com.medhelp2.mhchat.data.model.ScheduleResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;


public interface ScheduleViewHelper extends MvpView
{
    void closeNavigationDrawer();

    void lockDrawer();

    void unlockDrawer();

    void showProfileActivity();

    void showSearchActivity();

    void showContactsActivity();

    void showLoginActivity();

    void showSaleActivity();

    void showRateFragment();

    void showDoctorsActivity();

    void updateHeader(CenterResponse response);

    void setupCalendar(DateResponse today, List<ScheduleResponse> response);

    void updateCalendar(String day, List<ScheduleResponse> response);
}
