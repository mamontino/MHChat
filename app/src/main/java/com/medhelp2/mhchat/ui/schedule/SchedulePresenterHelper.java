package com.medhelp2.mhchat.ui.schedule;


import com.medhelp2.mhchat.data.model.DateResponse;
import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface SchedulePresenterHelper<V extends ScheduleViewHelper> extends MvpPresenter<V>
{
    void getDateFromService(int idService, int adm);

    void getDateFromDoctor(int idDoctor, int idService, int adm);

    void getScheduleByService(int idService, DateResponse today, int adm);

    void getScheduleByService(int idService, String day, int adm);

    void getScheduleByDoctor(int idDoctor, DateResponse today, int adm);

    void getScheduleByDoctor(int idDoctor, String day, int adm);

    void removePassword();

    void unSubscribe();
}
