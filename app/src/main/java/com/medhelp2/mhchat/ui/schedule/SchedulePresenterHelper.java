package com.medhelp2.mhchat.ui.schedule;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface SchedulePresenterHelper<V extends ScheduleViewHelper> extends MvpPresenter<V>
{
    void getData();

    void getCenterInfo();

    void updateCalendar();

    void removePassword();
}
