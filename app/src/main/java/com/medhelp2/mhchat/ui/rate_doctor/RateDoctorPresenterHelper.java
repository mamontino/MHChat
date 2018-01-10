package com.medhelp2.mhchat.ui.rate_doctor;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
@SuppressWarnings("unused")
public interface RateDoctorPresenterHelper<V extends RateDoctorViewHelper> extends MvpPresenter<V>
{
    void onRatingSubmitted(float rating, String message);

    void onCancelClicked();

    void onLaterClicked();

    void onPlayStoreRatingClicked();
}
