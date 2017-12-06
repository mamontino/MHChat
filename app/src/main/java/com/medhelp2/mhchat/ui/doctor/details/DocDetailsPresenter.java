package com.medhelp2.mhchat.ui.doctor.details;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DocDetailsPresenter<V extends DocDetailsViewHelper> extends BasePresenter<V>
        implements DocDetailsPresenterHelper<V>
{
    @Inject
    DocDetailsPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadDocInfo()
    {

    }

    @Override
    public void onScheduleClicked()
    {
        getMvpView().dismissDialog();
    }

    @Override
    public void onRecordClicked()
    {
        getMvpView().dismissDialog();
    }
}
