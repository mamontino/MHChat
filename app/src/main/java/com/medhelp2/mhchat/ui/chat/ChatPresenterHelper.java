package com.medhelp2.mhchat.ui.chat;


import com.medhelp2.mhchat.di.scope.PerActivity;
import com.medhelp2.mhchat.ui.base.MvpPresenter;

@PerActivity
public interface ChatPresenterHelper<V extends ChatViewHelper> extends MvpPresenter<V>
{
}
