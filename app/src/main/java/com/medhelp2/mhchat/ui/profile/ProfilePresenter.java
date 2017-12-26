package com.medhelp2.mhchat.ui.profile;

import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CenterResponse;
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
    public ProfilePresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getVisits()
    {
        getMvpView().showLoading();
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
                    if (response != null && response.getResponse() != null)
                    {
                        getMvpView().updateData(response.getResponse());
                    }
                    getMvpView().hideLoading();
                }, throwable ->
                {
                    Timber.e("Данные посещений загружены с ошибкой: "
                            + throwable.getMessage());

                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
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
                    Timber.e("Данные центра загружены с ошибкой: " + throwable.getMessage());
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));

//        getCompositeDisposable().add(getDataHelper()
//                .getCenterApiCall()
//                .map(CenterList::getResponse)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(response ->
//                {
//                    if (!isViewAttached())
//                    {
//                        return;
//                    }
//                    if (response != null)
//                    {
//                        getMvpView().updateHeader(response.get(0));
//                        saveCenterInfo(response.get(0));
//                    }
//                    getMvpView().hideLoading();
//                }, throwable ->
//                {
//                    Timber.e("Данные центра загружены с ошибкой: " + throwable.getMessage());
//                    if (!isViewAttached())
//                    {
//                        return;
//                    }
//                    getMvpView().hideLoading();
//                }));
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
            Timber.d("Ошибка чтения пользовательских данных из SharedPreference");
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
                                Timber.e("Данные успешно сохранены в локальное хранилище"),
                        throwable -> Timber.e("Ошибка сохранения CenterResponse " +
                                "в локальное хранилище: " + throwable.getMessage())));
    }

    @Override
    public CenterResponse getCenterInfo()
    {
        final CenterResponse[] center = {null};
        getCompositeDisposable().add(getDataHelper().getRealmCenter()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(centerResponse -> center[0] = centerResponse));
        return center[0];
    }
}
