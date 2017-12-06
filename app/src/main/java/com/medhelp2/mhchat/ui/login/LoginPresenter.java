package com.medhelp2.mhchat.ui.login;

import android.arch.lifecycle.LifecycleObserver;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.UserResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class LoginPresenter<V extends LoginViewHelper> extends BasePresenter<V> implements LoginPresenterHelper<V>, LifecycleObserver
{
    @Inject
    public LoginPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLoginClick(String username, final String password)
    {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
        {
            Timber.d("Пустое значение");
            getMvpView().showError(R.string.empty_data);
            return;
        }

        if (getDataHelper().checkNetwork())
        {
            Timber.d("Инернет соединение... Верификация пользователя");
            verifyUser(username, password);
        } else
        {
            Timber.d("Инернет соединение отсутствует");
            getMvpView().showError(R.string.connection_error);
        }
    }



    private void verifyUser(String username, String password)
    {
        Timber.d("Верификация пользователя на сервере");
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().doLogin(username, password)
                .subscribeOn(getSchedulerProvider().io())
                .map((response) ->
                {
                    UserResponse userResponse;
                    List ar = response.getResponse();
                    userResponse = (UserResponse) ar.get(0);
                    Timber.d(response.getMessage());
                    return userResponse;
                })
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    Timber.d("Данные пользователя загружены успешно: " + response.getIdUser() + " " + response.getUsername());

                    if (!isViewAttached())
                    {
                        return;
                    }
                    saveUserPref(response);

                    if (getMvpView().isNeedSave())
                    {
                        savePrivateData(username, password);
                    } else
                    {
                        cleanPassword();
                    }
                    getMvpView().hideLoading();
                    getMvpView().closeActivity();
                    getMvpView().openProfileActivity();
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    Timber.d("Данные пользователя были загружены с ошибкой: " + throwable.getMessage());
                    getMvpView().hideLoading();
                    getMvpView().showError("Данные пользователя были загружены с ошибкой: " + throwable.getMessage());
                }));
    }

    private void cleanPassword()
    {
        try
        {
            getDataHelper().deleteCurrentPassword();
            Timber.e("Успешное удаление пароля из SharedPreference");
        } catch (Exception e)
        {
            Timber.e("Ошибка удаления пароля из SharedPreference: " + e.getMessage());
            getMvpView().showError("Ошибка удаления пароля из SharedPreference: " + e.getMessage());
        }
    }

    private void savePrivateData(String username, String password)
    {
        try
        {
            getDataHelper().setCurrentPassword(password);
            getDataHelper().setCurrentUserName(username);
            Timber.d("Успешное сохранение пароля в SharedPreference");
        } catch (Exception e)
        {
            Timber.e("Ошибка сохранения пароля в SharedPreference: " + e.getMessage());
            getMvpView().showError("Ошибка сохранения пароля в SharedPreference: " + e.getMessage());
        }
    }

    private void saveUserPref(UserResponse response)
    {
        try
        {
            getDataHelper().setCurrentUserId(response.getId());
            getDataHelper().setCurrentUserName(response.getUsername());
            getDataHelper().setCurrentCenterId(response.getIdCenter());
            getDataHelper().setCurrentUserName(response.getUsername());
            getDataHelper().setAccessToken(response.getApiKey());
            Timber.d("Успешное сохранение пользовательских данных в SharedPreference: "
                    + response.getIdUser() + " " + response.getUsername());
        } catch (Exception e)
        {
            Timber.e("Ошибка сохранения пользовательских данных в SharedPreference: " + e.getMessage());
            getMvpView().showError("Ошибка сохранения пользовательских данных в SharedPreference: " + e.getMessage());
        }
    }
}
