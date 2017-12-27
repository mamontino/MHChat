package com.medhelp2.mhchat.ui.sale;

import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.SaleList;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class SalePresenter<V extends SaleViewHelper> extends BasePresenter<V> implements SalePresenterHelper<V>
{

    @Inject
    public SalePresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getCenterInfo()
    {
        Timber.d("Получение информации о центре из локального хранилища");
        getCompositeDisposable().add(getDataHelper().getRealmCenter()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(centerResponse ->
                {
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
    public void updateSaleList()
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getCurrentDateApiCall()
                .map(dateList -> dateList.getResponse().getToday())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(today ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getSalePastDate(today);
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    private void getSalePastDate(String today)
    {
        getCompositeDisposable().add(getDataHelper().getSaleApiCall(today)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .map(SaleList::getResponse)
                .subscribe(response ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    Timber.d("Данные успешно загружены в updateSaleList");
                    getMvpView().hideLoading();
                    getMvpView().updateSaleData(response);
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    Timber.e("Загрузка данных в updateSaleList произошла с ошибкой: " + throwable.getMessage());
                }));
    }

    @Override
    public void removePassword()
    {
        getDataHelper().setCurrentPassword("");
    }
}
