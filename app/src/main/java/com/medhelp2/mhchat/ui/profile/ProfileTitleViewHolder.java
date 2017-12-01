package com.medhelp2.mhchat.ui.profile;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class ProfileTitleViewHolder extends GroupViewHolder
{
    private TextView title;

    public ProfileTitleViewHolder(View itemView)
    {
        super(itemView);
        title = itemView.findViewById(R.id.tv_profile_item_title);
    }

    @Override
    public void expand()
    {
        title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);
        Log.i("Adapter", "expand");
    }

    @Override
    public void collapse()
    {
        Log.i("Adapter", "collapse");
        title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
    }

    public void setGroupName(ExpandableGroup group)
    {
        if (group.getTitle() != null){
            title.setText(group.getTitle());
        }else {
            title.setText(" ");
        }
    }
}