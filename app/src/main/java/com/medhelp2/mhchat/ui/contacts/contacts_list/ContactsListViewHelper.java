package com.medhelp2.mhchat.ui.contacts.contacts_list;

import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.ui.base.MvpView;

import java.util.List;

public interface ContactsListViewHelper extends MvpView
{
    void updateUserListData(List<RoomResponse> response);
}
