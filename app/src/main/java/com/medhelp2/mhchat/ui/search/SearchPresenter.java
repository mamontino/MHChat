package com.medhelp2.mhchat.ui.search;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class SearchPresenter<V extends SearchViewHelper> extends BasePresenter<V>
        implements SearchPresenterHelper<V>
{
    @Inject
    public SearchPresenter(
            DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    private void getPrice(List<CategoryResponse> categoryResponse)
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getPriceApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (response.getServices() != null)
                    {
                        getMvpView().updateView(categoryResponse, response.getServices());
                    }
                    getMvpView().hideLoading();
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
    public void removePassword()
    {
        getDataHelper().setCurrentPassword("");
    }

    @Override
    public void getData()
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getCategoryApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (response != null)
                    {
                        getPrice(response.getSpec());
                    }
                    getMvpView().hideLoading();
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
                }, throwable -> Timber.d("Данные из локального хранилища загружены с ошибкой")));
    }

    @Override
    public void unSubscribe()
    {
        if (!getCompositeDisposable().isDisposed())
        {
            getCompositeDisposable().dispose();
        }
        getMvpView().hideLoading();
    }
}
