package com.medhelp2.mhchat.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.ViewGroup;


public abstract class BaseSubView extends ViewGroup implements SubMvpView {

    private MvpView mvpView;

    public BaseSubView(Context context) {
        super(context);
    }

    public BaseSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BaseSubView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void attachParentMvpView(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void showLoading() {
        if (mvpView != null) {
            mvpView.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (mvpView != null) {
            mvpView.hideLoading();
        }
    }

    @Override
    public void showError(@StringRes int resId) {
        if (mvpView != null) {
            mvpView.showError(resId);
        }
    }

    @Override
    public void showError(String message) {
        if (mvpView != null) {
            mvpView.showError(message);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mvpView != null) {
            mvpView.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mvpView != null) {
            mvpView.showMessage(resId);
        }
    }

    @Override
    public void hideKeyboard() {
        if (mvpView != null) {
            mvpView.hideKeyboard();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (mvpView != null) {
            return mvpView.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void openActivityLogin() {
        if (mvpView != null) {
            mvpView.openActivityLogin();
        }
    }

    protected abstract void bindViewsAndSetOnClickListeners();

    protected abstract void setUp();
}
