package com.medhelp2.mhchat.ui.exit;


import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.DoctorInfoList;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class ExitPresenter<V extends ExitViewHelper> extends BasePresenter<V>
        implements ExitPresenterHelper<V>
{
    @Inject
    ExitPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    private Disposable disposable;

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
                Timber.d("Получение информации о докторе из сети: \n" +
                        "idDoctor: " + idDoctor);

                disposable = getDataHelper().getDoctorApiCall(idDoctor)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribeWith(new DisposableObserver<DoctorInfoList>()
                        {
                            @Override
                            public void onNext(DoctorInfoList doctors)
                            {
                                Timber.e("Новый элемент: " + doctors.getMessage());
                                Timber.e("Размер: " + doctors.getResponse().size());
                                getMvpView().updateDocInfo(doctors.getResponse().get(0));
                            }

                            @Override
                            public void onError(Throwable e)
                            {
                                Timber.e("Данные из сети загружены с ошибкой: " + e.getMessage());
                            }

                            @Override
                            public void onComplete()
                            {
                                Timber.e("onComplete");
                            }
                        });
                getMvpView().hideLoading();

            } else
            {
                getMvpView().hideLoading();
                getMvpView().showError(R.string.connection_error);
            }
        }
//        disposable.dispose();
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
