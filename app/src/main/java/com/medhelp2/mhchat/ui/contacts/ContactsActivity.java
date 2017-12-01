package com.medhelp2.mhchat.ui.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.chat.ChatActivity;
import com.medhelp2.mhchat.ui.contacts.contacts_list.ContactsListFragment;
import com.medhelp2.mhchat.ui.doctor.DoctorsActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;
import com.medhelp2.mhchat.ui.search.SearchActivity;
import com.medhelp2.mhchat.ui.settings.SettingsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ContactsActivity extends BaseActivity implements ContactsViewHelper, NavigationView.OnNavigationItemSelectedListener
{
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
    private TextView headerDesc;
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
        if (savedInstanceState != null)
        {
            Timber.d("savedInstanceState != null");
        } else
        {
            showContactList();
            Timber.d("savedInstanceState == null");
        }
    }


    private void showContactList()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.fr_contacts, ContactsListFragment.newInstance(), ContactsListFragment.TAG)
                .commit();
        Timber.d("showContactList");
    }

    @Override
    public void updateHeader(CenterResponse response)
    {
        View headerLayout = navView.getHeaderView(0);
        headerLogo = headerLayout.findViewById(R.id.header_logo);
        headerDesc = headerLayout.findViewById(R.id.header_tv_desc);
        headerTitle = headerLayout.findViewById(R.id.header_tv_title);

        Timber.d("updateHeader: " + response.getTitle());
        if (response.getLogo() != null)
        {
            //       headerLogo.setImageBitmap(response.getLogo());
        }
        headerTitle.setText(response.getTitle());
        headerDesc.setText(response.getInfo());
    }

    private void setupToolbar()
    {
        Timber.d("setupToolbar");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        toolbarLayout.setTitleEnabled(false);

        AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) toolbarLayout.getLayoutParams();
        if (actionBar != null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public void onFragmentAttached()
    {
    }

    @Override
    public void onFragmentDetached(String tag)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            Timber.d("onFragmentDetached: " + tag);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_settings, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId())
//        {
//            case R.id.action_settings:
//                showSettingsActivity();
//                return true;
//            case android.R.id.home:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

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

    public void showChatActivity()
    {
        Intent intent = ChatActivity.getStartIntent(this);
        startActivity(intent);
        Timber.d("showChatActivity");
    }

//    public void openDetailsFragment()
//    {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .disallowAddToBackStack()
//                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                .add(R.id.fr, DetailsFragment.newInstance(), DetailsFragment.TAG)
//                .commit();
//
//        Timber.d("openDetailsFragment");
//    }

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

    private void setupNavMenu()
    {
        View headerLayout = navView.getHeaderView(0);
        headerLogo = headerLayout.findViewById(R.id.header_logo);
        headerTitle = headerLayout.findViewById(R.id.header_tv_title);
        headerDesc = headerLayout.findViewById(R.id.header_tv_desc);
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
    public void showSettingsActivity()
    {
        Intent intent = SettingsActivity.getStartIntent(this);
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
        startActivity(intent);
    }

    @Override
    public void showAboutFragment()
    {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId())
        {
            case R.id.nav_item_chat:
                return true;

            case R.id.nav_item_feedback:
                showAboutFragment();
                return true;

            case R.id.nav_item_logout:
                showLoginActivity();
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
                showSearchActivity();
                return true;

//            case R.id.nav_item_settings:
//                showSettingsActivity();
//                return true;

            case R.id.nav_item_staff:
                showDoctorsActivity();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }
}
