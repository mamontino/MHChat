package com.medhelp2.mhchat.ui.sale;

import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface SalePresenterHelper<V extends SaleViewHelper> extends MvpPresenter<V>
{
    void getCenterInfo();

    void updateSaleList();

    void removePassword();

    void unSubscribe();
}
