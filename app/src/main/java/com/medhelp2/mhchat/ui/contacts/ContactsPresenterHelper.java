package com.medhelp2.mhchat.ui.contacts;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface ContactsPresenterHelper<V extends ContactsViewHelper> extends MvpPresenter<V>
{
    void getCenterInfo();

    void updateUserList();

    void removePassword();

//    void loadUserInfo(int id);

//    void updateUnread();

    void unSubscribe();
}
