package com.medhelp2.mhchat.ui.search;

import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;


public interface SearchViewHelper extends MvpView
{
    void closeNavigationDrawer();

    void lockDrawer();

    void unlockDrawer();

    void showProfileActivity();

    void showScheduleActivity(ServiceResponse response);

    void showContactsActivity();

    void showLoginActivity();

    void showSaleActivity();

    void showRateFragment();

    void showDoctorsActivity();

    void updateHeader(CenterResponse response);

    void updateView(List<CategoryResponse> categories, List<ServiceResponse> services);
}
