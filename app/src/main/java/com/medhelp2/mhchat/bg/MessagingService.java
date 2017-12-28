package com.medhelp2.mhchat.bg;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.NotificationMessage;
import com.medhelp2.mhchat.ui.chat.chat_list.ChatListFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.medhelp2.mhchat.ui.chat.chat_list.ChatListFragment.BROADCAST_OUTGOING_MESSAGE;


public final class MessagingService extends Service
{
    @Inject
    DataHelper dataManager;

    public static final String SERVICE_MESSAGE = "SERVICE_MESSAGE";
    public static final String SERVICE_USER_ID = "SERVICE_USER_ID";
    public static final String SERVICE_CHAT_ROOM = "SERVICE_CHAT_ROOM";

    private BroadcastReceiver isCon;
    private static ArrayList<NotificationMessage> msgQueue = new ArrayList<>();

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, MessagingService.class);
    }

    @Override
    public void onCreate()
    {
        Timber.d("MessagingService onCreate");
        super.onCreate();
        MainApp app = (MainApp) getApplication();
        app.getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Timber.d("MessagingService onStartCommand");
        int idUser = intent.getIntExtra(SERVICE_USER_ID, 0);
        int idChatRoom = intent.getIntExtra(SERVICE_CHAT_ROOM, 0);
        String message = intent.getStringExtra(SERVICE_MESSAGE);
        NotificationMessage notificationMessage = new NotificationMessage(message, idChatRoom, idUser);
        addMessage(notificationMessage);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy()
    {
        Timber.d("MessagingService onDestroy");
        if (isCon != null)
        {
            unregisterReceiver(isCon);
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Timber.d("MessagingService onBindButton");
        return null;
    }

    public void addMessage(NotificationMessage body)
    {
        if (msgQueue.contains(body))
        {
            Timber.d("Элемент уже находится в очереди" + body.getMessage());
        } else
        {
            msgQueue.add(body);
            Timber.d("Добавлен новый элемент в очередь: " + body.getMessage());
        }

        if (dataManager.hasNetwork())
        {
            sendMessageToServer();
        } else
        {
            runConnectionReceiver();
        }
    }

    private void runConnectionReceiver()
    {
        Timber.e("Отсутствует соединение с интернетом runConnectionReceiver");
        if (isCon == null)
        {
            isCon = new BroadcastReceiver()
            {
                @Override
                public void onReceive(Context context, Intent intent)
                {
                    if (dataManager.hasNetwork())
                    {
                        sendMessageToServer();
                    }
                }
            };
            IntentFilter filters = new IntentFilter();
            filters.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(isCon, filters);
        }
    }

    void sendMessageToServer()
    {
        Timber.d("sendMessageToServer ");

        for (NotificationMessage body: msgQueue)
        {
            Timber.d("Отправка сообщения: " + body.getMessage());

            int room = body.getIdChatRoom();
            String mess = body.getMessage();

            new CompositeDisposable().add(dataManager.sendMessageApiCall(room, mess)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response ->
                    {
                        msgQueue.remove(body);
                        Timber.d("Сообщение успешно отправлено");
                        if (msgQueue.size() == 0)
                        {
                            Intent intent = new Intent(BROADCAST_OUTGOING_MESSAGE);
                            intent.putExtra(ChatListFragment.PARAM_STATUS, ChatListFragment.STATUS_FINISH);
                            sendBroadcast(intent);
                            Timber.d("Очередь отправки сообщений isEmpty");
                            stopSelf();
                        }
                    }, throwable ->
                    {
                        Timber.d("Отправка сообщения с ошибкой: " + throwable.getMessage());

                        if (!dataManager.hasNetwork())
                        {
                            runConnectionReceiver();
                        } else
                        {
                            Timber.e("Непредвиденная ошибка отправки сообщения");
                        }
                    }));
        }
    }
}



