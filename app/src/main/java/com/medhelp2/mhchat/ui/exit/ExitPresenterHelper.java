package com.medhelp2.mhchat.ui.exit;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface ExitPresenterHelper<V extends ExitViewHelper> extends MvpPresenter<V>
{
    void loadDocInfo(int idDoctor);

    void onScheduleClicked();

    void onRecordClicked();
}
