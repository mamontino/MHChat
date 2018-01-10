package com.medhelp2.mhchat.ui.doctor;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.Doctor;
import com.medhelp2.mhchat.data.model.DoctorInfo;
import com.medhelp2.mhchat.ui.analise.AnaliseActivity;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.doctor.details.DocDetailsFragment;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;
import com.medhelp2.mhchat.ui.rate_app.RateFragment;
import com.medhelp2.mhchat.ui.sale.SaleActivity;
import com.medhelp2.mhchat.ui.schedule.ScheduleActivity;
import com.medhelp2.mhchat.ui.search.SearchActivity;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.rx.RxEvents;
import com.medhelp2.mhchat.utils.view.ItemListDecorator;
import com.medhelp2.mhchat.utils.view.RecyclerViewClickListener;
import com.medhelp2.mhchat.utils.view.RecyclerViewTouchListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DoctorsActivity extends BaseActivity implements DoctorsViewHelper,
        NavigationView.OnNavigationItemSelectedListener, Spinner.OnItemSelectedListener
{
    @Inject
    DoctorsPresenterHelper<DoctorsViewHelper> presenter;

    @Inject
    CompositeDisposable disposables;

    @BindView(R.id.toolbar_doctors)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar_doctors)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.drawer_doctors)
    DrawerLayout drawer;

    @BindView(R.id.spinner_doctors)
    Spinner spinner;

    @BindView(R.id.tv_no_con_center)
    TextView tvNoCon;

    @BindView(R.id.err_tv_message)
    TextView errMessage;

    @BindView(R.id.err_load_btn)
    Button errLoadBtn;

    @BindView(R.id.nav_view_doctors)
    NavigationView navView;

    @Inject
    DoctorsAdapter adapter;

    @Inject
    LinearLayoutManager layoutManager;

    @BindView(R.id.rv_doctors)
    RecyclerView recyclerView;

    private List<DoctorInfo> cashList;

    private TextView headerTitle;
    private ImageView headerLogo;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, DoctorsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        setUp();
    }

    @Override
    protected void setUp()
    {
        if (!presenter.isNetworkMode())
        {
            tvNoCon.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
        }

        setupToolbar();
        setupDrawer();

        cashList = new ArrayList<>();
        presenter.getCenterInfo();
        presenter.getSpecialtyByCenter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
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
                        spinner.setVisibility(View.VISIBLE);
                    } else if (object instanceof RxEvents.noConnection)
                    {
                        tvNoCon.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                    }
                }));
    }


    @Override
    public void updateView(List<DoctorInfo> response)
    {
        errMessage.setVisibility(View.GONE);
        errLoadBtn.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        cashList.addAll(response);
        adapter = new DoctorsAdapter(response);
        recyclerView.addItemDecoration(new ItemListDecorator(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this, recyclerView, new RecyclerViewClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                int idDoctor = cashList.get(position).getIdDoctor();
                showDocDetailsFragment(idDoctor);
            }

            @Override
            public void onLongClick(View view, int position)
            {

            }
        }));
    }

    @Override
    public void updateSpecialty(List<CategoryResponse> response)
    {
        DocSpinnerAdapter spinnerAdapter = new DocSpinnerAdapter(this, response);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                presenter.getDoctorList(response.get(position).getIdSpec());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
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
                final List<DoctorInfo> filteredModelList = filterDoctor(cashList, newText);
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

    private List<DoctorInfo> filterDoctor(List<DoctorInfo> models, String query)
    {
        query = query.toLowerCase();
        final List<DoctorInfo> filteredModelList = new ArrayList<>();
        for (DoctorInfo model : models)
        {
            if (model.getFullName() != null)
            {
                final String text = model.getFullName().toLowerCase();
                if (text.contains(query))
                {
                    filteredModelList.add(model);
                }
            }
        }
        return filteredModelList;
    }

    @SuppressWarnings("unused")
    private void setupToolbar()
    {
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
    public void lockDrawer()
    {
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer()
    {
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
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
    protected void onResume()
    {
        setupRxBus();
        super.onResume();
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
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

    @Override
    public void onBackPressed()
    {
            if (drawer.isDrawerOpen(GravityCompat.START))
            {
                drawer.closeDrawer(GravityCompat.START);
            } else
            {
                presenter.unSubscribe();
                super.onBackPressed();
            }
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
    public void showErrorScreen()
    {
        recyclerView.setVisibility(View.GONE);
        errMessage.setVisibility(View.VISIBLE);
        errLoadBtn.setVisibility(View.VISIBLE);
        errLoadBtn.setVisibility(View.VISIBLE);
        errLoadBtn.setOnClickListener(v ->
                presenter.getSpecialtyByCenter());
    }

    @Override
    public void showRateFragment()
    {
        RateFragment.newInstance().show(getSupportFragmentManager());
    }

    @Override
    public void showSearchActivity()
    {
        Intent intent = SearchActivity.getStartIntent(this);
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
    public void showLoginActivity()
    {
        Intent intent = LoginActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        presenter.removePassword();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId())
        {
            case R.id.nav_item_chat:
                showContactsActivity();
                return true;

            case R.id.nav_item_sale:
                showSaleActivity();
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

            case R.id.nav_item_result:
                showAnaliseActivity();
                return true;

            case R.id.nav_item_rate:
                showRateFragment();
                return true;

            case R.id.nav_item_staff:
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onPause()
    {
        disposables.dispose();
        super.onPause();
    }

    @Override
    public void showScheduleActivity(Doctor doctor)
    {
        Intent intent = ScheduleActivity.getStartIntent(this);
        intent.putExtra(ScheduleActivity.EXTRA_DATA_ID_DOCTOR, doctor);
        startActivity(intent);
    }

    @Override
    public void showSaleActivity()
    {
        Intent intent = SaleActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showDocDetailsFragment(int idDoctor)
    {
        DocDetailsFragment.newInstance(idDoctor).show(getSupportFragmentManager());
    }

    @Override
    public void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }
}
