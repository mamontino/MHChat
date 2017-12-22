package com.medhelp2.mhchat.ui.doctor;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface DoctorsPresenterHelper<V extends DoctorsViewHelper> extends MvpPresenter<V>
{
    void getCenterInfo();

    void getDoctorList();

    void removePassword();
}
