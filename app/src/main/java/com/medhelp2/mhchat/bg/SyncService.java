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
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.rx.RxEvents;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class SyncService extends Service
{
    public static final String REGISTRATION_TOKEN = "REGISTRATION_TOKEN";
    public static final String USER_REGISTRATION_ID = "USER_REGISTRATION_ID";
    public static final String REVIEW_MESSAGE = "REVIEW_MESSAGE";
    public static final String REVIEW_RATING = "REVIEW_RATING";

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
        MainApp app = (MainApp) getApplication();
        app.getComponent().inject(this);
        changeConnectionFlag();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        assert intent != null;
        String token = intent.getStringExtra(REGISTRATION_TOKEN);
        int userId = intent.getIntExtra(USER_REGISTRATION_ID, 0);

        if (token != null && !token.trim().equals(""))
        {
            saveToken(token);
        }

        if (userId != 0)
        {
            sendToken();
        }

        String message = intent.getStringExtra(REVIEW_MESSAGE);
        int rating = intent.getIntExtra(REVIEW_RATING, 0);

        if (message != null && rating != 0)
        {
            sendReview(message, rating);
        }

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy()
    {
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
        try
        {
            dataManager.setFireBaseToken(token);

        } catch (Exception e)
        {
            e.printStackTrace();
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

        if (dataManager.getCurrentUserId() != 0 && dataManager
                .getFireBaseToken() != null && !dataManager
                .getFireBaseToken().trim().equals(""))
        {
            new CompositeDisposable().add(dataManager
                    .sendTokenToServerApiCall(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response ->
                            Timber.d("Токен успешно обновлен на сервере: " + response.getResponse()), throwable ->
                    {
                        Timber.d("Токен не был загружен на сервер: " + throwable.getMessage());

                        if (!dataManager.hasNetwork())
                        {
                            noConnectionSend();
                        } else
                        {
                            Timber.e("Непредвиденная ошибка отправки токена на сервер");
                            stopSelf();
                        }
                    }));
        } else
        {
            Timber.e("Пустое значение идентификатора пользователя");
        }
    }

    public void sendReview(String message, int rating)
    {
        new CompositeDisposable().add(dataManager
                .sendReviewToServerApiCall(message, rating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Timber.d("sendReview"), throwable ->
                {
                    if (!dataManager.hasNetwork())
                    {
                        noConnectionSend();
                    }
                }));
    }

    private void noConnectionSend()
    {
        if (isCon == null)
        {
            isCon = new BroadcastReceiver()
            {
                @Override
                public void onReceive(Context context, Intent intent)
                {
                    if (dataManager.hasNetwork())
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

    /**
     *  При изменении соостояния сетевого соединения меняет флаг NETWORK_MODE
     */
    private void changeConnectionFlag()
    {
        if (isCon == null)
        {
            isCon = new BroadcastReceiver()
            {
                @Override
                public void onReceive(Context context, Intent intent)
                {
                    if (dataManager.hasNetwork())
                    {
                        dataManager.setNetworkMode(AppConstants.NETWORK_MODE);
                        ((MainApp) getApplication())
                                .bus()
                                .send(new RxEvents.hasConnection());
                        Timber.d("NETWORK_MODE");
                    } else
                    {
                        dataManager.setNetworkMode(AppConstants.NOT_NETWORK_MODE);
                        ((MainApp) getApplication())
                                .bus()
                                .send(new RxEvents.noConnection());
                        Timber.d("NOT_NETWORK_MODE");
                    }
                }
            };

            IntentFilter filters = new IntentFilter();
            filters.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(isCon, filters);
        }
    }
}

