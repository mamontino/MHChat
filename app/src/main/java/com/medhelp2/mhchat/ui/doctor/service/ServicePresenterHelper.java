package com.medhelp2.mhchat.ui.doctor.service;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface ServicePresenterHelper<V extends ServiceViewHelper> extends MvpPresenter<V>
{
    void getData(int idDoctor);

    void unSubscribe();
}
