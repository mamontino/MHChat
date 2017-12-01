package com.medhelp2.mhchat.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SettingsActivity extends BaseActivity implements SettingsViewHelper
{

    @Inject
    SettingsPresenter<SettingsViewHelper> presenter;

    @BindView(R.id.toolbar_settings)
    Toolbar toolbar;

    private TextView headerTitle;
    private TextView headerDesc;
    private ImageView headerLogo;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        presenter.onAttach(this);
        setUp();
    }

    private void setupToolbar()
    {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white_24dp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case R.id.action_settings_save:
                showMessage("Сохранение настроек");
                return true;

            case android.R.id.home:
                super.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void setUp()
    {
        Timber.d("setUp");
        setupToolbar();
    }
}
