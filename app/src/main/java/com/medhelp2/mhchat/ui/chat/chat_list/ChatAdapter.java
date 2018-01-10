package com.medhelp2.mhchat.ui.chat.chat_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.MessageResponse;
import com.medhelp2.mhchat.ui.base.BaseViewHolder;
import com.medhelp2.mhchat.utils.main.TimesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_FROM = 1;
    private static final int VIEW_TYPE_SELF = 2;

    private List<MessageResponse> response;
    private int idUser;

    ChatAdapter(List<MessageResponse> response, int idUser)
    {
        this.response = response;
        this.idUser = idUser;
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
            case VIEW_TYPE_FROM:
                return new FromViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_from, parent, false));
            case VIEW_TYPE_SELF:
                return new SelfViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_self, parent, false));
            case VIEW_TYPE_EMPTY:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_chat, parent, false));
            default:
                return new ErrorViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error_download, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        MessageResponse message;

        if (response != null && response.size() > 0)
        {
            message = response.get(position);
            int userId = message.getIdUser();

            if (userId == idUser)
            {
                return VIEW_TYPE_SELF;
            } else
            {
                return VIEW_TYPE_FROM;
            }
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

    @SuppressWarnings("unused")
    void addItems(List<MessageResponse> repoList)
    {
        response.clear();
        response.addAll(repoList);
        notifyDataSetChanged();
    }

    class SelfViewHolder extends BaseViewHolder
    {
        @BindView(R.id.message_self)
        TextView messageSelf;

        @BindView(R.id.timestamp_self)
        TextView timestampSelf;

        @BindView(R.id.unread_message_self)
        ImageView unreadImage;

        SelfViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear()
        {
            messageSelf.setText("");
            timestampSelf.setText("");
            unreadImage.setVisibility(View.GONE);
        }

        public void onBind(int position)
        {
            super.onBind(position);

            final MessageResponse repo = response.get(position);

            if (repo.getTimeStamp() != null)
            {
                timestampSelf.setText(TimesUtils.getTime(repo.getTimeStamp()));
            }

            if (repo.getMessage() != null)
            {
                messageSelf.setText(repo.getMessage());
            }

            if (repo.getIsRead() != null)
            {
                if (repo.getIsRead().equals("false"))
                {
                    unreadImage.setVisibility(View.GONE);
                } else if (repo.getIsRead().equals("true"))
                {
                    unreadImage.setVisibility(View.VISIBLE);
                }
            }
            itemView.setOnClickListener(v ->
            {
            });
        }
    }

    class FromViewHolder extends BaseViewHolder
    {
        @BindView(R.id.message_from)
        TextView messageFrom;

        @BindView(R.id.timestamp_from)
        TextView timestampFrom;

        FromViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear()
        {
            messageFrom.setText("");
            timestampFrom.setText("");
        }

        public void onBind(int position)
        {
            super.onBind(position);

            final MessageResponse repo = response.get(position);

            if (repo.getTimeStamp() != null)
            {
                timestampFrom.setText(TimesUtils.getTime(repo.getTimeStamp()));
            }

            if (repo.getMessage() != null)
            {
                messageFrom.setText(repo.getMessage());
            }
        }
    }

    class EmptyViewHolder extends BaseViewHolder
    {
        @BindView(R.id.empty_chat_tv_info)
        TextView tvInfoChatMessage;

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

    class ErrorViewHolder extends BaseViewHolder
    {
        @BindView(R.id.err_tv_message)
        TextView errMessage;

        ErrorViewHolder(View itemView)
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
