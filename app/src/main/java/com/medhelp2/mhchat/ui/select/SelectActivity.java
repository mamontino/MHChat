package com.medhelp2.mhchat.ui.select;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectActivity extends BaseActivity implements SelectViewHelper,
        Spinner.OnItemSelectedListener
{
    @Inject
    SelectPresenterHelper<SelectViewHelper> presenter;

    @BindView(R.id.toolbar_select)
    Toolbar toolbar;

    @BindView(R.id.spinner_select_spec)
    Spinner spinnerSpec;

    @BindView(R.id.spinner_select_doctor)
    Spinner spinnerDoctor;

    @BindView(R.id.spinner_select_service)
    Spinner spinnerService;

    @BindView(R.id.tv_select_spec)
    TextView tvSpec;

    @BindView(R.id.tv_select_doctor)
    TextView tvDoctor;

    @BindView(R.id.tv_select_service)
    TextView tvService;

    @BindView(R.id.tv_selected_spec)
    TextView tvSpecSelected;

    @BindView(R.id.tv_selected_doctor)
    TextView tvDoctorSelected;

    @BindView(R.id.tv_selected_service)
    TextView tvServiceSelected;

    @BindView(R.id.collapsing_toolbar_select)
    CollapsingToolbarLayout toolbarLayout;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, SelectActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        getActivityComponent().inject(this);
        setUp();
    }

    @Override
    protected void setUp()
    {
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        setupToolbar();
    }

    @SuppressWarnings("unused")
    private void setupToolbar()
    {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) toolbarLayout.getLayoutParams();
        if (actionBar != null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_select_continue)
    public void btnContinueClick()
    {

    }

    @Override
    public void updateView(@NonNull List<Doctor> doctors, @NonNull List<CategoryResponse> categories, @NonNull List<ServiceResponse> services)
    {

    }
}

