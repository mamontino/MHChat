package com.medhelp2.mhchat.ui.confirm;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.DoctorInfo;
import com.medhelp2.mhchat.di.component.ActivityComponent;
import com.medhelp2.mhchat.ui.base.BaseDialog;
import com.medhelp2.mhchat.ui.doctor.service.ServiceActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmFragment extends BaseDialog implements ConfirmViewHelper
{
    public static final String TAG = "ConfirmFragment";
    public static final String EXTRA_ID = "EXTRA_ID";

    @Inject
    ConfirmPresenterHelper<ConfirmViewHelper> presenter;

    @BindView(R.id.doc_info_image)
    ImageView docInfoImage;

    @BindView(R.id.doc_info_exp)
    TextView docInfoExp;

    @BindView(R.id.doc_info_hint_exp)
    TextView docInfoHintExp;

    @BindView(R.id.doc_info_hint_year)
    TextView docInfoHintYear;

    @BindView(R.id.doc_info_name)
    TextView docInfoName;

    @BindView(R.id.doc_info_hint_name)
    TextView docInfoHintName;

    @BindView(R.id.doc_info_info)
    TextView docInfoInfo;

    @BindView(R.id.doc_info_hint_info)
    TextView docInfoHintInfo;

    @BindView(R.id.doc_info_spec)
    TextView docInfoSpec;

    @BindView(R.id.doc_info_hint_spec)
    TextView docInfoHintSpec;

    private int idDoctor;

    public static ConfirmFragment newInstance(int idDoctor)
    {
        Bundle args = new Bundle();
        ConfirmFragment fragment = new ConfirmFragment();
        args.putInt(EXTRA_ID, idDoctor);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_doctor_details, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null)
        {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
            presenter.loadDocInfo(idDoctor);
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        idDoctor = getArguments().getInt(EXTRA_ID, 0);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.doc_info_btn_close)
    void onCloseDialog()
    {
        dismissDialog();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.doc_info_btn_record)
    void onRecordClick()
    {
        showServiceActivity(idDoctor);
    }

    public void showServiceActivity(int idDoctor)
    {
        Intent intent = ServiceActivity.getStartIntent(getContext());
        intent.putExtra(ServiceActivity.EXTRA_DATA_ID_DOCTOR, idDoctor);
        startActivity(intent);
        dismissDialog();
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
    public void onDestroyView()
    {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void dismissDialog()
    {
        super.dismissDialog(TAG);
    }

    @Override
    public void updateDocInfo(DoctorInfo doctorInfo)
    {
        if (doctorInfo != null)
        {
            if (doctorInfo.getInfo() != null && !doctorInfo.getInfo().trim().isEmpty())
            {
                docInfoInfo.setText(doctorInfo.getInfo());
            } else
            {
                docInfoInfo.setVisibility(View.GONE);
                docInfoHintInfo.setVisibility(View.GONE);
            }
            if (doctorInfo.getExpr() != 0)
            {
                docInfoExp.setText(String.valueOf(doctorInfo.getExpr()));
            } else
            {
                docInfoExp.setVisibility(View.GONE);
                docInfoHintExp.setVisibility(View.GONE);
                docInfoHintYear.setVisibility(View.GONE);
            }
            if (doctorInfo.getSpecialty() != null && !doctorInfo.getSpecialty().trim().isEmpty())
            {
                docInfoSpec.setText(doctorInfo.getSpecialty());
            } else
            {
                docInfoSpec.setVisibility(View.GONE);
                docInfoHintSpec.setVisibility(View.GONE);
            }
            if (doctorInfo.getFullName() != null && !doctorInfo.getFullName().trim().isEmpty())
            {
                docInfoName.setText(doctorInfo.getFullName());
            } else
            {
                docInfoName.setVisibility(View.GONE);
                docInfoHintName.setVisibility(View.GONE);
            }
        }
    }
}
