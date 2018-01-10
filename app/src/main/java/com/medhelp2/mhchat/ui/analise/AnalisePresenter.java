package com.medhelp2.mhchat.ui.analise;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.SaleList;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class AnalisePresenter<V extends AnaliseViewHelper> extends BasePresenter<V> implements AnalisePresenterHelper<V>
{

    @Inject
    AnalisePresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
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
                        e.printStackTrace();
                    }
                }, throwable -> Timber.e("Данные из локального хранилища загружены с ошибкой" + throwable.getMessage())));
    }

    @Override
    public void updateAnaliseList()
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getCurrentDateApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(dateList ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getSalePastDate(dateList.getResponse().getToday());
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().showErrorScreen();
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
                    getMvpView().hideLoading();
                    //                    getMvpView().updateAnaliseData(response);
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().showErrorScreen();
                }));
    }

    @Override
    public void unSubscribe()
    {
        if (!getCompositeDisposable().isDisposed())
        {
            getCompositeDisposable().dispose();
            getMvpView().hideLoading();
        }
    }

    @Override
    public void removePassword()
    {
        getDataHelper().setCurrentPassword("");
    }

    @Override
    public void loadFile(String image)
    {

    }
}
