package com.medhelp2.mhchat.ui.doctor.details;


import com.medhelp2.mhchat.data.model.DoctorInfo;
import com.medhelp2.mhchat.ui.base.DialogMvpView;

public interface DocDetailsViewHelper extends DialogMvpView
{
    void updateDocInfo(DoctorInfo doctorInfo);

    void dismissDialog();
}
