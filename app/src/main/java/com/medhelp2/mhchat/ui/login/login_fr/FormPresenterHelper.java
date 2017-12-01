package com.medhelp2.mhchat.ui.login.login_fr;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface FormPresenterHelper<V extends FormViewHelper> extends MvpPresenter<V>
{
    void onLoginClick(String username, String password);
}
