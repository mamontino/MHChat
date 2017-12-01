package com.medhelp2.mhchat.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;

import javax.inject.Inject;

import timber.log.Timber;

public class SplashActivity extends BaseActivity implements SplashViewHelper
{
    @Inject
    SplashPresenterHelper<SplashViewHelper> presenter;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");
        setContentView(R.layout.activity_splash);
        getActivityComponent().inject(this);
        setUp();
    }

    @Override
    public void openLoginActivity()
    {
        Timber.d("openLoginActivity");
        Intent intent = LoginActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openContactsActivity()
    {
        Timber.d("openContactsActivity");
        Intent intent = ContactsActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openProfileActivity()
    {
        Timber.d("openProfileActivity");
        Intent intent = ProfileActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openChatActivity()
    {
        Timber.d("openChatActivity");
        Intent intent = ProfileActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy()
    {
        Timber.d("onDestroy");
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp()
    {
        Timber.d("setUp");
        presenter.onAttach(SplashActivity.this);
    }
}
