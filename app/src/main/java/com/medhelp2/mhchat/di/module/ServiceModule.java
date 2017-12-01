package com.medhelp2.mhchat.di.module;


import com.medhelp2.mhchat.di.scope.PerService;
import com.medhelp2.mhchat.bg.MessagingService;
import com.medhelp2.mhchat.bg.SyncService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    MessagingService messagingService;
    SyncService syncService;

    @PerService
    @Provides
    MessagingService provideMessagingService() {
        return messagingService;
    }

    @PerService
    @Provides
    SyncService provideSyncService() {
        return syncService;
    }
}
