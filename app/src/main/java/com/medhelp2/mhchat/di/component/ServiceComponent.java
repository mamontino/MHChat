package com.medhelp2.mhchat.di.component;

import com.medhelp2.mhchat.di.module.ActivityModule;
import com.medhelp2.mhchat.di.module.ServiceModule;
import com.medhelp2.mhchat.di.scope.PerService;
import com.medhelp2.mhchat.bg.MessagingService;
import com.medhelp2.mhchat.bg.SyncService;
import dagger.Component;

@PerService
@Component(dependencies = AppComponent.class, modules = {ServiceModule.class, ActivityModule.class})
public interface ServiceComponent
{
    void inject(MessagingService service);

    void inject(SyncService service);
}