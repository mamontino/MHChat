package com.medhelp2.mhchat.ui.doctor.details;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface DocDetailsPresenterHelper<V extends DocDetailsViewHelper> extends MvpPresenter<V>
{
    void loadDocInfo(int idDoctor);

    void dispose();

    void onScheduleClicked();

    void onRecordClicked();
}
