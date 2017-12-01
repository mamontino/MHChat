package com.medhelp2.mhchat.ui.splash;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface SplashPresenterHelper<V extends SplashViewHelper> extends MvpPresenter<V>
{
}
