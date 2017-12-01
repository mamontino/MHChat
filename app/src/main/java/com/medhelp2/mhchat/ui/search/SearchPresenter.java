package com.medhelp2.mhchat.ui.search;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class SearchPresenter<V extends SearchViewHelper> extends BasePresenter<V> implements SearchPresenterHelper<V>
{
    @Inject
    public SearchPresenter(DataHelper dataHelper, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    public void getPrice(List<CategoryResponse> categoryResponse)
    {
        Timber.d("getPrice");
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getPriceApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (response.getServices() != null)
                    {
                        Timber.d("getPrice загрузка прошла успешно: " + "categoryResponse: " + categoryResponse.size() + " " + response.getServices().size());
                        getMvpView().updateView(categoryResponse, response.getServices());
                    }
                    Timber.d("getPrice response == null or response.getServices() == null");
                    getMvpView().hideLoading();
                }, throwable ->
                {
                    Timber.d("getPrice загрузка прошла с ошибкой: " + throwable.getMessage());
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
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
                        getPrice(response.getSpec());
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
}
