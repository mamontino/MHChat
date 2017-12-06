package com.medhelp2.mhchat.ui.about;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.di.component.ActivityComponent;
import com.medhelp2.mhchat.ui.base.BaseDialog;

import javax.inject.Inject;

public class AboutFragment extends BaseDialog implements AboutViewHelper
{
    public static final String TAG = "AboutFragment";

    @Inject
    AboutPresenterHelper<AboutViewHelper> presenter;

    public static AboutFragment newInstance()
    {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null)
        {
            component.inject(this);
            presenter.onAttach(this);
        }

        return view;
    }

    public void show(FragmentManager fragmentManager)
    {
        super.show(fragmentManager, TAG);
    }

    @Override
    protected void setUp(View view)
    {
        view.setOnClickListener(v -> super.dismissDialog(TAG));
    }

    @Override
    public void onDestroyView()
    {
        presenter.onDetach();
        super.onDestroyView();
    }
}
