package com.medhelp2.mhchat.ui.sale;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface SalePresenterHelper<V extends SaleViewHelper> extends MvpPresenter<V>
{
    public void getCenterInfo();

    void updateSaleList();

    void removePassword();

//    void loadUserInfo(int id);

//    void updateUnread();
}
