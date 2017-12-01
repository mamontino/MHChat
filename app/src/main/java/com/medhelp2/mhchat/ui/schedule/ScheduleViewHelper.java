package com.medhelp2.mhchat.ui.schedule;

import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;


public interface ScheduleViewHelper extends MvpView
{
    void closeNavigationDrawer();

    void lockDrawer();

    void unlockDrawer();

    void showProfileActivity();

    void showContactsActivity();

    void showLoginActivity();

    void showAboutFragment();

    void showDoctorsActivity();

    void showSettingsActivity();

    void updateHeader(CenterResponse response);

    void updateDateInfo(List<String> response);
}
