package com.medhelp2.mhchat.ui.contacts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.RoomResponse;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.chat.ChatActivity;
import com.medhelp2.mhchat.ui.doctor.DoctorsActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;
import com.medhelp2.mhchat.ui.rating.RateFragment;
import com.medhelp2.mhchat.ui.sale.SaleActivity;
import com.medhelp2.mhchat.ui.schedule.ScheduleActivity;
import com.medhelp2.mhchat.ui.search.SearchActivity;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.main.NotificationUtils;
import com.medhelp2.mhchat.utils.view.ItemListDecorator;
import com.medhelp2.mhchat.utils.view.RecyclerViewClickListener;
import com.medhelp2.mhchat.utils.view.RecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.medhelp2.mhchat.ui.chat.chat_list.ChatListFragment.BROADCAST_INCOMING_MESSAGE;

public class ContactsActivity extends BaseActivity implements ContactsViewHelper,
        NavigationView.OnNavigationItemSelectedListener
{
    public static final String TAG = "ChatListFragment";
    public static final String PARAM_STATUS = "status";
    public static final int STATUS_START = 150;
    public static final int STATUS_FINISH = 250;

    private BroadcastReceiver incomingMessageReceiver;

    @Inject
    ContactsAdapter adapter;

    @Inject
    LinearLayoutManager layoutManager;

    @BindView(R.id.rv_contacts)
    RecyclerView recyclerView;

    private ArrayList<RoomResponse> contactsList;

    @Inject
    ContactsPresenterHelper<ContactsViewHelper> presenter;

    @BindView(R.id.toolbar_contacts)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar_contacts)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.drawer_contacts)
    DrawerLayout drawer;

    @BindView(R.id.nav_view_contacts)
    NavigationView navView;

    private ActionBarDrawerToggle drawerToggle;

    private TextView headerTitle;
    private ImageView headerLogo;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, ContactsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        presenter.getCenterInfo();
        setUp();
        presenter.updateUserList();
    }

    @Override
    public void updateHeader(CenterResponse response)
    {
        View headerLayout = navView.getHeaderView(0);
        headerLogo = headerLayout.findViewById(R.id.header_logo);
        headerTitle = headerLayout.findViewById(R.id.header_tv_title);

        Timber.d("updateHeader: " + response.getTitle());
        if (response.getLogo() != null)
        {
            //       headerLogo.setImageBitmap(response.getLogo());
        }
        headerTitle.setText(response.getTitle());
    }

    private void setupToolbar()
    {
        Timber.d("setupToolbar");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        toolbarLayout.setTitleEnabled(false);

        AppBarLayout.LayoutParams appBarParams =
                (AppBarLayout.LayoutParams) toolbarLayout.getLayoutParams();

        if (actionBar != null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void lockDrawer()
    {
        Timber.d("lockDrawer");
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer()
    {
        Timber.d("unlockDrawer");
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void closeNavigationDrawer()
    {
        Timber.d("closeNavigationDrawer");
        if (drawer != null)
        {
            drawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void setUp()
    {
        Timber.d("setUp");
        setupToolbar();
        setupDrawer();
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemListDecorator(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this, recyclerView, new RecyclerViewClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra(AppConstants.ID_ROOM, contactsList.get(position).getIdRoom());
                intent.putExtra(AppConstants.ROOM_NAME, contactsList.get(position).getFullName());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position)
            {

            }
        }));
    }

    private void setupDrawer()
    {
        Timber.d("setupDrawer");
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }
        };

        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        setupNavMenu();
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void showRateFragment()
    {
        RateFragment.newInstance().show(getSupportFragmentManager());
    }

    private void setupNavMenu()
    {
        View headerLayout = navView.getHeaderView(0);
        headerLogo = headerLayout.findViewById(R.id.header_logo);
        headerTitle = headerLayout.findViewById(R.id.header_tv_title);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void showProfileActivity()
    {
        Intent intent = ProfileActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showSearchActivity()
    {
        Intent intent = SearchActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showScheduleActivity()
    {
        Intent intent = ScheduleActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showDoctorsActivity()
    {
        Intent intent = DoctorsActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showLoginActivity()
    {
        Intent intent = LoginActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        presenter.removePassword();
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId())
        {
            case R.id.nav_item_chat:
                return true;

//            case R.id.nav_item_feedback:
//                showAboutFragment();
//                return true;

            case R.id.nav_item_logout:
                showLoginActivity();
                return true;

            case R.id.nav_item_sale:
                showSaleActivity();
                return true;

            case R.id.nav_item_main:
                showProfileActivity();
                return true;

            case R.id.nav_item_price:
                showSearchActivity();
                return true;

            case R.id.nav_item_record:
                showSearchActivity();
                return true;

            case R.id.nav_item_schedule:
                showScheduleActivity();
                return true;

//            case R.id.nav_item_settings:
//                showSettingsActivity();
//                return true;

            case R.id.nav_item_rate:
                showRateFragment();
                return true;

            case R.id.nav_item_staff:
                showDoctorsActivity();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void showSaleActivity()
    {
        Intent intent = SaleActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void onStop()
    {
        if (incomingMessageReceiver != null)
        {
            unregisterReceiver(incomingMessageReceiver);
        }
        super.onStop();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        registerIncomingMessageReceiver();
    }


    @Override
    public void updateUserListData(List<RoomResponse> response)
    {
        contactsList = new ArrayList<>();
        contactsList.addAll(response);
        adapter.addItems(contactsList);
        adapter.addItems(response);
    }

    private void registerIncomingMessageReceiver()
    {
        incomingMessageReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Timber.d("Получено новое сообщение");
                int status = intent.getIntExtra(PARAM_STATUS, 0);
                Timber.d("onReceive: status = " + status);

                if (status == STATUS_START)
                {
                    Timber.d("Запуск BroadcastReceiver");
                }
                if (status == STATUS_FINISH)
                {
                    Timber.d("Остановка BroadcastReceiver");
                    presenter.updateUserList();
                    NotificationUtils.clearNotifications(getApplicationContext());
                }
            }
        };

        IntentFilter filterIncomingMessage = new IntentFilter(BROADCAST_INCOMING_MESSAGE);
        registerReceiver(incomingMessageReceiver, filterIncomingMessage);
    }
}
