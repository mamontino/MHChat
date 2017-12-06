package com.medhelp2.mhchat.ui.login;

import com.medhelp2.mhchat.ui.base.MvpView;

public interface LoginViewHelper extends MvpView
{
    void openContactsActivity();

    void openProfileActivity();

    void openChatActivity();

    void openNetworkSettings();

    boolean isNeedSave();

    void closeActivity();
}
