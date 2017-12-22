package com.medhelp2.mhchat.ui.confirm;

import com.medhelp2.mhchat.data.model.DoctorInfo;
import com.medhelp2.mhchat.ui.base.DialogMvpView;

public interface ConfirmViewHelper extends DialogMvpView
{
    void updateDocInfo(DoctorInfo doctorInfo);

    void dismissDialog();
}
