package com.medhelp2.mhchat.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashViewHelper
{
    @Inject
    SplashPresenterHelper<SplashViewHelper> presenter;

    @BindView(R.id.splash_img)
    ImageView imgSplash;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActivityComponent().inject(this);
        presenter.onAttach(SplashActivity.this);
        ButterKnife.bind(this);
        animationStart();
    }

    @Override
    public void openLoginActivity()
    {
        animationStop();
        Intent intent = LoginActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openContactsActivity()
    {
        animationStop();
        Intent intent = ContactsActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openProfileActivity()
    {
        animationStop();
        Intent intent = ProfileActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openChatActivity()
    {
        animationStop();
        Intent intent = ProfileActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp()
    {

    }

    private void animationStart()
    {
        final AnimationSet animationSet = new AnimationSet(true);

        final Animation slideRight = AnimationUtils.loadAnimation(this,
                R.anim.slide_splash_fade_in);
        slideRight.setFillAfter(true);
        slideRight.setDuration(1000);

        final AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setFillAfter(true);
        alpha.setDuration(1000);

        final RotateAnimation rotate = new RotateAnimation(0.0f, 360.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);

        animationSet.addAnimation(rotate);
        animationSet.addAnimation(alpha);
        animationSet.addAnimation(slideRight);

        imgSplash.startAnimation(animationSet);
    }

    private void animationStop()
    {
        final AnimationSet animationSet = new AnimationSet(true);

        final Animation slideRight = AnimationUtils.loadAnimation(this,
                R.anim.slide_right);
        slideRight.setFillAfter(true);
        slideRight.setDuration(1000);

        final AlphaAnimation alpha = new AlphaAnimation(1f, 0.0f);
        alpha.setFillAfter(true);
        alpha.setDuration(1000);

        final RotateAnimation rotate = new RotateAnimation(0.0f, 360.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);

        animationSet.addAnimation(rotate);
        animationSet.addAnimation(alpha);
        animationSet.addAnimation(slideRight);

        imgSplash.startAnimation(animationSet);
    }
}
