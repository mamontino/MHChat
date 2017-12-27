package com.medhelp2.mhchat.ui.rate_app;


import com.medhelp2.mhchat.ui.base.DialogMvpView;

public interface RateViewHelper extends DialogMvpView
{
    void openPlayStoreForRating();

    void showPlayStoreRatingView();

    void showRatingMessageView();

    void hideSubmitButton();

    void disableRatingStars();

    void dismissDialog();

    void sendReview(String message, float rating);
}
