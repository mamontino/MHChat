package com.medhelp2.mhchat.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ListFragment;
import android.view.View;

import com.medhelp2.mhchat.utils.main.MainUtils;
import com.medhelp2.mhchat.di.component.ActivityComponent;

import butterknife.Unbinder;

public abstract class BaseFragmentList extends ListFragment implements MvpView {

    private BaseActivity activity;
    private Unbinder unbinder;
    private ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.activity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        dialog = MainUtils.showLoadingDialog(this.getContext());
    }

    @Override
    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }

    @Override
    public void showError(String message) {
        if (activity != null) {
            activity.showError(message);
        }
    }

    @Override
    public void showError(@StringRes int resId) {
        if (activity != null) {
            activity.showError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (activity != null) {
            activity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (activity != null) {
            activity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (activity != null) {
            return activity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    @Override
    public void openActivityLogin() {
        if (activity != null) {
            activity.openActivityLogin();
        }
    }

    public ActivityComponent getActivityComponent() {
        if (activity != null) {
            return activity.getActivityComponent();
        }
        return null;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public void setUnBinder(Unbinder unBinder) {
        unbinder = unBinder;
    }

    protected abstract void setUp(View view);

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
