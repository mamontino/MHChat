package com.medhelp2.mhchat.ui.rating;


import com.medhelp2.mhchat.ui.base.DialogMvpView;

public interface RateViewHelper extends DialogMvpView
{
    void openPlayStoreForRating();

    void showPlayStoreRatingView();

    void showRatingMessageView();

    void hideSubmitButton();

    void disableRatingStars();

    void dismissDialog();
}
