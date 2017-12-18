package com.medhelp2.mhchat.ui.search.select;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface SelectPresenterHelper<V extends SelectViewHelper> extends MvpPresenter<V>
{
    void loadDocList(int idService);
}
