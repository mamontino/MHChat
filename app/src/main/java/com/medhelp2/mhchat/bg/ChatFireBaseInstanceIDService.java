package com.medhelp2.mhchat.bg;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import timber.log.Timber;

import static com.medhelp2.mhchat.bg.SyncService.REGISTRATION_TOKEN;

public class ChatFireBaseInstanceIDService extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh()
    {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Timber.d("Обновленный токен: " + refreshedToken);
        saveRegistration(refreshedToken);
    }

    private void saveRegistration(String token)
    {
        Timber.d(token);
        if (token != null || !token.equals("")){
            Timber.d("Сохранение токена в SharedPreference: " + token);
            Intent intent = SyncService.getStartIntent(this);
            intent.putExtra(REGISTRATION_TOKEN, token);
            startService(intent);
        }
    }
}