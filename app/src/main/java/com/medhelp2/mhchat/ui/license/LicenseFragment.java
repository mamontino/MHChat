package com.medhelp2.mhchat.ui.license;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.di.component.ActivityComponent;
import com.medhelp2.mhchat.ui.base.BaseDialog;
import com.medhelp2.mhchat.ui.login.LoginActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LicenseFragment extends BaseDialog implements LicenseViewHelper
{
    public static final String TAG = "LicenseFragment";

    @Inject
    LicensePresenterHelper<LicenseViewHelper> presenter;

    public static LicenseFragment newInstance()
    {
        Bundle args = new Bundle();
        LicenseFragment fragment = new LicenseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_license, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null)
        {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }
        return view;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.license_cancel)
    void onCloseDialog()
    {
        dismissDialog();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.license_confirm)
    void onRecordClick()
    {
        presenter.confirmLicense();
    }

    @Override
    protected void setUp(View view)
    {
    }

    public void show(FragmentManager fragmentManager)
    {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }

    private void dismissDialog()
    {
        if (getActivity() != null)
        {
            getActivity().finish();
        }
        super.dismissDialog(TAG);
    }

    @Override
    public void showLoginActivity()
    {
        Intent intent = LoginActivity.getStartIntent(getContext());
        startActivity(intent);
        dismissDialog();
    }
}
