package com.medhelp2.mhchat.ui.settings;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface SettingsPresenterHelper<V extends SettingsViewHelper> extends MvpPresenter<V> {

    void loadUserInfo();

    void loadUserInfoLocal();

    void doLogOut();
}
