package com.medhelp2.mhchat.ui.login;

import com.medhelp2.mhchat.ui.base.MvpView;

public interface LoginViewHelper extends MvpView
{
    void openProfileActivity();

    boolean isNeedSave();

    void updateUsernameHint(String username);

    void closeActivity();
}
