package com.medhelp2.mhchat.ui.base;


import com.androidnetworking.error.ANError;

@SuppressWarnings("unused")
public interface MvpPresenter<V extends MvpView>
{
    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(ANError error);

    boolean isNetworkMode();
}
