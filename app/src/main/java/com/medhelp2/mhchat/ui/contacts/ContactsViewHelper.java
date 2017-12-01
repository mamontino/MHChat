package com.medhelp2.mhchat.ui.contacts;

import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

public interface ContactsViewHelper extends MvpView {

    void lockDrawer();

    void unlockDrawer();

    void closeNavigationDrawer();

    void showProfileActivity();

    void showDoctorsActivity();

    void showSearchActivity();

    void showSettingsActivity();

    void showAboutFragment();

    void showLoginActivity();

    void updateHeader(CenterResponse response);
}
