package com.medhelp2.mhchat.ui.analise;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface AnalisePresenterHelper<V extends AnaliseViewHelper> extends MvpPresenter<V>
{
    void getCenterInfo();

    void updateAnaliseList();

    void removePassword();

    void loadFile(String image);

    void unSubscribe();
}
