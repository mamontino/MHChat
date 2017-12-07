package com.medhelp2.mhchat.ui.doctor.details;


import android.os.Bundle;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class DocDetailsFragment extends BaseDialog implements DocDetailsViewHelper
{
    public static final String TAG = "DocDetailsFragment";

    @Inject
    DocDetailsPresenterHelper<DocDetailsViewHelper> presenter;

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

    private static int id;

    public static DocDetailsFragment newInstance(int idDoctor)
    {
        Timber.d("DocDetailsFragment newInstance for id " + idDoctor);
        Bundle args = new Bundle();
        DocDetailsFragment fragment = new DocDetailsFragment();
        id = idDoctor;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_doctor_details, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null)
        {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
            presenter.loadDocInfo(id);
        }
        return view;
    }

    @OnClick(R.id.doc_info_btn_schedule)
    void onScheduleClick()
    {
        presenter.onScheduleClicked();
    }

    @OnClick(R.id.doc_info_btn_record)
    void onRecordClick()
    {
        presenter.onRecordClicked();
    }

    @Override
    protected void setUp(View view)
    {
        view.setOnClickListener(v -> super.dismissDialog(TAG));
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
            Timber.d("updateDocInfo");

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
