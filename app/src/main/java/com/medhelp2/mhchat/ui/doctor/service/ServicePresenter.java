package com.medhelp2.mhchat.ui.doctor.service;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


public class ServicePresenter<V extends ServiceViewHelper> extends BasePresenter<V>
        implements ServicePresenterHelper<V>
{
    @Inject
    public ServicePresenter(
            DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    private void getPrice(List<CategoryResponse> categoryResponse, int idDoctor)
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getPriceApiCall(idDoctor)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    getMvpView().hideLoading();
                    getMvpView().updateView(categoryResponse, response.getServices());
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
    public void getData(int idDoctor)
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getCategoryApiCall(idDoctor)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (response != null)
                    {
                        getPrice(response.getSpec(), idDoctor);
                    }
                    getMvpView().hideLoading();
                }, throwable ->
                {
                    Timber.e("getData загрузка прошла с ошибкой: " + throwable.getMessage());
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
}
