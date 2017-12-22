package com.medhelp2.mhchat.ui.doctor.service;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.base.BaseViewHolder;
import com.medhelp2.mhchat.ui.schedule.ScheduleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    private static final int VIEW_TYPE_NORMAL = 11;
    private int idDoctor;
    private List<ServiceResponse> response;

    ServiceAdapter(List<ServiceResponse> response, int idDoc)
    {
        this.response = response;
        idDoctor = idDoc;
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
                return new ViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_search, parent, false));
            default:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_error_download, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if (response != null && response.size() > 0)
        {
            return VIEW_TYPE_NORMAL;
        }
        return 1;
    }

    @Override
    public int getItemCount()
    {
        return response.size();
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

        @BindView(R.id.btn_search_record)
        Button recordButton;


        ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view ->
            {
                if (recordButton.getVisibility() == View.VISIBLE)
                {
                    recordButton.setVisibility(View.GONE);
                } else
                {
                    recordButton.setVisibility(View.VISIBLE);
                }
            });
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
            recordButton.setOnClickListener(view ->
                    startScheduleActivity(repo)
            );
        }

        private void startScheduleActivity(ServiceResponse repo)
        {
            Intent intent = ScheduleActivity.getStartIntent(recordButton.getContext());
            assert repo != null;
            intent.putExtra(ScheduleActivity.EXTRA_DATA_ID_SERVICE, repo.getIdService());
            intent.putExtra(ScheduleActivity.EXTRA_DATA_ADM, repo.getAdmission());
            intent.putExtra(ScheduleActivity.EXTRA_DATA_ID_DOCTOR, idDoctor);
            recordButton.getContext().startActivity(intent);
        }
    }

    class EmptyViewHolder extends BaseViewHolder
    {
        @BindView(R.id.err_tv_message)
        TextView errMessage;

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

    void setFilter(List<ServiceResponse> filterService)
    {
        response = new ArrayList<>();
        response.addAll(filterService);
        notifyDataSetChanged();
    }
}
