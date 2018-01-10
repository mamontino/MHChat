package com.medhelp2.mhchat.ui.license;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface LicensePresenterHelper<V extends LicenseViewHelper> extends MvpPresenter<V>
{
    void confirmLicense();
}
