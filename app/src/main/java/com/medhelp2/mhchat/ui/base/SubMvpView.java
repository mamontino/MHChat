package com.medhelp2.mhchat.ui.base;


// TODO: Базовый интерфейс для View в MVP (Model View Presenter)

public interface SubMvpView extends MvpView
{
    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void attachParentMvpView(MvpView mvpView);
}
