package com.medhelp2.mhchat.ui.chat;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.chat.chat_list.ChatListFragment;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.rx.RxEvents;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ChatActivity extends BaseActivity implements ChatViewHelper,
        SwipeRefreshLayout.OnRefreshListener
{
    @Inject
    ChatPresenterHelper<ChatViewHelper> presenter;

    @BindView(R.id.toolbar_chat)
    Toolbar toolbar;

    @BindView(R.id.drawer_chat)
    DrawerLayout drawer;

    @BindView(R.id.nav_view_chat)
    NavigationView navView;

    @BindView(R.id.swipe_chat)
    SwipeRefreshLayout swipeChat;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, ChatActivity.class);
    }

    private int chatRoomId;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.d("OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getActivityComponent().inject(this);

        ButterKnife.bind(this);

        chatRoomId = getIntent().getIntExtra(AppConstants.ID_ROOM, 0);
        title = getIntent().getStringExtra(AppConstants.ROOM_NAME);

        if (chatRoomId == 0)
        {
            showError("Контакт с данным идентификатором не найден");
            finish();
        }

        setUp();

        if (savedInstanceState == null)
        {
            showChatListFragment();
        }
    }



    @Override
    public void showChatListFragment()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.fr_chat, ChatListFragment.newInstance(chatRoomId), ChatListFragment.TAG)
                .commit();
    }

    @Override
    public void onFragmentAttached()
    {
    }

    @Override
    public void onFragmentDetached(String tag)
    {
        getSupportFragmentManager();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
        }
    }

    private void setupToolbar()
    {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white_24dp);

            if (title != null)
            {
                actionBar.setTitle(title);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void setUp()
    {
        setupToolbar();
        setupRefresh();
    }

    private void setupRefresh()
    {
        swipeChat.setOnRefreshListener(this);
        swipeChat.setColorSchemeResources(R.color.color_accent);
    }

    @SuppressWarnings("unused")
    private void swipeDismiss()
    {
        swipeChat.setRefreshing(false);
    }

    @Override
    public void onRefresh()
    {
        ((MainApp) getApplication())
                .bus()
                .send(new RxEvents.startChatRefreshing());
    }
}
