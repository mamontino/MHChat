package com.medhelp2.mhchat.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.chat.chat_list.ChatListFragment;
import com.medhelp2.mhchat.utils.main.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ChatActivity extends BaseActivity implements ChatViewHelper
{
    @Inject
    ChatPresenterHelper<ChatViewHelper> mPresenter;

    @BindView(R.id.toolbar_chat)
    Toolbar toolbar;

    @BindView(R.id.drawer_chat)
    DrawerLayout drawer;

    @BindView(R.id.nav_view_chat)
    NavigationView navView;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, ChatActivity.class);
    }

    private int chatRoomId;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getActivityComponent().inject(this);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        chatRoomId = intent.getIntExtra(AppConstants.ID_ROOM, 0);
        title = intent.getStringExtra(AppConstants.ROOM_NAME);

        if (chatRoomId == 0)
        {
            Timber.e("Контакт с данным идентификатором не найден");
            showError("Контакт с данным идентификатором не найден");
            finish();
        }
        setUp();

        if (savedInstanceState != null)
        {
            Timber.d("savedInstanceState != null");
        } else
        {
            Timber.d("savedInstanceState == null");
            showChatListFragment();
        }
    }

    @Override
    public void showChatListFragment()
    {
        Timber.d("showChatListFragment");
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
        Timber.d("onFragmentDetached");
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
        Timber.d("setupToolbar");
        if (toolbar != null)
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
        Timber.d("setUp");
        setupToolbar();
    }
}
