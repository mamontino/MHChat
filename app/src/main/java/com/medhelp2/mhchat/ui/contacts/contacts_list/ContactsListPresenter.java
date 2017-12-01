package com.medhelp2.mhchat.ui.contacts.contacts_list;


import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class ContactsListPresenter<V extends ContactsListViewHelper> extends BasePresenter<V>
        implements ContactsListPresenterHelper<V>
{
    @Inject
    ContactsListPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void updateUserList()
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().getRoomList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    Timber.d("Данные успешно загружены в updateUserList");
                    Timber.d(response.toString());

                    saveUserList(response);
//                    updateUnread();
                    getMvpView().hideLoading();
                    getMvpView().updateUserListData(response);

                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    Timber.e("Загрузка данных в updateUserList произошла с ошибкой: " + throwable.getMessage());
                }));
    }

//    @Override
//    public void updateUnread()
//    {
//        getCompositeDisposable().add(getDataHelper().getUnreadCount()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(response ->
//                {
//                    if (!isViewAttached())
//                    {
//                        return;
//                    }
//                    Timber.d("Данные успешно загружены в updateUserList");
//                    Timber.d(response.toString());
//                    saveUserListAndUpdate(response);
//                }, throwable ->
//                {
//                    if (!isViewAttached())
//                    {
//                        return;
//                    }
//                    getMvpView().hideLoading();
//                    Timber.e("Загрузка данных в updateUserList произошла с ошибкой: " + throwable.getMessage());
//                }));
//    }

//    @Override
//    public void loadUserInfo(int id)
//    {
//        getMvpView().showLoading();
//        getCompositeDisposable().add(getDataHelper().getRoomList()
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
                }));
    }
}
