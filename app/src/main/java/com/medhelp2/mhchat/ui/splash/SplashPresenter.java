package com.medhelp2.mhchat.ui.splash;

import com.medhelp2.mhchat.data.DataHelper;
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
        Timber.d("onAttachPresenter");
        super.onAttach(mvpView);
        openNextActivity();
    }

    private void openNextActivity()
    {
        Timber.d("openNextActivity");

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

        if (username != null && password != null)
        {
            getMvpView().openProfileActivity();
        } else
        {
            getMvpView().openLoginActivity();
        }
    }
}
