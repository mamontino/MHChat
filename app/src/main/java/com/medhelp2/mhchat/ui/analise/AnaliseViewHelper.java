package com.medhelp2.mhchat.ui.analise;


import com.medhelp2.mhchat.data.model.AnaliseResponse;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;

@SuppressWarnings("unused")
public interface AnaliseViewHelper extends MvpView
{
    void showErrorScreen();

    void showProfileActivity();

    void showDoctorsActivity();

    void showSearchActivity();

    void showSaleActivity();

    void showContactsActivity();

    void showRateFragment();

    void showLoginActivity();

    void updateHeader(CenterResponse response);

    void updateAnaliseData(List<AnaliseResponse> response);
}
