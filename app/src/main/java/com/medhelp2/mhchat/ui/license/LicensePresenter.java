package com.medhelp2.mhchat.ui.license;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LicensePresenter<V extends LicenseViewHelper> extends BasePresenter<V>
        implements LicensePresenterHelper<V>
{
    @Inject
    LicensePresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void confirmLicense()
    {
        getDataHelper().setStartMode(AppConstants.NOT_FIRST_START);
        getMvpView().showLoginActivity();
    }
}
