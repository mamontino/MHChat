package com.medhelp2.mhchat.ui.details;


import com.medhelp2.mhchat.utils.rx.SchedulerProvider;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DetailsPresenter<V extends DetailsViewHelper> extends BasePresenter<V>
        implements DetailsPresenterHelper<V> {

    @Inject
    DetailsPresenter(DataHelper dataHelper,
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
}
