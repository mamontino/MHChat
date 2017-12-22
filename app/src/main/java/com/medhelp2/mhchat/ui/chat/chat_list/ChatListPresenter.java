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
        Timber.d("Загрузка списка сообщений");
        if (getDataHelper().checkNetwork())
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
        Timber.d("updateMessageList: Загрузка списка сообщений");
        if (getDataHelper().checkNetwork())
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
        Timber.d("Загрузка сообщений из локального хранилища для idChat: " + idChat);
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().getRealmMessageList(idChat)
                .subscribeOn(getSchedulerProvider().computation())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    Timber.d("Загрузка сообщений произошла успешно");
                    if (!isViewAttached())
                    {
                        return;
                    }
                    Timber.d(response.toString());
                    getMvpView().hideLoading();
                    getMvpView().updateMessageList(response);

                }, throwable ->
                {
                    Timber.e("Загрузка сообщений произошла с ошибкой" + throwable.getMessage());
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }));
    }

    @Override
    public void loadMessagesFromServer(int idRoom)
    {
        Timber.d("Загрузка сообщений из сети");
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataHelper().getMessageListApiCall(idRoom)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response ->
                {
                    Timber.d("Загрузка сообщений из сети произошла успешно");
                    if (!isViewAttached())
                    {
                        return;
                    }
                    saveMessages(response.getResponse(), idRoom);
                }, throwable ->
                {
                    Timber.d("Загрузка сообщений из сети произошла с ошибкой");
                    if (!isViewAttached())
                    {
                        return;
                    }
                    loadLocalMessages(idRoom);
                }));
    }

    private void saveMessages(List<MessageResponse> response, int idRoom)
    {
        Timber.d("Сохранение списка сообщений в локальное хранилище");
        getCompositeDisposable().add(getDataHelper().saveRealmMessages(response, idRoom)
                .subscribeOn(getSchedulerProvider().computation())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(() ->
                {
                    Timber.d("Данные успешно сохранены в локальное хранилище");
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
                    Timber.d("Успешная отправка сообщения");
                    if (!isViewAttached())
                    {
                        return;
                    }
                    getMvpView().hideLoading();
                }, throwable ->
                {
                    Timber.d("Отправка сообщения произошла с ошибкой: " + throwable.getMessage());
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
}
