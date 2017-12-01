package com.medhelp2.mhchat.ui.doctor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.ui.base.BaseViewHolder;
import com.medhelp2.mhchat.ui.search.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DocListAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    @Inject
    SearchPresenter presenter;

    private static final int VIEW_TYPE_EMPTY = 10;
    private static final int VIEW_TYPE_NORMAL = 11;

    private List<Doctor> response;

    public DocListAdapter(List<Doctor> response)
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
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false));
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

    void addItems(List<Doctor> repoList)
    {
        response.clear();
        response.addAll(repoList);
        notifyDataSetChanged();
    }

    class ViewHolder extends BaseViewHolder
    {
        @BindView(R.id.doc_name_tv)
        TextView tvTitle;

        @BindView(R.id.doc_spec_tv)
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
            final Doctor repo = response.get(position);
            if (repo != null)
            {
                tvTitle.setText(repo.getFullName());
                tvPrice.setText(repo.getSpecialty());
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

    public void setFilter(List<Doctor> filterService) {
        response = new ArrayList<>();
        response.addAll(filterService);
//        addItems(filterService);
        notifyDataSetChanged();
    }
}
