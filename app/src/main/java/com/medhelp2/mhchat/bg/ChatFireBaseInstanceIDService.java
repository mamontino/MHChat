package com.medhelp2.mhchat.bg;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.medhelp2.mhchat.bg.SyncService.REGISTRATION_TOKEN;

public class ChatFireBaseInstanceIDService extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh()
    {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        saveRegistration(refreshedToken);
    }

    private void saveRegistration(String token)
    {
        assert token != null;
        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(REGISTRATION_TOKEN, token);
        startService(intent);
    }
}