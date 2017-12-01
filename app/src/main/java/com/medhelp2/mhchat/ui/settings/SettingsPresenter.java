package com.medhelp2.mhchat.ui.settings;

import com.medhelp2.mhchat.utils.rx.SchedulerProvider;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SettingsPresenter<V extends SettingsViewHelper> extends BasePresenter<V> implements SettingsPresenterHelper<V> {

    private static final String TAG = "ChatListPresenter";

    @Inject
    SettingsPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable) {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadUserInfo() {

    }

    @Override
    public void loadUserInfoLocal() {

    }

    @Override
    public void doLogOut() {

    }
}
