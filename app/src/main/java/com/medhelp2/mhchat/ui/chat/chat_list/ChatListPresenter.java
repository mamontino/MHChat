package com.medhelp2.mhchat.ui.chat.chat_list;


import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.MessageResponse;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class ChatListPresenter<V extends ChatListViewHelper> extends BasePresenter<V>
        implements ChatListPresenterHelper<V>
{
    @Inject
    public ChatListPresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadMessageList(int idChat)
    {
        if (getDataHelper().hasNetwork())
        {
            loadMessagesFromServer(idChat);
        } else
        {
            getMvpView().showError(R.string.connection_error);
            loadLocalMessages(idChat);
        }
    }

    @Override
    public void updateMessageList(int idChat)
    {
        if (getDataHelper().hasNetwork())
        {
            loadMessagesFromServer(idChat);
        } else
        {
            getMvpView().showError(R.string.connection_error);
        }
    }

    @Override
    public void loadLocalMessages(int idChat)
    {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().getRealmMessageList(idChat)
                .subscribeOn(getSchedulerProvider().computation())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    Timber.d(response.toString());
                    getMvpView().hideLoading();
                    getMvpView().updateMessageList(response);

                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().showErrorScreen();
                }));
    }

    @Override
    public void loadMessagesFromServer(int idRoom)
    {
        getMvpView().showLoading();

        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().getLastMessage(idRoom)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> loadMessages(idRoom, response.getIdMessage())
                       , throwable -> loadMessages(idRoom, 0)
                ));
    }

    private void loadMessages(int idRoom, int idMessage){

        Timber.e("loadMessages: " + idRoom + " : " + idMessage);

        getCompositeDisposable().add(getDataHelper().getMessageListApiCall(idRoom, idMessage)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    Timber.d("Загрузка сообщений из сети произошла успешно: " + idMessage);
                    if (!isViewAttached())
                    {
                        return;
                    }
                    saveMessages(response.getResponse(), idRoom);
                    getMvpView().stopRefreshing();
                }, throwable ->
                {
                    Timber.d("Загрузка сообщений из сети произошла с ошибкой: " + idMessage);
                    if (!isViewAttached())
                    {
                        return;
                    }
                    loadLocalMessages(idRoom);
                    getMvpView().stopRefreshing();
                    getMvpView().showErrorScreen();
                }));
    }

    private void saveMessages(List<MessageResponse> response, int idRoom)
    {
        getCompositeDisposable().add(getDataHelper().saveRealmMessages(response, idRoom)
                .subscribeOn(getSchedulerProvider().computation())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(() ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                    loadLocalMessages(idRoom);
                }, throwable -> Timber.e("Ошибка сохранения списка сообщений в локальное хранилище: " + throwable.getMessage())));
    }

    @Override
    public void sendMessage(int idRoom, String message)
    {
        Timber.d("Отправка сообщения");
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().sendMessageApiCall(idRoom, message)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((response) ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }, throwable ->
                {
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void readMessages(int idRoom)
    {
        getCompositeDisposable().add(getDataHelper().readMessagesApiCall(idRoom)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                        Timber.d("sendReadMessages: успешная отправка запроса на прочтение сообщений: " + response.getMessage()), throwable ->
                        Timber.e("sendReadMessages: отправка запроса на прочтение сообщений: " + throwable.getMessage())));
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
