package com.medhelp2.mhchat.ui.contacts;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.ui.base.BaseViewHolder;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.view.RoundImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    @Inject
    ContactsPresenter presenter;

    private static final int VIEW_TYPE_EMPTY = 10;
    private static final int VIEW_TYPE_NORMAL = 11;

    private List<RoomResponse> response;

    public ContactsAdapter(List<RoomResponse> response)
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
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
            default:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error_download, parent, false));
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
            return response.size();
        } else
        {
            return 1;
        }
    }

    void addItems(List<RoomResponse> repoList)
    {
        response.clear();
        response.addAll(repoList);
        notifyDataSetChanged();
    }

    class ViewHolder extends BaseViewHolder
    {

        @BindView(R.id.contacts_img)
        ImageView contactImage;

        @BindView(R.id.contacts_tv_username)
        TextView tvUsername;

        //        @BindView(R.id.contacts_tv_date)
        //        TextView tvDate;
        //
        //        @BindView(R.id.contacts_tv_last_message)
        //        TextView tvLastMessage;

        @BindView(R.id.contacts_tv_unread)
        TextView tvUnread;


        ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear()
        {
            contactImage.setImageDrawable(null);
            tvUsername.setText("");
            //            tvLastMessage.setText("");
            //            tvDate.setText("");
            tvUnread.setText("");
        }

        public void onBind(int position)
        {
            super.onBind(position);
            final RoomResponse repo = response.get(position);
            if (repo != null)
            {
                String imageUrl = repo.getImage();

                Picasso.with(itemView.getContext())
                        .load(Uri.parse(imageUrl + "&token=" + AppConstants.API_KEY))
                        .error(R.drawable.holder_doctor)
                        .placeholder(R.drawable.holder_doctor)
                        .transform(new RoundImage())
                        .into(contactImage);

                if (repo.getFullName() != null)
                {
                    tvUsername.setText(repo.getFullName());
                }

                //                if (repo.getUnreadCountApiCall() > 0 && repo.getUnreadCountApiCall() < 99)
                //                {
                //                    Timber.d("repo.getUnreadCountApiCall() = " + repo.getUnreadCountApiCall());
                //                    try
                //                    {
                //                        String count = String.valueOf(repo.getUnreadCountApiCall());
                //                        tvUnread.setText(count);
                //                        tvUnread.setVisibility(View.VISIBLE);
                //                    } catch (Exception e)
                //                    {
                //                        Timber.e("Ошибка установки count: " + e.getMessage());
                //                    }
                //                } else if (repo.getUnreadCountApiCall() > 99)
                //                {
                //                    tvUnread.setText("∞");
                //                    tvUnread.setVisibility(View.VISIBLE);
                //                } else
                //                {
                //                    tvUnread.setVisibility(View.GONE);
                //                }
                //
                //                if (repo.getLastMessage() != null)
                //                {
                //                    tvLastMessage.setText(repo.getLastMessage());
                //                }
                //
                //                contactImage.setOnClickListener(v ->
                //                {
                //                    if (repo.getIdChatRoom() != 0)
                //                    {
                //                        Toast.makeText(contactImage.getContext(), "Details Fragment open", Toast.LENGTH_SHORT).show();
                //                    }
                //                });
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
}
