package com.medhelp2.mhchat.ui.schedule;


import com.medhelp2.mhchat.data.model.DateResponse;
import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface SchedulePresenterHelper<V extends ScheduleViewHelper> extends MvpPresenter<V>
{
    void getCenterInfo();

    void getDateFromServer(int idDoctor, int adm);

    void getCalendarData(int idDoctor, DateResponse today, int adm);

    void getCalendarData(int idDoctor, String day, int adm);

    void removePassword();

    void unSubscribe();
}
