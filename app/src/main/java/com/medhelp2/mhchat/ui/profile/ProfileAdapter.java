package com.medhelp2.mhchat.ui.profile;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.VisitResponse;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import timber.log.Timber;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ProfileAdapter extends MultiTypeExpandableRecyclerViewAdapter<ProfileTitleViewHolder, ProfileVisitViewHolder>
{
    private static final int BUTTON_MODE = 3;
    private static final int NO_BUTTON_MODE = 4;
    private static final int ERROR_MODE = 5;
    private Context context;

    public ProfileAdapter(Activity context, List<? extends ExpandableGroup> groups)
    {
        super(groups);
        this.context = context;
    }

    @Override
    public ProfileTitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (inflater != null)
        {
            view = inflater.inflate(R.layout.item_groupe, parent, false);
        }
        return new ProfileTitleViewHolder(view);
    }

    @Override
    public ProfileVisitViewHolder onCreateChildViewHolder(ViewGroup parent, final int viewType)
    {
        Timber.d("ProfileVisitViewHolder");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        switch (viewType)
        {
            case BUTTON_MODE:
                View viewBtn = null;
                if (inflater != null)
                {
                    viewBtn = inflater.inflate(R.layout.item_profile_btn, parent, false);
                }
                return new ProfileVisitViewHolder(viewBtn);
            case NO_BUTTON_MODE:
                View viewNoBtn = null;
                if (inflater != null)
                {
                    viewNoBtn = inflater.inflate(R.layout.item_profile_no_btn, parent, false);
                }
                return new ProfileVisitViewHolder(viewNoBtn);
            default:
                throw new IllegalArgumentException("Invalid viewType");
        }
    }

    @Override
    public void onBindGroupViewHolder(ProfileTitleViewHolder holder, int flatPosition, ExpandableGroup group)
    {
        holder.setGroupName(group);
    }

    @Override
    public void onBindChildViewHolder(ProfileVisitViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex)
    {
        int viewType = getItemViewType(flatPosition);
        VisitResponse visit = ((ProfileParentModel) group).getItems().get(childIndex);
        switch (viewType)
        {
            case BUTTON_MODE:
                holder.onBindButton(visit);
                break;
            case NO_BUTTON_MODE:
                holder.onBindNoButton(visit);
                break;
        }
    }

    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex)
    {
        if (group.getTitle().equals("Прошедшие"))
        {
            Timber.d("ViewType NO_BUTTON_MODE");
            return NO_BUTTON_MODE;
        } else if (group.getTitle().equals("Предстоящие"))
        {
            Timber.d("ViewType BUTTON_MODE");
            return BUTTON_MODE;
        } else
        {
            Timber.d("ViewType ERROR_MODE");
            return ERROR_MODE;
        }
    }

    @Override
    public boolean isChild(int viewType)
    {
        return viewType == BUTTON_MODE || viewType == NO_BUTTON_MODE;
    }
}
