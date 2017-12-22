package com.medhelp2.mhchat.bg;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.data.DataHelper;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class SyncService extends Service
{
    public static final String REGISTRATION_TOKEN = "REGISTRATION_TOKEN";
    public static final String USER_REGISTRATION_ID = "USER_REGISTRATION_ID";

    private IBinder binder = new SyncService.SyncServiceBinder();
    private BroadcastReceiver isCon;

    @Inject
    DataHelper dataManager;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, SyncService.class);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Timber.d("SyncService onCreate");
        MainApp app = (MainApp) getApplication();
        app.getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Timber.d("SyncService onStartCommand");

        assert intent != null;
        String token = intent.getStringExtra(REGISTRATION_TOKEN);
        int userId = intent.getIntExtra(USER_REGISTRATION_ID, 0);
        if (token != null && !token.trim().equals(""))
        {
            Timber.d("Сохранение токена");
            saveToken(token);
        }
        if (userId != 0)
        {
            Timber.d("Отправка токена на сервер для пользователя: " + userId);
            sendToken();
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy()
    {
        Timber.d("SyncService onDestroy");
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
        Timber.d("SyncService onBindButton");
        return binder;
    }

    public class SyncServiceBinder extends Binder
    {
        public SyncService getService()
        {
            return SyncService.this;
        }
    }

    public void saveToken(String token)
    {
        Timber.d("Сохранение токена в SharedPreference: " + token);
        try
        {
            dataManager.setFireBaseToken(token);
            stopSelf();

        } catch (Exception e)
        {
            Timber.e("Ошибка сохранения токена: " + e.getMessage());
            stopSelf();
        }
    }

    public void sendToken()
    {
        String token = null;
        try
        {
            token = dataManager.getFireBaseToken();
        } catch (Exception e)
        {
            Timber.d("Ошибка чтение токена: " + e.getMessage());
        }
        Timber.d("Отправка токена на сервер: " + token);
        Timber.d("UserId: " + dataManager.getCurrentUserId());
        if (dataManager.getCurrentUserId() != 0 && dataManager.getFireBaseToken() != null && !dataManager.getFireBaseToken().trim().equals(""))
        {
            new CompositeDisposable().add(dataManager.sendTokenToServerApiCall(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response ->
                            {
                                Timber.d("Токен успешно обновлен на сервере: " + response.getResponse());
                                stopSelf();
                            }
                            , throwable ->
                            {
                                Timber.d("Токен не был загружен на сервер: " + throwable.getMessage());

                                if (!dataManager.checkNetwork())
                                {
                                    noConnectionSend();
                                } else
                                {
                                    Timber.e("Непредвиденная ошибка отправки токена на сервер");
                                    stopSelf();
                                }
                            }
                    ));
        } else
        {
            Timber.e("Пустое значение идентификатора пользователя");
        }
    }

    private void noConnectionSend()
    {
        Timber.e("Отсутствует соединение с интернетом, повторная отправка токена");

        if (isCon == null)
        {
            isCon = new BroadcastReceiver()
            {
                @Override
                public void onReceive(Context context, Intent intent)
                {
                    if (dataManager.checkNetwork())
                    {
                        sendToken();
                    }
                }
            };
            IntentFilter filters = new IntentFilter();
            filters.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(isCon, filters);
        }
    }
}

