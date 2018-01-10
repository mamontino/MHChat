package com.medhelp2.mhchat.ui.contacts;


import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class ContactsPresenter<V extends ContactsViewHelper> extends BasePresenter<V> implements ContactsPresenterHelper<V>
{

    @Inject
    ContactsPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
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
                        getMvpView().updateHeader(centerResponse);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }, throwable -> Timber.d("Данные из локального хранилища загружены с ошибкой")));
    }

    @Override
    public void updateUserList()
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().getRoomListApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    saveUserList(response);
                    getMvpView().hideLoading();
                    getMvpView().updateUserListData(response);
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().showError(R.string.data_load_network_err);
                    getMvpView().showErrorScreen();
                }));
    }

    @Override
    public void removePassword()
    {
        getDataHelper().setCurrentPassword("");
    }

    //    @Override
    //    public void updateUnread()
    //    {
    //        getCompositeDisposable().add(getDataHelper().getUnreadCountApiCall()
    //                .subscribeOn(getSchedulerProvider().io())
    //                .observeOn(getSchedulerProvider().ui())
    //                .subscribe(response ->
    //                {
    //                    if (!isViewAttached())
    //                    {
    //                        return;
    //                    }
    //                    Timber.d("Данные успешно загружены в updateAnaliseList");
    //                    Timber.d(response.toString());
    //                    saveUserListAndUpdate(response);
    //                }, throwable ->
    //                {
    //                    if (!isViewAttached())
    //                    {
    //                        return;
    //                    }
    //                    getMvpView().hideLoading();
    //                    Timber.e("Загрузка данных в updateAnaliseList произошла с ошибкой: " + throwable.getMessage());
    //                }));
    //    }

    //    @Override
    //    public void loadUserInfo(int id)
    //    {
    //        getMvpView().showLoading();
    //        getCompositeDisposable().add(getDataHelper().getRoomListApiCall()
    //                .subscribeOn(getSchedulerProvider().computation())
    //                .observeOn(getSchedulerProvider().ui())
    //                .subscribe(response ->
    //                {
    //                    if (!isViewAttached())
    //                    {
    //                        return;
    //                    }
    //                    getDataHelper().saveRealmRoom(response);
    //                }, throwable ->
    //                {
    //                    if (!isViewAttached())
    //                    {
    //                        return;
    //                    }
    //                    getMvpView().hideLoading();
    //                }));
    //    }

    private void saveUserList(List<RoomResponse> response)
    {
        getCompositeDisposable().add(getDataHelper().saveRealmRoom(response)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(() ->
                        {
                            Timber.d("Данные сохранены в saveUserList");
                            loadUsersFromDb();
                        }, throwable ->
                        {
                            Timber.e("Данные не были сохранены в saveUserList" + throwable.getMessage());
                            getMvpView().showError("Ошибка загрузки данных");
                            loadUsersFromDb();
                        }

                ));
    }

    //    private void saveUserListAndUpdate(List<RoomResponse> response)
    //    {
    //        getCompositeDisposable().add(getDataHelper().saveRealmRoom(response)
    //                .subscribeOn(getSchedulerProvider().computation())
    //                .observeOn(getSchedulerProvider().computation())
    //                .subscribe(() ->
    //                        {
    //                            Timber.d("saveUserListAndUpdate");
    //                            if (!isViewAttached())
    //                            {
    //                                return;
    //                            }
    //                            getMvpView().hideLoading();
    //                            loadUsersFromDb();
    //                        }, throwable ->
    //                        {
    //                            Timber.e("Данные не были сохранены в saveUserList" + throwable.getMessage());
    //                            if (!isViewAttached())
    //                            {
    //                                return;
    //                            }
    //                            getMvpView().hideLoading();
    //                        }
    //                ));
    //    }


    private void loadUsersFromDb()
    {
        getCompositeDisposable().add(getDataHelper().getAllRealmRoom()
                .subscribeOn(getSchedulerProvider().computation())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    Timber.d("Загрузка из БД прошла успешно в loadUsersFromDb");
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().updateUserListData(response);
                }, throwable ->
                {
                    Timber.e("Ошибка загрузки из БД в loadUsersFromDb" + throwable.getMessage());
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().showErrorScreen();
                }));
    }

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
