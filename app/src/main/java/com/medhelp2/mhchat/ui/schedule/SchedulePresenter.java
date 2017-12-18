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
    public void getCenterInfo()
    {
        getCompositeDisposable().add(getDataHelper().getRealmCenter()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(centerResponse ->
                {
                    try
                    {
                        getMvpView().updateHeader(centerResponse);
                    } catch (Exception e)
                    {
                        Timber.e("Данные из локального хранилища " +
                                "загружены с ошибкой: " + e.getMessage());
                    }
                }, throwable -> Timber.e("Данные из локального хранилища " +
                        "загружены с ошибкой: " + throwable.getMessage())));
    }

    @Override
    public void getDateFromServer(int idDoctor, int adm)
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getCurrentDate()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }

                    if (!response.isError())
                    {
                        DateResponse date = response.getResponse();

                        if (!response.isError())
                        {
                            getMvpView().hideLoading();
                            getCalendarData(idDoctor, date, adm);
                        } else
                        {
                            getMvpView().hideLoading();
                        }
                    } else
                    {
                        getMvpView().hideLoading();
                    }
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
    public void getCalendarData(int idDoctor, DateResponse date, int adm)
    {
        getMvpView().showLoading();

        String monday = date.getLastMonday();

        if (monday.equals("1"))
        {
            monday = date.getToday();
        }

        getCompositeDisposable().add(getDataHelper()
                .getScheduleByDoctor(idDoctor, monday, adm)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .map(ScheduleList::getResponse)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (response != null && response.size() > 0)
                    {
                        getMvpView().hideLoading();
                        getMvpView().setupCalendar(date, response);
                    }

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
    public void getCalendarData(int idDoctor, String date, int adm)
    {
        Timber.d("getCalendarData");
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getScheduleByDoctor(idDoctor, date, adm)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .map(ScheduleList::getResponse)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (response != null && response.size() > 0)
                    {
                        Timber.d("getSchedule: загрузка прошла успешно");
                    }
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
