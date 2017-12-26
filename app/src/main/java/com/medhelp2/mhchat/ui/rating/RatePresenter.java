package com.medhelp2.mhchat.ui.rating;


import android.content.Intent;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.ui.base.BasePresenter;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class RatePresenter<V extends RateViewHelper> extends BasePresenter<V> implements RatePresenterHelper<V>
{
    private boolean isRatingSecondaryActionShown = false;

    @Inject
    public RatePresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        super(dataHelper, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onRatingSubmitted(final float rating, String message)
    {

        if (rating == 0)
        {
            getMvpView().showMessage(R.string.rating_not_provided_error);
            return;
        }

        if (!isRatingSecondaryActionShown)
        {
            if (rating == 5)
            {
                getMvpView().showPlayStoreRatingView();
                getMvpView().hideSubmitButton();
                getMvpView().disableRatingStars();
            } else
            {
                getMvpView().showRatingMessageView();
            }
            isRatingSecondaryActionShown = true;
            return;
        }
        getMvpView().sendReview(message, rating);
        getMvpView().showMessage(R.string.rating_thanks);
        getMvpView().dismissDialog();

    }


    @Override
    public void onCancelClicked()
    {
        getMvpView().dismissDialog();
    }

    @Override
    public void onLaterClicked()
    {
        getMvpView().dismissDialog();
    }

    @Override
    public void onPlayStoreRatingClicked()
    {
        getMvpView().openPlayStoreForRating();
        getMvpView().dismissDialog();
    }
}
