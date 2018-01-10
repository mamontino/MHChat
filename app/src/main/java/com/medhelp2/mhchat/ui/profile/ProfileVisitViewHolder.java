package com.medhelp2.mhchat.ui.profile;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.VisitResponse;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.main.TimesUtils;
import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

class ProfileVisitViewHolder extends ChildViewHolder
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

    ProfileVisitViewHolder(View itemView)
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

    void onBindButton(VisitResponse response)
    {
        if (response != null)
        {
            if (response.getTitle() != null && receptionTitle != null)
            {
                receptionTitle.setText(response.getTitle());
            }
            if (response.getFullName() != null && receptionDoctor != null)
            {
                receptionDoctor.setText(response.getFullName());
            }
            if (response.getAdmDate() != null && receptionTime != null)
            {
                receptionTime.setText(TimesUtils.transformStringDate(response.getAdmDate()));
            }

            Picasso.with(receptionLogo.getContext())
                    .load(Uri.parse(response.getPhoto() + "&token=" + AppConstants.API_KEY))
                    .placeholder(R.drawable.holder_doctor)
                    .error(R.drawable.holder_doctor)
                    .into(receptionLogo);
        }

        receptionConfirm.setOnClickListener(view ->
        {

        });

        receptionCancel.setOnClickListener(view ->
        {

        });
    }

    void onBindNoButton(VisitResponse response)
    {
        if (response != null)
        {
            if (response.getTitle() != null && receptionTitleNo != null)
            {
                receptionTitleNo.setText(response.getTitle());
            }
            if (response.getFullName() != null && receptionDoctorNo != null)
            {
                receptionDoctorNo.setText(response.getFullName());
            }
            if (response.getAdmDate() != null && receptionTimeNo != null)
            {
                receptionTimeNo.setText(TimesUtils.transformStringDate(response.getAdmDate()));
            }

            Picasso.with(receptionLogoNo.getContext())
                    .load(Uri.parse(response.getPhoto() + "&token=" + AppConstants.API_KEY))
                    .placeholder(R.drawable.holder_doctor)
                    .error(R.drawable.holder_doctor)
                    .into(receptionLogoNo);
        }
    }
}