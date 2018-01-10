package com.medhelp2.mhchat.ui.login;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface LoginPresenterHelper<V extends LoginViewHelper> extends MvpPresenter<V>
{
    void onLoginClick(String username, String password);

    void getUsername();
}
