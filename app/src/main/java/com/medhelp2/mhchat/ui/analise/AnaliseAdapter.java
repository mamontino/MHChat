package com.medhelp2.mhchat.ui.analise;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.AnaliseResponse;
import com.medhelp2.mhchat.ui.base.BaseViewHolder;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnaliseAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    @Inject
    AnalisePresenter presenter;

    private static final int VIEW_TYPE_NORMAL = 11;
    private List<AnaliseResponse> response;

    public AnaliseAdapter(List<AnaliseResponse> response)
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
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_analise, parent, false));
        }
        return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error_download, parent, false));
    }

    @Override
    public int getItemViewType(int position)
    {
        if (response != null && response.size() > 0)
        {
            return VIEW_TYPE_NORMAL;
        } else
        {
            return 0;
        }
    }

    @Override
    public int getItemCount()
    {
        if (response != null && response.size() > 0)
        {
            return response.size();
        } else
        {
            return 0;
        }
    }

    void addItems(List<AnaliseResponse> repoList)
    {
        response.clear();
        response.addAll(repoList);
        notifyDataSetChanged();
    }

    class ViewHolder extends BaseViewHolder
    {
        @BindView(R.id.analise_img_item)
        ImageView analiseImage;

        @BindView(R.id.analise_tv_item)
        TextView analiseName;

        @BindView(R.id.analise_date_item)
        TextView analiseDate;

        ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear()
        {
            analiseImage.setImageDrawable(null);
            analiseDate.setText("");
            analiseName.setText("");
        }

        public void onBind(int position)
        {
            super.onBind(position);
            final AnaliseResponse repo = response.get(position);

            if (repo != null)
            {
                if (repo.getImage() != null)
                {
                    analiseDate.setOnClickListener(v ->
                            presenter.loadFile(repo.getImage()));
                }

                if (repo.getDate() != null)
                {
                    analiseDate.setText(repo.getDate());
                }

                if (repo.getName() != null)
                {
                    analiseName.setText(repo.getName());
                }
            }
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
}
