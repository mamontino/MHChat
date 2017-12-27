package com.medhelp2.mhchat.ui.chat.chat_list;

import com.medhelp2.mhchat.data.model.MessageResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;

public interface ChatListViewHelper extends MvpView
{
    void updateMessageList(List<MessageResponse> response);

    void stopRefreshing();
}
