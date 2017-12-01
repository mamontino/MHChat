package com.medhelp2.mhchat.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.login.login_fr.FormFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginViewHelper
{
    @Inject
    LoginPresenterHelper<LoginViewHelper> presenter;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        presenter.onAttach(LoginActivity.this);
        setUp();
    }

    @Override
    public void showLoginContent()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.fr_login, FormFragment.newInstance(), FormFragment.TAG)
                .commit();
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
        showLoginContent();
    }

    @Override
    public void onFragmentAttached()
    {
    }

    @Override
    public void onFragmentDetached(String tag)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null)
        {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
        }
    }
}
