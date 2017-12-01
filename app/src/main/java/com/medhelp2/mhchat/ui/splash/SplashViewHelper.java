package com.medhelp2.mhchat.ui.splash;

import com.medhelp2.mhchat.ui.base.MvpView;

public interface SplashViewHelper extends MvpView
{
    void openLoginActivity();

    void openContactsActivity();

    void openProfileActivity();

    void openChatActivity();
}
