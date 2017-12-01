package com.medhelp2.mhchat.ui.schedule;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

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
    public void getData()
    {
        Timber.d("getData");
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getCategoryApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (response != null)
                    {
                        Timber.d("getData загрузка прошла успешно");
                    }
                    getMvpView().hideLoading();
                }, throwable ->
                {
                    Timber.d("getData загрузка прошла с ошибкой: " + throwable.getMessage());
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void getCenterInfo()
    {
        Timber.d("Получение информации о центре из локального хранилища");
        getCompositeDisposable().add(getDataHelper().getRealmCenter()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(centerResponse -> {
                    try
                    {
                        Timber.d("Данные успешно загружены из локального хранилища");
                        Timber.d(centerResponse.getTitle() + " " + (centerResponse.getPhone()));
                        getMvpView().updateHeader(centerResponse);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }, throwable -> Timber.d("Данные из локального хранилища загружены с ошибкой")));
    }

    @Override
    public void updateCalendar()
    {
        List<String> list = new ArrayList<>();

        list.add("10.00");
        list.add("10.30");
        list.add("11.00");
        list.add("11.30");
        list.add("12.00");
        list.add("12.30");
        list.add("13.00");
        list.add("13.30");
        list.add("14.30");

        getMvpView().updateDateInfo(list);
    }
}
