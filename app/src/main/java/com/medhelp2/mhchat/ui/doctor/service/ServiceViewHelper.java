package com.medhelp2.mhchat.ui.doctor.service;

import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;


public interface ServiceViewHelper extends MvpView
{
    void updateView(List<CategoryResponse> categories, List<ServiceResponse> services);

    void showErrorScreen();
}
