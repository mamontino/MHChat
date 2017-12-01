package com.medhelp2.mhchat.di.component;

import android.app.Application;
import android.content.Context;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.di.module.AppModule;
import com.medhelp2.mhchat.di.scope.PerApplication;
import com.medhelp2.mhchat.bg.MessagingService;
import com.medhelp2.mhchat.bg.SyncService;

import dagger.Component;

@PerApplication
@Component(modules = {AppModule.class})
public interface AppComponent
{
    void inject(MainApp app);

    void inject(MessagingService service);

    void inject(FirebaseMessagingService service);

    void inject(SyncService service);

    @PerApplication
    Context context();

    Application application();

    DataHelper getDataManager();
}