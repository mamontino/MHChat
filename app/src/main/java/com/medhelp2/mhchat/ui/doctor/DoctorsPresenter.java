package com.medhelp2.mhchat.ui.doctor;

import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.CategoryList;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class DoctorsPresenter<V extends DoctorsViewHelper> extends BasePresenter<V> implements DoctorsPresenterHelper<V>
{
    @Inject
    DoctorsPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void removePassword()
    {
        getDataHelper().setCurrentPassword("");
    }

    @Override
    public void getCenterInfo()
    {
        getCompositeDisposable().add(getDataHelper().getRealmCenter()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(centerResponse ->
                {
                    try
                    {
                        Timber.d("Данные успешно загружены из локального хранилища");
                        Timber.d(centerResponse.toString());
                        getMvpView().updateHeader(centerResponse);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }, throwable -> Timber.d("Данные из локального хранилища загружены с ошибкой")));
    }

    @Override
    public void getDoctorList(int idSpec)
    {
        getCompositeDisposable().add(getDataHelper().getStaffApiCall(idSpec)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    try
                    {
                        Timber.d("Данные успешно загружены из сети");
                        getMvpView().updateView(response.getResponse());
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }, throwable -> getMvpView().showErrorScreen()));
    }

    @Override
    public void getSpecialtyByCenter()
    {
        getCompositeDisposable().add(getDataHelper().getCategoryApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .map(CategoryList::getSpec)
                .subscribe(response ->
                {
                    try
                    {
                        Timber.d("Данные успешно загружены из сети");
                        getMvpView().updateSpecialty(response);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }, throwable -> getMvpView().showErrorScreen()));
    }

    //    private void saveDoctorList(List<Doctor> response)
    //    {
    //        Timber.d("Сохранение списка докторов в локальное хранилище");
    //        getCompositeDisposable().add(getDataHelper().saveRealmStaff(response)
    //                .subscribeOn(getSchedulerProvider().io())
    //                .observeOn(getSchedulerProvider().ui())
    //                .subscribe(() ->
    //                {
    //                    try
    //                    {
    //                        Timber.d("Данные успешно сохранены в локальное хранилище");
    //                        getLocalDoctorList();
    //                    } catch (Exception e)
    //                    {
    //                        e.printStackTrace();
    //                    }
    //                }, throwable -> Timber.d("Данные в локальное хранилище не были сохранены, ошибка: " + throwable.getMessage())));
    //    }
    //
    //    private void getLocalDoctorList()
    //    {
    //        Timber.d("Получение списка докторов из локального хранища");
    //        getCompositeDisposable().add(getDataHelper().getRealmStaff()
    //                .subscribeOn(getSchedulerProvider().io())
    //                .observeOn(getSchedulerProvider().ui())
    //                .subscribe(response ->
    //                {
    //                    try
    //                    {
    //                        Timber.d("Данные успешно загружены из локального хранилища");
    //                        getMvpView().updateView(response);
    //                    } catch (Exception e)
    //                    {
    //                        e.printStackTrace();
    //                    }
    //                }, throwable -> Timber.d("Данные из локального хранилища загружены с ошибкой: " + throwable.getMessage())));
    //    }

    @Override
    public void unSubscribe()
    {
        if (!getCompositeDisposable().isDisposed())
        {
            getCompositeDisposable().dispose();
            getMvpView().hideLoading();
        }
    }
}
