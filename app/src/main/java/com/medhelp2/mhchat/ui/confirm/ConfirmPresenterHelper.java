package com.medhelp2.mhchat.ui.confirm;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface ConfirmPresenterHelper<V extends ConfirmViewHelper> extends MvpPresenter<V>
{
    void loadDocInfo(int idDoctor);

    void onScheduleClicked();

    void onRecordClicked();
}
