package com.medhelp2.mhchat.ui.splash;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.bg.SyncService;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.license.LicenseFragment;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashViewHelper
{
    @Inject
    SplashPresenterHelper<SplashViewHelper> presenter;

    @BindView(R.id.splash_img)
    ImageView imgSplash;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActivityComponent().inject(this);
        presenter.onAttach(SplashActivity.this);
        ButterKnife.bind(this);

        if (presenter.isFirstStart())
        {
            showLicenseFragment();
        } else
        {
            presenter.openNextActivity();
        }
    }

    @Override
    public void openLoginActivity()
    {
        Intent intent = LoginActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openProfileActivity()
    {
        Intent intent = ProfileActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp()
    {
        startSyncService();
    }

    private void showLicenseFragment()
    {
        LicenseFragment.newInstance().show(getSupportFragmentManager());
    }

    private void startSyncService()
    {
        Intent intent = SyncService.getStartIntent(this);
        startService(intent);
    }
}
