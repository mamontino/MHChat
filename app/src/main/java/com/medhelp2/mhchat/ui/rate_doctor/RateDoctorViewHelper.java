package com.medhelp2.mhchat.ui.rate_doctor;


import com.medhelp2.mhchat.ui.base.DialogMvpView;

public interface RateDoctorViewHelper extends DialogMvpView
{
    void openPlayStoreForRating();

    void showPlayStoreRatingView();

    void showRatingMessageView();

    void hideSubmitButton();

    void disableRatingStars();

    void dismissDialog();

    void sendReview(String message, float rating);
}
