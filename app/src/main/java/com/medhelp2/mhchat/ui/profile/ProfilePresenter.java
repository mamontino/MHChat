package com.medhelp2.mhchat.ui.profile;

import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@PerActivity
public class ProfilePresenter<V extends ProfileViewHelper> extends BasePresenter<V> implements ProfilePresenterHelper<V>
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
                    if (response != null && response.getResponse() != null)
                    {
                        Timber.d("Данные посещений загружены успешно: " + response.getResponse().size());
                        getMvpView().updateData(response.getResponse());
                    }
                    getMvpView().hideLoading();
                }, throwable ->
                {
                    Timber.e("Данные посещений загружены с ошибкой: " + throwable.getMessage());
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
        getCompositeDisposable().add(getDataHelper()
                .getCenterApiCall()
                .map(response->{
                    CenterResponse center = new CenterResponse();
                    List<CenterResponse> ar = new ArrayList<>();
                    ar.addAll(response.getResponse());
                    center = ar.get(0);
                    return center;
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (response != null)
                    {
                        Timber.d("Данные центра загружены успешно: " + response.getTitle() + " " + response.getPhone());
                        getMvpView().updateHeader(response);
                        saveCenterInfo(response);
                    }
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
            Timber.d("Успешное чтение данных из SharedPreference: " + idUser + " " + fbToken);
            if (fbToken != null && idUser != 0)
            {
                getMvpView().runSendRegistrationService(fbToken, idUser);
                Timber.d("Запуск сервиса для отправки токена на сервер: " + idUser + " " + fbToken);
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
        Timber.d("Сохранение CenterResponse в локальное хранилище");
        getCompositeDisposable().add(getDataHelper().saveRealmCenter(response)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(() ->
                        Timber.d("Данные успешно сохранены в локальное хранилище"),
                        throwable -> Timber.e("Ошибка сохранения CenterResponse в локальное хранилище: " + throwable.getMessage())));
    }

    @Override
    public CenterResponse getCenterInfo()
    {
        final CenterResponse[] center = {null};
        Timber.d("Сохранение списка сообщений в локальное хранилище");
         getCompositeDisposable().add(getDataHelper().getRealmCenter()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(centerResponse -> {
                    Timber.d("Данные успешно загружены из локального хранилища");
                    center[0] = centerResponse;
                }));
        return center[0];
    }
}
