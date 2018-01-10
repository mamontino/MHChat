package com.medhelp2.mhchat.ui.doctor;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.DoctorInfo;
import com.medhelp2.mhchat.ui.base.BaseViewHolder;
import com.medhelp2.mhchat.ui.search.SearchPresenter;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.main.TimesUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorsAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    @Inject
    SearchPresenter presenter;

    private static final int VIEW_TYPE_NORMAL = 11;
    private List<DoctorInfo> response;

    public DoctorsAdapter(List<DoctorInfo> response)
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
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_doctor, parent, false));
            default:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_error_download, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if (response != null && response.size() > 0)
        {
            return VIEW_TYPE_NORMAL;
        }
        return 0;
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

    @SuppressWarnings("unused")
    void addItems(List<DoctorInfo> repoList)
    {
        response.clear();
        response.addAll(repoList);
        notifyDataSetChanged();
    }

    class ViewHolder extends BaseViewHolder
    {
        @BindView(R.id.doc_name_tv)
        TextView docName;

        @BindView(R.id.doc_spec_tv)
        TextView docExp;

        @BindView(R.id.doc_img)
        ImageView docImage;


        ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear()
        {
            docImage.setImageDrawable(null);
            docName.setText("");
            docExp.setText("");
        }

        public void onBind(int position)
        {
            super.onBind(position);
            final DoctorInfo repo = response.get(position);
            if (repo != null)
            {
                docName.setText(repo.getFullName());
                int type = TimesUtils.getTypeYear(repo.getExpr());

                Picasso.with(docImage.getContext())
                        .load(Uri.parse(repo.getPhoto() + "&token=" + AppConstants.API_KEY))
                        .placeholder(R.drawable.holder_doctor)
                        .error(R.drawable.holder_doctor)
                        .into(docImage);

                String exp = null;
                if (type == AppConstants.TYPE_YEAR_ONE)
                {
                    exp = this.docExp.getResources().getString(R.string.f_exp_year, repo.getExpr());
                } else if (type == AppConstants.TYPE_YEAR_TWO)
                {
                    exp = this.docExp.getResources().getString(R.string.f_exp_2, repo.getExpr());
                }
                if (type == AppConstants.TYPE_YEAR_THREE)
                {
                    exp = this.docExp.getResources().getString(R.string.f_exp_1, repo.getExpr());
                }
                docExp.setText(exp);
            }
        }
    }

    class EmptyViewHolder extends BaseViewHolder
    {
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

    void setFilter(List<DoctorInfo> filterService)
    {
        response = new ArrayList<>();
        response.addAll(filterService);
        notifyDataSetChanged();
    }
}
