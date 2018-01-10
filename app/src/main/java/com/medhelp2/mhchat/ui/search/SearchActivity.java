package com.medhelp2.mhchat.ui.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.analise.AnaliseActivity;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.doctor.DoctorsActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;
import com.medhelp2.mhchat.ui.rate_app.RateFragment;
import com.medhelp2.mhchat.ui.sale.SaleActivity;
import com.medhelp2.mhchat.ui.schedule.ScheduleActivity;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.rx.RxEvents;
import com.medhelp2.mhchat.utils.view.ItemListDecorator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends BaseActivity implements SearchViewHelper, Spinner.OnItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener
{
    @Inject
    SearchPresenter<SearchViewHelper> presenter;

    @Inject
    CompositeDisposable disposables;

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

    @BindView(R.id.tv_no_con_center)
    TextView tvNoCon;

    @BindView(R.id.err_tv_message)
    TextView errMessage;

    @BindView(R.id.err_load_btn)
    TextView errLoadBtn;

    private TextView headerTitle;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getActivityComponent().inject(this);
        setUp();
    }

    @Override
    public void updateHeader(CenterResponse response)
    {
        View headerLayout = navView.getHeaderView(0);
        headerLogo = headerLayout.findViewById(R.id.header_logo);
        headerTitle = headerLayout.findViewById(R.id.header_tv_title);
        headerTitle.setText(response.getTitle());

        Picasso.with(this)
                .load(Uri.parse(response.getLogo() + "&token=" + AppConstants.API_KEY))
                .placeholder(R.drawable.holder_center)
                .error(R.drawable.holder_center)
                .into(headerLogo);
    }

    @Override
    protected void setUp()
    {
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        spinner.setVisibility(View.GONE);
        presenter.getCenterInfo();

        if (!presenter.isNetworkMode())
        {
            tvNoCon.setVisibility(View.VISIBLE);
        }

        setupToolbar();
        setupDrawer();
        serviceCash = new ArrayList<>();
        presenter.getData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupRxBus()
    {
        disposables.add(((MainApp) getApplication())
                .bus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object ->
                {
                    if (object instanceof RxEvents.hasConnection)
                    {
                        tvNoCon.setVisibility(View.GONE);
                    } else if (object instanceof RxEvents.noConnection)
                    {
                        tvNoCon.setVisibility(View.VISIBLE);
                    }
                }));
    }

    @SuppressWarnings("unused")
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
    public void onBackPressed()
    {
        presenter.unSubscribe();
        super.onBackPressed();
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
    public void showErrorScreen()
    {
        recyclerView.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        errMessage.setVisibility(View.VISIBLE);
        errLoadBtn.setVisibility(View.VISIBLE);
        errLoadBtn.setOnClickListener(v -> presenter.getData());
    }

    @Override
    public void updateView(List<CategoryResponse> categories, List<ServiceResponse> services)
    {
        spinner.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        errMessage.setVisibility(View.GONE);
        errLoadBtn.setVisibility(View.GONE);

        adapter = new SearchAdapter(serviceCash);

        recyclerView.addItemDecoration(new ItemListDecorator(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        filterList = new ArrayList<>();
        filterList.add(0, new CategoryResponse("Все специальности"));
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
    protected void onPause()
    {
        super.onPause();
        disposables.dispose();
    }

    @Override
    protected void onDestroy()
    {
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
        if (drawer != null)
        {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public void unlockDrawer()
    {
        if (drawer != null)
        {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void closeNavigationDrawer()
    {
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
//            case R.id.nav_item_chat:
//                showContactsActivity();
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

            case R.id.nav_item_result:
                showAnaliseActivity();
                return true;

            case R.id.nav_item_sale:
                showSaleActivity();
                return true;

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
        finish();
    }

    @Override
    public void showSaleActivity()
    {
        Intent intent = SaleActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showDoctorsActivity()
    {
        Intent intent = DoctorsActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showAnaliseActivity()
    {
        Intent intent = AnaliseActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showRateFragment()
    {
        RateFragment.newInstance().show(getSupportFragmentManager());
    }

    @Override
    protected void onResume()
    {
        setupRxBus();
        super.onResume();
        if (drawer != null)
        {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private void setupDrawer()
    {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
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
    public void showScheduleActivity(ServiceResponse response)
    {
        Intent intent = ScheduleActivity.getStartIntent(this);
        intent.putExtra(ScheduleActivity.EXTRA_DATA_ID_SERVICE, response);
        startActivity(intent);
    }

    @Override
    public void showProfileActivity()
    {
        Intent intent = ProfileActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showContactsActivity()
    {
        Intent intent = ContactsActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }
}

