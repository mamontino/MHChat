package com.medhelp2.mhchat.ui.profile;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.VisitResponse;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import timber.log.Timber;

public class ProfileVisitViewHolder extends ChildViewHolder
{
    private ImageView receptionLogo;
    private TextView receptionTitle;
    private TextView receptionDoctor;
    private TextView receptionTime;
    private Button receptionCancel;
    private Button receptionConfirm;

    private ImageView receptionLogoNo;
    private TextView receptionTitleNo;
    private TextView receptionDoctorNo;
    private TextView receptionTimeNo;

    public ProfileVisitViewHolder(View itemView)
    {
        super(itemView);
        receptionLogo = itemView.findViewById(R.id.image_doc_item);
        receptionTitle = itemView.findViewById(R.id.tv_price_name_item);
        receptionDoctor = itemView.findViewById(R.id.tv_doc_name_item);
        receptionTime = itemView.findViewById(R.id.tv_date_item);
        receptionCancel = itemView.findViewById(R.id.btn_cancel_profile_item);
        receptionConfirm = itemView.findViewById(R.id.btn_confirm_profile_item);

        receptionLogoNo = itemView.findViewById(R.id.image_doc_item_no);
        receptionTitleNo = itemView.findViewById(R.id.tv_price_name_item_no);
        receptionDoctorNo = itemView.findViewById(R.id.tv_doc_name_item_no);
        receptionTimeNo = itemView.findViewById(R.id.tv_date_item_no);
    }

    public void onBindButton(VisitResponse response)
    {
        if (response != null){
            if (response.getTitle() != null && receptionTitle != null){
                receptionTitle.setText(response.getTitle());
            }
            if (response.getFullName() != null && receptionDoctor != null){
                receptionDoctor.setText(response.getFullName());
            }
            if (response.getAdmDate() != null && receptionTime != null){
                receptionTime.setText(response.getAdmDate());
            }
//            if (response.getPhoto() != null && receptionLogo != null){
//                receptionLogo.setImageBitmap(response.getPhoto());
//            }
        }
        Timber.d("response == null");

        receptionConfirm.setOnClickListener(view ->
        {

        });

        receptionCancel.setOnClickListener(view ->
        {

        });



//        if (group.getTitle().equals("Прошедшие")) {
//            mReception.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reception_complete_green_24dp, 0, 0, 0);
//        } else if (group.getTitle().equals("Предстоящие")) {
//            mReception.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reception_actual_grey_24dp, 0, 0, 0);
//        }
    }

    public void onBindNoButton(VisitResponse response)
    {
        if (response != null){
            if (response.getTitle() != null && receptionTitleNo != null){
                receptionTitleNo.setText(response.getTitle());
            }
            if (response.getFullName() != null && receptionDoctorNo != null){
                receptionDoctorNo.setText(response.getFullName());
            }
            if (response.getAdmDate() != null && receptionTimeNo != null){
                receptionTimeNo.setText(response.getAdmDate());
            }
        }
        Timber.d("response == null");

    }
}