package com.medhelp2.mhchat.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.chat.ChatPresenter;
import com.medhelp2.mhchat.ui.chat.ChatPresenterHelper;
import com.medhelp2.mhchat.ui.chat.ChatViewHelper;
import com.medhelp2.mhchat.ui.chat.chat_list.ChatListPresenter;
import com.medhelp2.mhchat.ui.chat.chat_list.ChatListPresenterHelper;
import com.medhelp2.mhchat.ui.chat.chat_list.ChatListViewHelper;
import com.medhelp2.mhchat.ui.confirm.ConfirmPresenter;
import com.medhelp2.mhchat.ui.confirm.ConfirmPresenterHelper;
import com.medhelp2.mhchat.ui.confirm.ConfirmViewHelper;
import com.medhelp2.mhchat.ui.contacts.ContactsAdapter;
import com.medhelp2.mhchat.ui.contacts.ContactsPresenter;
import com.medhelp2.mhchat.ui.contacts.ContactsPresenterHelper;
import com.medhelp2.mhchat.ui.contacts.ContactsViewHelper;
import com.medhelp2.mhchat.ui.doctor.DoctorsAdapter;
import com.medhelp2.mhchat.ui.doctor.DoctorsPresenter;
import com.medhelp2.mhchat.ui.doctor.DoctorsPresenterHelper;
import com.medhelp2.mhchat.ui.doctor.DoctorsViewHelper;
import com.medhelp2.mhchat.ui.doctor.details.DocDetailsPresenter;
import com.medhelp2.mhchat.ui.doctor.details.DocDetailsPresenterHelper;
import com.medhelp2.mhchat.ui.doctor.details.DocDetailsViewHelper;
import com.medhelp2.mhchat.ui.doctor.service.ServicePresenter;
import com.medhelp2.mhchat.ui.doctor.service.ServicePresenterHelper;
import com.medhelp2.mhchat.ui.doctor.service.ServiceViewHelper;
import com.medhelp2.mhchat.ui.login.LoginPresenter;
import com.medhelp2.mhchat.ui.login.LoginPresenterHelper;
import com.medhelp2.mhchat.ui.login.LoginViewHelper;
import com.medhelp2.mhchat.ui.profile.ProfilePresenter;
import com.medhelp2.mhchat.ui.profile.ProfilePresenterHelper;
import com.medhelp2.mhchat.ui.profile.ProfileViewHelper;
import com.medhelp2.mhchat.ui.rating.RatePresenter;
import com.medhelp2.mhchat.ui.rating.RatePresenterHelper;
import com.medhelp2.mhchat.ui.rating.RateViewHelper;
import com.medhelp2.mhchat.ui.sale.SaleAdapter;
import com.medhelp2.mhchat.ui.sale.SalePresenter;
import com.medhelp2.mhchat.ui.sale.SalePresenterHelper;
import com.medhelp2.mhchat.ui.sale.SaleViewHelper;
import com.medhelp2.mhchat.ui.schedule.SchedulePresenter;
import com.medhelp2.mhchat.ui.schedule.SchedulePresenterHelper;
import com.medhelp2.mhchat.ui.schedule.ScheduleViewHelper;
import com.medhelp2.mhchat.ui.search.SearchAdapter;
import com.medhelp2.mhchat.ui.search.SearchPresenter;
import com.medhelp2.mhchat.ui.search.SearchPresenterHelper;
import com.medhelp2.mhchat.ui.search.SearchViewHelper;
import com.medhelp2.mhchat.ui.select.SelectPresenter;
import com.medhelp2.mhchat.ui.select.SelectPresenterHelper;
import com.medhelp2.mhchat.ui.select.SelectViewHelper;
import com.medhelp2.mhchat.ui.splash.SplashPresenter;
import com.medhelp2.mhchat.ui.splash.SplashPresenterHelper;
import com.medhelp2.mhchat.ui.splash.SplashViewHelper;
import com.medhelp2.mhchat.utils.rx.AppSchedulerProvider;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;


@Module
public class ActivityModule
{
    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity)
    {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    Context provideContext()
    {
        return activity;
    }

    @PerActivity
    @Provides
    AppCompatActivity provideActivity()
    {
        return activity;
    }

    @PerActivity
    @Provides
    CompositeDisposable provideCompositeDisposable()
    {
        return new CompositeDisposable();
    }

    @PerActivity
    @Provides
    SchedulerProvider provideSchedulerProvider()
    {
        return new AppSchedulerProvider();
    }

    @PerActivity
    @Provides
    SplashPresenterHelper<SplashViewHelper> provideSplashPresenter(SplashPresenter<SplashViewHelper> presenter)
    {
        return presenter;
    }

    @PerActivity
    @Provides
    LoginPresenterHelper<LoginViewHelper> provideLoginPresenter(LoginPresenter<LoginViewHelper> presenter)
    {
        return presenter;
    }

    @PerActivity
    @Provides
    SalePresenterHelper<SaleViewHelper> provideSalePresenter(SalePresenter<SaleViewHelper> presenter)
    {
        return presenter;
    }


    @PerActivity
    @Provides
    ContactsPresenterHelper<ContactsViewHelper> provideContactsPresenter(ContactsPresenter<ContactsViewHelper> presenter)
    {
        return presenter;
    }

    @PerActivity
    @Provides
    DocDetailsPresenterHelper<DocDetailsViewHelper> provideDocDetailsPresenter(DocDetailsPresenter<DocDetailsViewHelper> presenter)
    {
        return presenter;
    }

    @PerActivity
    @Provides
    DoctorsPresenterHelper<DoctorsViewHelper> provideDoctorsPresenter(DoctorsPresenter<DoctorsViewHelper> presenter)
    {
        return presenter;
    }

    @PerActivity
    @Provides
    SchedulePresenterHelper<ScheduleViewHelper> provideSchedulePresenter(SchedulePresenter<ScheduleViewHelper> presenter)
    {
        return presenter;
    }

    @Provides
    ChatListPresenterHelper<ChatListViewHelper> provideChatListPresenter(ChatListPresenter<ChatListViewHelper> presenter)
    {
        return presenter;
    }

    @Provides
    ConfirmPresenterHelper<ConfirmViewHelper> provideConfirmPresenter(ConfirmPresenter<ConfirmViewHelper> presenter)
    {
        return presenter;
    }

    @Provides
    RatePresenterHelper<RateViewHelper> provideRatePresenter(RatePresenter<RateViewHelper> presenter)
    {
        return presenter;
    }

    @Provides
    SelectPresenterHelper<SelectViewHelper> provideSelectPresenter(SelectPresenter<SelectViewHelper> presenter)
    {
        return presenter;
    }

    @PerActivity
    @Provides
    ChatPresenterHelper<ChatViewHelper> provideChatPresenter(ChatPresenter<ChatViewHelper> presenter)
    {
        return presenter;
    }

    @PerActivity
    @Provides
    ProfilePresenterHelper<ProfileViewHelper> provideProfilePresenter(ProfilePresenter<ProfileViewHelper> presenter)
    {
        return presenter;
    }

    @PerActivity
    @Provides
    SearchPresenterHelper<SearchViewHelper> provideSearchPresenter(SearchPresenter<SearchViewHelper> presenter)
    {
        return presenter;
    }

    @PerActivity
    @Provides
    ServicePresenterHelper<ServiceViewHelper> provideServicePresenter(ServicePresenter<ServiceViewHelper> presenter)
    {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity)
    {
        return new LinearLayoutManager(activity);
    }

    @PerActivity
    @Provides
    ContactsAdapter provideContactsAdapter()
    {
        return new ContactsAdapter(new ArrayList<>());
    }

    @PerActivity
    @Provides
    SaleAdapter provideSaleAdapter()
    {
        return new SaleAdapter(new ArrayList<>());
    }

    @PerActivity
    @Provides
    DoctorsAdapter provideDoctorsAdapter()
    {
        return new DoctorsAdapter(new ArrayList<>());
    }

//    @PerActivity
//    @Provides
//    ScheduleAdapter provideScheduleAdapter()
//    {
//        return new ScheduleAdapter(new ArrayList<>());
//    }

    @PerActivity
    @Provides
    SearchAdapter provideSearchAdapter()
    {
        return new SearchAdapter(new ArrayList<>());
    }
}
