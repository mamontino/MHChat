package com.medhelp2.mhchat.ui.contacts;

import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;

public interface ContactsViewHelper extends MvpView {

    void lockDrawer();

    void unlockDrawer();

    void closeNavigationDrawer();

    void showProfileActivity();

    void showDoctorsActivity();

    void showSearchActivity();

    void showSaleActivity();

    void showScheduleActivity();

    void showSettingsActivity();

    void showAboutFragment();

    void showRateFragment();

    void showLoginActivity();

    void updateHeader(CenterResponse response);

    void updateUserListData(List<RoomResponse> response);
}
