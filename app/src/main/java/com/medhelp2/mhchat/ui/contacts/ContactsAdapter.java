package com.medhelp2.mhchat.ui.contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.ui.base.BaseViewHolder;
import com.medhelp2.mhchat.utils.view.RoundImage;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactsAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    @Inject
    ContactsPresenter presenter;

    private static final int VIEW_TYPE_EMPTY = 10;
    private static final int VIEW_TYPE_NORMAL = 11;

    private Callback callback;
    private List<RoomResponse> response;

    public ContactsAdapter(List<RoomResponse> response)
    {
        this.response = response;
    }

    public void setCallback(Callback callback)
    {
        this.callback = callback;
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
            case VIEW_TYPE_EMPTY:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error_download, parent, false));
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
            return 0;
        }
    }

    void addItems(List<RoomResponse> repoList)
    {
        response.clear();
        response.addAll(repoList);
        notifyDataSetChanged();
    }

    interface Callback
    {
        void onEmptyViewAddContactClick();
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
                Picasso.with(itemView.getContext())
                        .load(repo.getIdRoom())
                        .error(R.drawable.holder_doctor)
                        .placeholder(R.drawable.holder_doctor)
                        .transform(new RoundImage())
                        .into(contactImage);

                if (repo.getFullName() != null)
                {
                    tvUsername.setText(repo.getFullName());
                }

//                if (repo.getUnreadCount() > 0 && repo.getUnreadCount() < 99)
//                {
//                    Timber.d("repo.getUnreadCount() = " + repo.getUnreadCount());
//                    try
//                    {
//                        String count = String.valueOf(repo.getUnreadCount());
//                        tvUnread.setText(count);
//                        tvUnread.setVisibility(View.VISIBLE);
//                    } catch (Exception e)
//                    {
//                        Timber.e("Ошибка установки count: " + e.getMessage());
//                    }
//                } else if (repo.getUnreadCount() > 99)
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
//        @BindView(R.id.empty_image_add_contact)
//        ImageButton btnAddContact;
//
//        @BindView(R.id.empty_tv_add_contact)
//        TextView tvInfoMessage;

        EmptyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear()
        {
        }

        @OnClick(R.id.err_btn_retry)
        void onClickAddContact()
        {
            if (callback != null)
                callback.onEmptyViewAddContactClick();
        }
    }
}
