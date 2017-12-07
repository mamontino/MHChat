package com.medhelp2.mhchat.ui.doctor.details;


import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

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
    public void loadDocInfo(int idDoctor)
    {
        if (getMvpView().isNetworkConnected()){
            Timber.d("Получение информации о докторе из сети");
            getCompositeDisposable().add(getDataHelper().getDoctorApiCall(idDoctor)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response ->
                    {
                        try
                        {
                            Timber.d("Данные успешно загружены из сети");
                            getMvpView().updateDocInfo(response);
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }, throwable -> Timber.d("Данные из сети загружены с ошибкой: " + throwable.getMessage())));
        }else{
            getMvpView().showError(R.string.connection_error);
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
