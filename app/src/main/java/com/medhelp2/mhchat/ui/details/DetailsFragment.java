package com.medhelp2.mhchat.ui.details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.di.component.ActivityComponent;
import com.medhelp2.mhchat.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class DetailsFragment extends BaseFragment implements DetailsViewHelper
{
    public static final String TAG = "DetailsFragment";

    @Inject
    DetailsPresenterHelper<DetailsViewHelper> presenter;

    public static DetailsFragment newInstance()
    {
        Bundle args = new Bundle();
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null)
        {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected void setUp(View view)
    {
        view.setOnClickListener(v ->
        {

        });
    }

    @Override
    public void onDestroyView()
    {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void updateUserInfo()
    {

    }
}
