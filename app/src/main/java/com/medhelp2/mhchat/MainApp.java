package com.medhelp2.mhchat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.crashlytics.android.Crashlytics;
import com.medhelp2.mhchat.di.component.AppComponent;
import com.medhelp2.mhchat.di.component.DaggerAppComponent;
import com.medhelp2.mhchat.di.module.AppModule;
import com.medhelp2.mhchat.ui.chat.ChatActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.utils.rx.RxBus;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import timber.log.Timber;

public class MainApp extends Application implements Application.ActivityLifecycleCallbacks
{
    private AppComponent appComponent;
    private  RxBus bus;
    private int activityCount = 0;
    private boolean isChatActivityStarted = false;
    private boolean isContactsActivityStarted = false;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        registerActivityLifecycleCallbacks(this);

        Realm.init(this);

        bus = new RxBus();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
    }

    public AppComponent getComponent()
    {
        return appComponent;
    }

    public void setComponent(AppComponent appComponent)
    {
        this.appComponent = appComponent;
    }

    public RxBus bus()
    {
        return bus;
    }

    public boolean isAppForeground()
    {
        return activityCount > 0;
    }

    public boolean getChatActivityStarted()
    {
        return isChatActivityStarted;
    }

    public boolean getContactsActivityStarted()
    {
        return isContactsActivityStarted;
    }

    @Override
    public void onActivityStopped(Activity activity)
    {

    }

    @Override
    public void onActivityStarted(Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {

    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        activityCount++;
        if (activity instanceof ChatActivity)
        {
            isChatActivityStarted = true;
        }
    }

    @Override
    public void onActivityPaused(Activity activity)
    {
        activityCount--;

        if (activity instanceof ChatActivity)
        {
            isChatActivityStarted = false;
        }

        if (activity instanceof ContactsActivity)
        {
            isContactsActivityStarted = false;
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {

    }
}
