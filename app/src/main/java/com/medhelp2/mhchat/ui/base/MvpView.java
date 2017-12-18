package com.medhelp2.mhchat.ui.base;


import android.support.annotation.StringRes;

public interface MvpView
{
    void showLoading();

    void hideLoading();

    boolean isLoading();

    void openActivityLogin();

    void showError(@StringRes int resId);

    void showError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
