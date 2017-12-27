package com.medhelp2.mhchat.ui.rate_app;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface RatePresenterHelper<V extends RateViewHelper> extends MvpPresenter<V>
{
    void onRatingSubmitted(float rating, String message);

    void onCancelClicked();

    void onLaterClicked();

    void onPlayStoreRatingClicked();
}
