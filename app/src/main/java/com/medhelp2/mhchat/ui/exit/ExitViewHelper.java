package com.medhelp2.mhchat.ui.exit;

import com.medhelp2.mhchat.data.model.DoctorInfo;
import com.medhelp2.mhchat.ui.base.DialogMvpView;

public interface ExitViewHelper extends DialogMvpView
{
    void updateDocInfo(DoctorInfo doctorInfo);

    void dismissDialog();
}
