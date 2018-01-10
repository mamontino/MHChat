package com.medhelp2.mhchat.ui.chat.chat_list;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
@SuppressWarnings("unused")
public interface ChatListPresenterHelper<V extends ChatListViewHelper> extends MvpPresenter<V>
{
    void loadMessageList(int idChat);

    void updateMessageList(int idChat);

    void sendMessage(int idRoom, String message);

    void loadLocalMessages(int idRoom);

    void loadMessagesFromServer(int idRoom);

    void readMessages(int idRoom);

    void unSubscribe();
}
