package com.medhelp2.mhchat.ui.login;

import com.medhelp2.mhchat.utils.rx.SchedulerProvider;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginViewHelper> extends BasePresenter<V> implements LoginPresenterHelper<V>
{
    @Inject
    public LoginPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }
}
