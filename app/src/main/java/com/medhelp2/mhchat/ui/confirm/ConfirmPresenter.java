package com.medhelp2.mhchat.ui.confirm;


import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.DoctorInfoList;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ConfirmPresenter<V extends ConfirmViewHelper> extends BasePresenter<V>
        implements ConfirmPresenterHelper<V>
{
    @Inject
    ConfirmPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @SuppressWarnings("unused")
    @Override
    public void loadDocInfo(int idDoctor)
    {
        getMvpView().showLoading();

        if (idDoctor == 0)
        {
            getMvpView().showError(R.string.connection_error);
        } else
        {
            if (getMvpView().isNetworkConnected())
            {
                Disposable disposable = getDataHelper().getDoctorApiCall(idDoctor)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeWith(new DisposableObserver<DoctorInfoList>()
                        {
                            @Override
                            public void onNext(DoctorInfoList doctors)
                            {

                                getMvpView().updateDocInfo(doctors.getResponse().get(0));
                            }

                            @Override
                            public void onError(Throwable e)
                            {
                                getMvpView().showError(R.string.data_load_network_err);
                                getMvpView().dismissDialog();
                            }

                            @Override
                            public void onComplete()
                            {
                            }
                        });
                getMvpView().hideLoading();

            } else
            {
                getMvpView().hideLoading();
                getMvpView().showError(R.string.connection_error);
            }
        }
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
