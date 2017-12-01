package com.medhelp2.mhchat.ui.contacts.contacts_list;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface ContactsListPresenterHelper<V extends ContactsListViewHelper> extends MvpPresenter<V> {

    void updateUserList();

//    void loadUserInfo(int id);

//    void updateUnread();
}
