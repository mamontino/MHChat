package com.medhelp2.mhchat.ui.select;

import android.support.annotation.NonNull;

import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;

public interface SelectViewHelper extends MvpView
{
    void updateView(@NonNull List<Doctor> doctors, @NonNull List<CategoryResponse> categories,
            @NonNull List<ServiceResponse> services);
}
