package com.medhelp2.mhchat.ui.schedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ScheduleAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    @Inject
    SchedulePresenter presenter;

    private static final int VIEW_TYPE_EMPTY = 10;
    private static final int VIEW_TYPE_NORMAL = 11;

    private List<ServiceResponse> response;

    public ScheduleAdapter(List<ServiceResponse> response)
    {
        this.response = response;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position)
    {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case VIEW_TYPE_NORMAL:
                Timber.d("VIEW_TYPE_NORMAL");
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_rv, parent, false));
            case VIEW_TYPE_EMPTY:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_contact, parent, false));
            default:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_contact, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if (response != null && response.size() > 0)
        {
            return VIEW_TYPE_NORMAL;
        } else
        {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount()
    {
        if (response != null && response.size() > 0)
        {
            Timber.d("response != null");
            return response.size();
        } else
        {
            return 0;
        }
    }

    void addItems(List<ServiceResponse> repoList)
    {
        response.clear();
        response.addAll(repoList);
        notifyDataSetChanged();
    }

    class ViewHolder extends BaseViewHolder
    {
        @BindView(R.id.tv_search_item_name)
        TextView tvTitle;

        @BindView(R.id.tv_search_item_data)
        TextView tvPrice;


        ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear()
        {
            tvTitle.setText("");
            tvPrice.setText("");
        }

        public void onBind(int position)
        {
            super.onBind(position);
            final ServiceResponse repo = response.get(position);
            if (repo != null)
            {
                tvTitle.setText(repo.getTitle());
                tvPrice.setText(repo.getValue());
            }
        }
    }

    class EmptyViewHolder extends BaseViewHolder
    {
        @BindView(R.id.empty_image_add_contact)
        ImageButton btnAddContact;

        @BindView(R.id.empty_tv_add_contact)
        TextView tvInfoMessage;

        EmptyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear()
        {
        }
    }

    public void setFilter(List<ServiceResponse> filterService) {
        response = new ArrayList<>();
        response.addAll(filterService);
        notifyDataSetChanged();
    }
}
