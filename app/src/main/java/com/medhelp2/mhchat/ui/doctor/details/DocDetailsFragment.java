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
import com.medhelp2.mhchat.utils.main.AppConstants;

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

    @BindView(R.id.doc_info_name)
    TextView docInfoName;

    @BindView(R.id.doc_info_info)
    TextView docInfoInfo;

    @BindView(R.id.doc_info_spec)
    TextView docInfoSpec;

    private int idDoctor;

    public static DocDetailsFragment newInstance(int idDoctor)
    {
        Timber.d("DocDetailsFragment newInstance for idDoctor " + idDoctor);
        Bundle args = new Bundle();
        DocDetailsFragment fragment = new DocDetailsFragment();
        if (idDoctor != 0)
        {
            args.putInt(AppConstants.ID_ROOM, idDoctor);
        }
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
        presenter.loadDocInfo(idDoctor);
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

    }
}
