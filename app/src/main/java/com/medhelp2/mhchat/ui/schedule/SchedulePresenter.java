package com.medhelp2.mhchat.ui.schedule;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.DateResponse;
import com.medhelp2.mhchat.data.model.ScheduleList;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class SchedulePresenter<V extends ScheduleViewHelper> extends BasePresenter<V>
        implements SchedulePresenterHelper<V>
{
    @Inject
    public SchedulePresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void removePassword()
    {
        getDataHelper().setCurrentPassword("");
    }

    @Override
    public void getDateFromService(int idService, int adm)
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getCurrentDateApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }

                    DateResponse date = response.getResponse();
                    getMvpView().hideLoading();
                    getScheduleByService(idService, date, adm);
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void getDateFromDoctor(int idDoctor, int idService, int adm)
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getCurrentDateApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    DateResponse date = response.getResponse();
                    getScheduleByDoctor(idDoctor, date, adm);
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void getScheduleByDoctor(int idDoctor, DateResponse date, int adm)
    {
        getMvpView().showLoading();

        String monday = date.getLastMonday();

        if (monday.equals("1"))
        {
            monday = date.getToday();
        }

        getCompositeDisposable().add(getDataHelper()
                .getScheduleByDoctorApiCall(idDoctor, monday, adm)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .map(ScheduleList::getResponse)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    getMvpView().hideLoading();
                    getMvpView().setupCalendar(date, response);
                }, throwable ->
                {
                    Timber.e("getSchedule: загрузка прошла с ошибкой: "
                            + throwable.getMessage());
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void getScheduleByService(int idService, DateResponse date, int adm)
    {
        getMvpView().showLoading();

        String monday = date.getLastMonday();

        if (monday.equals("1"))
        {
            monday = date.getToday();
        }

        getCompositeDisposable().add(getDataHelper()
                .getScheduleByServiceApiCall(idService, monday, adm)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .map(ScheduleList::getResponse)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    getMvpView().hideLoading();
                    getMvpView().setupCalendar(date, response);

                }, throwable ->
                {
                    Timber.d("getSchedule: загрузка прошла с ошибкой: "
                            + throwable.getMessage());
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void getScheduleByService(int idService, String date, int adm)
    {
        Timber.d("getScheduleByService");
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getScheduleByServiceApiCall(idService, date, adm)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .map(ScheduleList::getResponse)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    getMvpView().hideLoading();
                    getMvpView().updateCalendar(date, response);
                }, throwable ->
                {
                    Timber.e("getSchedule: загрузка прошла с ошибкой: "
                            + throwable.getMessage());

                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void getScheduleByDoctor(int idDoctor, String date, int adm)
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getScheduleByDoctorApiCall(idDoctor, date, adm)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .map(ScheduleList::getResponse)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    getMvpView().hideLoading();
                    getMvpView().updateCalendar(date, response);
                }, throwable ->
                {
                    Timber.d("getSchedule: загрузка прошла с ошибкой: "
                            + throwable.getMessage());

                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void unSubscribe()
    {
        if (!getCompositeDisposable().isDisposed())
        {
            return;
        }
        getCompositeDisposable().clear();
        getMvpView().hideLoading();
    }
}
