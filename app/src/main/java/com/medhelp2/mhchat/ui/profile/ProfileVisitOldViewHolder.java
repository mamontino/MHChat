package com.medhelp2.mhchat.ui.profile;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.VisitResponse;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;


public class ProfileVisitOldViewHolder extends ChildViewHolder
{
    private ImageView mReceptionLogo;
    private TextView mReceptionTitle;
    private TextView mReceptionDoctor;
    private TextView mReceptionTime;

    public ProfileVisitOldViewHolder(View itemView)
    {
        super(itemView);
        mReceptionLogo = itemView.findViewById(R.id.image_doc_item_no);
        mReceptionTitle = itemView.findViewById(R.id.tv_price_name_item_no);
        mReceptionDoctor = itemView.findViewById(R.id.tv_doc_name_item_no);
        mReceptionTime = itemView.findViewById(R.id.tv_date_item_no);
    }

    public void onBind(VisitResponse response, ExpandableGroup group)
    {
        mReceptionTitle.setText(response.getTitle());
        mReceptionDoctor.setText(response.getFullName());
        mReceptionTime.setText(response.getAdmDate());

//        TODO: Добавить изображение к описанию
//
//        if (group.getTitle().equals("Прошедшие")) {
//            mReception.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reception_complete_green_24dp, 0, 0, 0);
//        } else if (group.getTitle().equals("Предстоящие")) {
//            mReception.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reception_actual_grey_24dp, 0, 0, 0);
//        }
    }
}