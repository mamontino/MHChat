package com.medhelp2.mhchat.ui.doctor;

import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.DoctorInfo;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;

public interface DoctorsViewHelper extends MvpView
{
    void lockDrawer();

    void unlockDrawer();

    void closeNavigationDrawer();

    void showDocDetailsFragment(int idDoctor);

    void showScheduleActivity(Doctor doctor);

    void showProfileActivity();

    void showSaleActivity();

    void showSearchActivity();

    void showContactsActivity();

    void showRateFragment();

    void showLoginActivity();

    void updateHeader(CenterResponse response);

    void updateView(List<DoctorInfo> response);

    void updateSpecialty(List<CategoryResponse> response);
}
