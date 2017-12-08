package com.medhelp2.mhchat.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.about.AboutFragment;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.doctor.DoctorsActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;
import com.medhelp2.mhchat.ui.rating.RateFragment;
import com.medhelp2.mhchat.ui.sale.SaleActivity;
import com.medhelp2.mhchat.ui.schedule.ScheduleActivity;
import com.medhelp2.mhchat.ui.settings.SettingsActivity;
import com.medhelp2.mhchat.utils.view.ItemListDecorator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SearchActivity extends BaseActivity implements SearchViewHelper, Spinner.OnItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener
{
    @Inject
    SearchPresenter<SearchViewHelper> presenter;

    @BindView(R.id.rv_search)
    RecyclerView recyclerView;

    @BindView(R.id.spinner_search)
    Spinner spinner;

    @BindView(R.id.toolbar_search)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar_search)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.drawer_search)
    DrawerLayout drawer;

    @BindView(R.id.nav_view_search)
    NavigationView navView;

    private ActionBarDrawerToggle drawerToggle;
//    private SwipeRefreshLayout refreshLayout;

    private TextView headerTitle;
    private TextView headerDesc;
    private ImageView headerLogo;

    private SearchAdapter adapter;
    private List<CategoryResponse> filterList;
    private List<ServiceResponse> serviceCash;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getActivityComponent().inject(this);
//        refreshLayout = findViewById(R.id.swipe_search);
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
        setUp();
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
        headerDesc.setText(response.getInfo());
    }

    @Override
    protected void setUp()
    {
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        presenter.getCenterInfo();
        setupToolbar();
        setupDrawer();
        serviceCash = new ArrayList<>();
        presenter.getData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupToolbar()
    {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) toolbarLayout.getLayoutParams();
        if (actionBar != null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                spinner.setSelection(0);
                final List<ServiceResponse> filteredModelList = filterService(serviceCash, newText);
                adapter.setFilter(filteredModelList);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    @Override
    public void updateView(List<CategoryResponse> categories, List<ServiceResponse> services)
    {
        Timber.d("updateCategory");

        adapter = new SearchAdapter(serviceCash);
        recyclerView.addItemDecoration(new ItemListDecorator(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        filterList = new ArrayList<>();
        filterList.add(0, new CategoryResponse("Все"));
        filterList.addAll(categories);

        SearchSpinnerAdapter spinnerAdapter = new SearchSpinnerAdapter(this, filterList);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                if (position == 0)
                {
                    adapter.addItems(services);
                } else
                {
                    List<ServiceResponse> serviceList = new ArrayList<>();
                    for (ServiceResponse serviceResponse : services)
                    {
                        if (serviceResponse.getIdSpec() == filterList.get(position).getIdSpec())
                        {
                            serviceList.add(serviceResponse);
                        }
                    }
                    adapter.addItems(serviceList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    @Override
    protected void onDestroy()
    {
        Timber.d("onDestroy");
        presenter.onDetach();
        super.onDestroy();
    }

    private List<ServiceResponse> filterService(List<ServiceResponse> models, String query)
    {
        query = query.toLowerCase();
        final List<ServiceResponse> filteredModelList = new ArrayList<>();
        for (ServiceResponse model : models)
        {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query))
            {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId())
        {
            case R.id.nav_item_chat:
                showContactsActivity();
                return true;

//            case R.id.nav_item_feedback:
//                showAboutFragment();
//                return true;

            case R.id.nav_item_logout:
                showLoginActivity();
                return true;

            case R.id.nav_item_main:
                showProfileActivity();
                return true;

            case R.id.nav_item_price:
                return true;

            case R.id.nav_item_record:
                return true;

            case R.id.nav_item_schedule:
                showScheduleActivity();
                return true;

                case R.id.nav_item_sale:
                showSaleActivity();
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
    public void showLoginActivity()
    {
        Intent intent = LoginActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        presenter.removePassword();
        startActivity(intent);
    }

    @Override
    public void showSaleActivity()
    {
        Intent intent = SaleActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showDoctorsActivity()
    {
        Intent intent = DoctorsActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showAboutFragment()
    {
        AboutFragment.newInstance().show(getSupportFragmentManager());
    }

    @Override
    public void showRateFragment()
    {
        RateFragment.newInstance().show(getSupportFragmentManager());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
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

//    @Override
//    public void onBackPressed()
//    {
//        DrawerLayout drawer = findViewById(R.id.drawer_profile);
//        if (drawer.isDrawerOpen(GravityCompat.START))
//        {
//            drawer.closeDrawer(GravityCompat.START);
//        } else
//        {
//            super.onBackPressed();
//        }
//    }

    private void setupNavMenu()
    {
        View headerLayout = navView.getHeaderView(0);
        headerLogo = headerLayout.findViewById(R.id.header_logo);
        headerTitle = headerLayout.findViewById(R.id.header_tv_title);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onRefresh()
    {
        presenter.getData();
    }

    @Override
    public void showSettingsActivity()
    {
        Intent intent = SettingsActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showScheduleActivity()
    {
        Intent intent = ScheduleActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showProfileActivity()
    {
        Intent intent = ProfileActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showContactsActivity()
    {
        Intent intent = ContactsActivity.getStartIntent(this);
        startActivity(intent);
    }
}

