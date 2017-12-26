package com.medhelp2.mhchat.ui.splash;

import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CenterList;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class SplashPresenter<V extends SplashViewHelper> extends BasePresenter<V>
        implements SplashPresenterHelper<V>
{
    @Inject
    SplashPresenter(DataHelper dataHelper, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView)
    {
        super.onAttach(mvpView);
        openNextActivity();
    }

    private void openNextActivity()
    {
        String username = null;
        String password = null;

        try
        {
            username = getDataHelper().getCurrentUserName().trim();
            password = getDataHelper().getCurrentPassword().trim();
        } catch (Exception e)
        {
            Timber.e("Ошибка чтения учетных данных пользователя: " + e.getMessage());
        }

        if (username != null && password != null && !password.equals(""))
        {
            updateHeaderInfo();
        } else
        {
            getMvpView().openLoginActivity();
        }
    }

    private void updateHeaderInfo()
    {
        getCompositeDisposable().add(getDataHelper()
                .getCenterApiCall()
                .map(CenterList::getResponse)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                        {
                            if (!isViewAttached())
                            {
                                return;
                            }
                            saveCenterInfo(response.get(0));
                        }, throwable ->
                        {
                            getMvpView().openLoginActivity();
                            Timber.e("Данные центра загружены с ошибкой: " + throwable.getMessage());
                        }
                ));
    }

    private void saveCenterInfo(CenterResponse response)
    {
        getCompositeDisposable().add(getDataHelper().saveRealmCenter(response)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(() ->
                                getMvpView().openProfileActivity(),
                        throwable ->
                        {
                            getMvpView().openLoginActivity();
                            Timber.e("Ошибка сохранения CenterResponse " +
                                    "в локальное хранилище: " + throwable.getMessage());
                        }));
    }
}
