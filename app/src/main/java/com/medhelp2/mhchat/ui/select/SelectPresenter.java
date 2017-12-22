package com.medhelp2.mhchat.ui.select;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class SelectPresenter<V extends SelectViewHelper> extends BasePresenter<V>
        implements SelectPresenterHelper<V>
{
    @Inject
    SelectPresenter(
            DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void updateData(int idService)
    {
        getMvpView().updateView(loadDoctors(idService), loadSpecialty(idService), loadService(idService));
    }

    private List<ServiceResponse> loadService(int idService)
    {
        return null;
    }

    private List<CategoryResponse> loadSpecialty(int idService)
    {
        return null;
    }

    private List<Doctor> loadDoctors(int idService)
    {
        List<Doctor> list = new ArrayList<>();
        getCompositeDisposable().add(getDataHelper()
                .getStaffApiCall(idService)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (!response.isError())
                    {
                        list.addAll(response.getResponse());
                    }
                    Timber.d("getPrice response == null or response.getServices() == null");
                    getMvpView().hideLoading();
                }, throwable ->
                {
                    Timber.d("getPrice загрузка прошла с ошибкой: " + throwable.getMessage());
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
        return list;
    }
}
