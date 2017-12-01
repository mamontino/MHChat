package com.medhelp2.mhchat.ui.about;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AboutPresenter<V extends AboutViewHelper> extends BasePresenter<V>
        implements AboutPresenterHelper<V>
{

    @Inject
    public AboutPresenter(DataHelper dataHelper,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }
}
