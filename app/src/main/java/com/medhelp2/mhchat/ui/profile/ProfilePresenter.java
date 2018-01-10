package com.medhelp2.mhchat.ui.profile;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.DateList;
import com.medhelp2.mhchat.data.model.DateResponse;
import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@PerActivity
public class ProfilePresenter<V extends ProfileViewHelper>
        extends BasePresenter<V> implements ProfilePresenterHelper<V>
{
    @Inject
    ProfilePresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getVisits()
    {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataHelper().getCurrentDateApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .map(DateList::getResponse)
                .map(DateResponse::getToday)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::loadVisits, throwable ->
                {
                    Timber.e("getCurrentDateApiCall error");
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().swipeDismiss();
                    getMvpView().showErrorScreen();
                }));
    }

    private void loadVisits(String today)
    {
        getCompositeDisposable().add(getDataHelper()
                .getAllReceptionApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().swipeDismiss();
                    getMvpView().updateData(response.getResponse(), today);
                }, throwable ->
                {
                    Timber.e("getAllReceptionApiCall error");
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().swipeDismiss();
                    getMvpView().showErrorScreen();
                }));
    }

    @Override
    public void updateHeaderInfo()
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().getRealmCenter()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(centerResponse ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().updateHeader(centerResponse);
                    saveCenterInfo(centerResponse);
                    getMvpView().hideLoading();
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().showError(R.string.data_load_db_err);
                }));
    }

    @Override
    public void updateToken()
    {
        String fbToken;
        int idUser;
        try
        {
            fbToken = getDataHelper().getFireBaseToken();
            idUser = getDataHelper().getCurrentUserId();

            if (fbToken != null && idUser != 0)
            {
                getMvpView().runSendRegistrationService(fbToken, idUser);
            }
        } catch (Exception e)
        {
            getMvpView().showError(R.string.data_load_pref_err);
        }
    }

    @Override
    public void removePassword()
    {
        getDataHelper().setCurrentPassword("");
    }

    private void saveCenterInfo(CenterResponse response)
    {
        getCompositeDisposable().add(getDataHelper().saveRealmCenter(response)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(() ->
                {
                }, throwable -> getMvpView().showError(R.string.data_load_db_err)));
    }

    @Override
    public CenterResponse getCenterInfo()
    {
        final CenterResponse[] center = {null};
        getCompositeDisposable().add(getDataHelper().getRealmCenter()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(centerResponse -> center[0] = centerResponse
                        , throwable -> getMvpView().showError("www")));
        return center[0];
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
