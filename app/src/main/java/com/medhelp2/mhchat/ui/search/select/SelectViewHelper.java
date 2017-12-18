package com.medhelp2.mhchat.ui.search.select;

import com.medhelp2.mhchat.data.model.DoctorInfo;
import com.medhelp2.mhchat.ui.base.DialogMvpView;

public interface SelectViewHelper extends DialogMvpView
{
    void updateDocList(DoctorInfo doctorInfo);

    void dismissDialog();
}
