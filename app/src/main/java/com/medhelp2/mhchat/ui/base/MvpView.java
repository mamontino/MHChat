package com.medhelp2.mhchat.ui.base;


import android.support.annotation.StringRes;

// TODO: Базовый интерфейс для View в MVP (Model View Presenter)

public interface MvpView
{
    void showLoading();

    void hideLoading();

    void openActivityLogin();

    void showError(@StringRes int resId);

    void showError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
