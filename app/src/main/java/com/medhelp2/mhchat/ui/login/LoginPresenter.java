package com.medhelp2.mhchat.ui.login;

import android.arch.lifecycle.LifecycleObserver;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CenterList;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.UserResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginViewHelper> extends BasePresenter<V> implements LoginPresenterHelper<V>, LifecycleObserver
{
    @Inject
    LoginPresenter(DataHelper dataHelper,
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
            getMvpView().showError(R.string.empty_data);
            return;
        }

        if (getDataHelper().hasNetwork())
        {
            verifyUser(username, password);
        } else
        {
            getMvpView().showError(R.string.connection_error);
        }
    }

    @Override
    public void getUsername()
    {
        try
        {
            getMvpView().updateUsernameHint(getDataHelper().getCurrentUserName());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void verifyUser(String username, String password)
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().doLoginApiCall(username, password)
                .subscribeOn(getSchedulerProvider().io())
                .map(response ->
                {
                    UserResponse userResponse;
                    List ar = response.getResponse();
                    userResponse = (UserResponse) ar.get(0);
                    return userResponse;
                })
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
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
                    getCenterInfo();
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().showError("Данные пользователя были загружены с ошибкой: " + throwable.getMessage());
                }));
    }

    private void cleanPassword()
    {
        try
        {
            getDataHelper().deleteCurrentPassword();
        } catch (Exception e)
        {
            getMvpView().showError("Ошибка удаления пароля: " + e.getMessage());
        }
    }

    private void savePrivateData(String username, String password)
    {
        try
        {
            getDataHelper().setCurrentPassword(password);
            getDataHelper().setCurrentUserName(username);
        } catch (Exception e)
        {
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
        } catch (Exception e)
        {
            getMvpView().showError("Не удалось сохранить данные на устройстве");
        }
    }

    private void getCenterInfo()
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper()
                .getCenterApiCall()
                .map(CenterList::getResponse)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> saveCenterInfo(response.get(0)), throwable ->
                        {
                            getMvpView().hideLoading();
                            getMvpView().openProfileActivity();
                        }
                ));
    }

    private void saveCenterInfo(CenterResponse response)
    {
        getCompositeDisposable().add(getDataHelper().saveRealmCenter(response)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(() ->
                {
                    getMvpView().hideLoading();
                    getMvpView().openProfileActivity();
                    getMvpView().closeActivity();
                }, throwable -> getMvpView().hideLoading()));
    }
}
