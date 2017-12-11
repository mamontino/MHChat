package com.medhelp2.mhchat.ui.rating;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.di.component.ActivityComponent;
import com.medhelp2.mhchat.ui.base.BaseDialog;
import com.medhelp2.mhchat.utils.main.PlayStoreUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RateFragment extends BaseDialog implements RateViewHelper
{
    private static final String TAG = "RateFragment";

    @Inject
    RatePresenterHelper<RateViewHelper> presenter;

    @BindView(R.id.rating_bar_feedback)
    RatingBar ratingBar;

    @BindView(R.id.et_message)
    EditText message;

    @BindView(R.id.view_rating_message)
    View ratingMessageView;

    @BindView(R.id.view_play_store_rating)
    View playStoreRatingView;

    @BindView(R.id.btn_submit)
    Button submitButton;


    public static RateFragment newInstance()
    {
        RateFragment fragment = new RateFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null)
        {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }
        return view;
    }

    public void show(FragmentManager fragmentManager)
    {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void openPlayStoreForRating()
    {
        PlayStoreUtils.openPlayStoreForApp(getContext());
    }

    @Override
    public void showPlayStoreRatingView()
    {
        playStoreRatingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRatingMessageView()
    {
        ratingMessageView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setUp(View view)
    {
        ratingMessageView.setVisibility(View.GONE);
        playStoreRatingView.setVisibility(View.GONE);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(0)
                .setColorFilter(ContextCompat.getColor(getContext(), R.color.light_gray), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1)
                .setColorFilter(ContextCompat.getColor(getContext(), R.color.light_gray), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2)
                .setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), PorterDuff.Mode.SRC_ATOP);

        submitButton.setOnClickListener(v -> presenter.onRatingSubmitted(ratingBar.getRating(), message.getText().toString()));
    }

    @OnClick(R.id.btn_later)
    void onLaterClick()
    {
        presenter.onLaterClicked();
    }

    @OnClick(R.id.btn_rate_on_play_store)
    void onPlayStoreRateClick()
    {
        presenter.onPlayStoreRatingClicked();
    }

    @Override
    public void disableRatingStars()
    {
        ratingBar.setIsIndicator(true);
    }

    @Override
    public void hideSubmitButton()
    {
        submitButton.setVisibility(View.GONE);
    }

    @Override
    public void dismissDialog()
    {
        super.dismissDialog(TAG);
    }

    @Override
    public void onDestroyView()
    {
        presenter.onDetach();
        super.onDestroyView();
    }
}