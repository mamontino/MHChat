package com.medhelp2.mhchat.ui.details;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface DetailsPresenterHelper<V extends DetailsViewHelper> extends MvpPresenter<V>
{
    void loadUserInfo();

    void loadUserInfoLocal();
}
