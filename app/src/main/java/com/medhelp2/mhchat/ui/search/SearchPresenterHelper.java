package com.medhelp2.mhchat.ui.search;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface SearchPresenterHelper<V extends SearchViewHelper> extends MvpPresenter<V>
{
//    void getPrice();

//    void getFilteredPrice(int idSpec);

//    void getLocalPrice();

//    void getLocalPrice(int idCategory);

    void removePassword();

    void getData();

    void getCenterInfo();

//    void getLocalCategory();
}
