package com.medhelp2.mhchat.ui.login.login_fr;

import com.medhelp2.mhchat.ui.base.MvpView;

public interface FormViewHelper extends MvpView
{
    void openContactsActivity();

    void openProfileActivity();

    void openChatActivity();

    void openNetworkSettings();

    boolean isNeedSave();

    void closeActivity();
}
