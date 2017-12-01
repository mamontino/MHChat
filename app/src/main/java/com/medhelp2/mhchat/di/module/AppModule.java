package com.medhelp2.mhchat.di.module;

import android.app.Application;
import android.content.Context;

import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.DataManager;
import com.medhelp2.mhchat.data.db.RealmHelper;
import com.medhelp2.mhchat.data.db.RealmManager;
import com.medhelp2.mhchat.data.network.NetworkHelper;
import com.medhelp2.mhchat.data.network.NetworkManager;
import com.medhelp2.mhchat.data.pref.PreferencesHelper;
import com.medhelp2.mhchat.data.pref.PreferencesManager;
import com.medhelp2.mhchat.di.scope.ApiInfo;
import com.medhelp2.mhchat.di.scope.DatabaseInfo;
import com.medhelp2.mhchat.di.scope.PerApplication;
import com.medhelp2.mhchat.di.scope.PreferenceInfo;
import com.medhelp2.mhchat.utils.main.AppConstants;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    private final Application application;

    public AppModule(Application application)
    {
        this.application = application;
    }

    @PerApplication
    @Provides
    Context provideContext()
    {
        return application;
    }

    @PerApplication
    @Provides
    Application provideApplication()
    {
        return application;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @PerApplication
    @Provides
    DataHelper provideDataManager(DataManager dataManager)
    {
        return dataManager;
    }

    @PerApplication
    @Provides
    PreferencesHelper providePreferencesHelper(PreferencesManager preferencesManager)
    {
        return preferencesManager;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return AppConstants.API_KEY;
    }

    @PerApplication
    @Provides
    NetworkHelper provideNetworkHelper(NetworkManager networkManager)
    {
        return networkManager;
    }

    @PerApplication
    @Provides
    RealmHelper provideRealmHelper(RealmManager realmManager)
    {
        return realmManager;
    }
}