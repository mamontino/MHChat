package com.medhelp2.mhchat.ui.search.select;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SelectPresenter<V extends SelectViewHelper> extends BasePresenter<V>
        implements SelectPresenterHelper<V>
{
    @Inject
    SelectPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadDocList(int idService)
    {

    }
}
