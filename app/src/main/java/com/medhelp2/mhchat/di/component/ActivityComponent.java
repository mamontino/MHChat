package com.medhelp2.mhchat.di.component;

import com.medhelp2.mhchat.di.module.ActivityModule;
import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.chat.ChatActivity;
import com.medhelp2.mhchat.ui.chat.chat_list.ChatListFragment;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.doctor.DoctorsActivity;
import com.medhelp2.mhchat.ui.doctor.details.DocDetailsFragment;
import com.medhelp2.mhchat.ui.doctor.service.ServiceActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;
import com.medhelp2.mhchat.ui.rate_doctor.RateDoctorFragment;
import com.medhelp2.mhchat.ui.rate_app.RateFragment;
import com.medhelp2.mhchat.ui.sale.SaleActivity;
import com.medhelp2.mhchat.ui.schedule.ScheduleActivity;
import com.medhelp2.mhchat.ui.confirm.ConfirmFragment;
import com.medhelp2.mhchat.ui.search.SearchActivity;
import com.medhelp2.mhchat.ui.select.SelectActivity;
import com.medhelp2.mhchat.ui.splash.SplashActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)

public interface ActivityComponent
{
    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

    void inject(ContactsActivity activity);

    void inject(ChatActivity activity);

    void inject(ScheduleActivity activity);

    void inject(ChatListFragment fragment);

    void inject(DocDetailsFragment fragment);

    void inject(SelectActivity fragment);

    void inject(RateFragment fragment);

    void inject(SearchActivity activity);

    void inject(ProfileActivity activity);

    void inject(DoctorsActivity activity);

    void inject(SaleActivity saleActivity);

    void inject(ServiceActivity serviceActivity);

    void inject(ConfirmFragment confirmFragment);

    void inject(RateDoctorFragment rateDoctorFragment);
}
