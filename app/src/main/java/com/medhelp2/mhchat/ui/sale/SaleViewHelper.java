package com.medhelp2.mhchat.ui.sale;

import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.SaleResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;

public interface SaleViewHelper extends MvpView {

    void lockDrawer();

    void unlockDrawer();

    void closeNavigationDrawer();

    void showProfileActivity();

    void showDoctorsActivity();

    void showSearchActivity();

    void showScheduleActivity();

    void showContactsActivity();

    void showRateFragment();

    void showLoginActivity();

    void updateHeader(CenterResponse response);

    void updateSaleData(List<SaleResponse> response);
}
