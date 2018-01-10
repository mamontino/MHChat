package com.medhelp2.mhchat.ui.contacts;


import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;

@SuppressWarnings("unused")
public interface ContactsViewHelper extends MvpView
{
    void lockDrawer();

    void unlockDrawer();

    void closeNavigationDrawer();

    void showProfileActivity();

    void showDoctorsActivity();

    void showSearchActivity();

    void showSaleActivity();

    void showAnaliseActivity();

    void showRateFragment();

    void showLoginActivity();

    void showErrorScreen();

    void updateHeader(CenterResponse response);

    void updateUserListData(List<RoomResponse> response);
}
