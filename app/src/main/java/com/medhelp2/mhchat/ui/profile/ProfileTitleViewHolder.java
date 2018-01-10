package com.medhelp2.mhchat.ui.profile;


import android.view.View;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

class ProfileTitleViewHolder extends GroupViewHolder
{
    private TextView title;

    ProfileTitleViewHolder(View itemView)
    {
        super(itemView);
        title = itemView.findViewById(R.id.tv_profile_item_title);
    }

    @Override
    public void expand()
    {
        title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);
    }

    @Override
    public void collapse()
    {
        title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
    }

    void setGroupName(ExpandableGroup group)
    {
        if (group.getTitle() != null)
        {
            title.setText(group.getTitle());
        }

        if (group.getTitle().equals("Предстоящие"))
        {
            title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);
        }
    }
}
