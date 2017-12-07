package com.medhelp2.mhchat.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.di.component.ActivityComponent;
import com.medhelp2.mhchat.di.component.DaggerActivityComponent;
import com.medhelp2.mhchat.di.module.ActivityModule;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.utils.main.MainUtils;
import com.medhelp2.mhchat.utils.main.NetworkUtils;

import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback
{
    private ProgressDialog dialog;
    private ActivityComponent activityComponent;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((MainApp) getApplication()).getComponent())
                .build();
    }

    public ActivityComponent getActivityComponent()
    {
        return activityComponent;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission)
    {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading()
    {
        hideLoading();
        dialog = MainUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading()
    {
        if (dialog != null && dialog.isShowing())
        {
            dialog.cancel();
        }
    }

    private void showSnackBar(String message)
    {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void showError(String message)
    {
        if (message != null)
        {
            showSnackBar(message);
        } else
        {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void showError(@StringRes int resId)
    {
        showError(getString(resId));
    }

    @Override
    public void showMessage(String message)
    {
        if (message != null)
        {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId)
    {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected()
    {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached()
    {
    }

    @Override
    public void onFragmentDetached(String tag)
    {
    }

    public void hideKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
            {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void openActivityLogin()
    {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    public void setUnBinder(Unbinder unBinder)
    {
        unbinder = unBinder;
    }

    @Override
    protected void onDestroy()
    {
        if (unbinder != null)
        {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract void setUp();
}
