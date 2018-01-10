package com.medhelp2.mhchat.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.bg.SyncService;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.VisitResponse;
import com.medhelp2.mhchat.ui.analise.AnaliseActivity;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.doctor.DoctorsActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.rate_app.RateFragment;
import com.medhelp2.mhchat.ui.sale.SaleActivity;
import com.medhelp2.mhchat.ui.search.SearchActivity;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.main.TimesUtils;
import com.medhelp2.mhchat.utils.rx.RxEvents;
import com.medhelp2.mhchat.utils.view.RecyclerViewClickListener;
import com.medhelp2.mhchat.utils.view.RecyclerViewTouchListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivity extends BaseActivity implements ProfileViewHelper,
        NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener
{
    @Inject
    ProfilePresenterHelper<ProfileViewHelper> presenter;

    @Inject
    CompositeDisposable disposables;

    @BindView(R.id.toolbar_profile)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar_profile)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.logo_center_profile)
    ImageView logoProfile;

    @BindView(R.id.tv_center_name_profile)
    TextView centerName;

    @BindView(R.id.tv_no_con_bottom)
    TextView tvNoCon;

    @BindView(R.id.tv_phone_profile)
    TextView centerPhone;

    @BindView(R.id.tv_phone_hint_profile)
    TextView centerPhoneHint;

    @BindView(R.id.err_tv_message)
    TextView errMessage;

    @BindView(R.id.err_load_btn)
    Button errLoadBtn;

    @BindView(R.id.rv_profile)
    RecyclerView recyclerView;

    @BindView(R.id.fab_profile)
    FloatingActionButton fab;

    @BindView(R.id.drawer_profile)
    DrawerLayout drawer;

    @BindView(R.id.nav_view_profile)
    NavigationView navView;

    @BindView(R.id.swipe_profile)
    SwipeRefreshLayout swipeProfile;

    private TextView headerTitle;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);

        setUp();
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
    public void showRateFragment()
    {
        RateFragment.newInstance().show(getSupportFragmentManager());
    }

    @Override
    public void showContactsActivity()
    {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showSearchActivity()
    {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateHeader(CenterResponse response)
    {
        String centerUrl = response.getLogo();

        View headerLayout = navView.getHeaderView(0);
        ImageView headerLogo = headerLayout.findViewById(R.id.header_logo);
        headerTitle = headerLayout.findViewById(R.id.header_tv_title);

        centerName.setText(response.getTitle());

        if (response.getPhone() != null)
        {
            centerPhoneHint.setVisibility(View.VISIBLE);
            centerPhone.setText(response.getPhone());
            centerPhone.setOnClickListener(v -> callToCenter(response.getPhone()));
        }

        headerTitle.setText(response.getTitle());

        Picasso.with(this)
                .load(Uri.parse(centerUrl + "&token=" + AppConstants.API_KEY))
                .placeholder(R.drawable.holder_center)
                .error(R.drawable.holder_center)
                .into(logoProfile);

        Picasso.with(this)
                .load(Uri.parse(centerUrl + "&token=" + AppConstants.API_KEY))
                .placeholder(R.drawable.holder_center)
                .error(R.drawable.holder_center)
                .into(headerLogo);
    }

    @Override
    public void updateData(List<VisitResponse> response, String today)
    {
        ArrayList<ProfileParentModel> parentModels = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (response.size() > 0 && today != null)
        {
            recyclerView.setVisibility(View.VISIBLE);
            errMessage.setVisibility(View.GONE);
            errLoadBtn.setVisibility(View.GONE);

            List<VisitResponse> actualReceptions = new ArrayList<>();
            List<VisitResponse> latestReceptions = new ArrayList<>();

            for (VisitResponse visit : response)
            {
                if (TimesUtils.getMillisFromVisit(visit.getAdmDate()) > TimesUtils.getMillisFromServer(today))
                {
                    actualReceptions.add(visit);
                } else
                {
                    latestReceptions.add(visit);
                }
            }

            if (actualReceptions.size() > 0)
            {
                parentModels.add(new ProfileParentModel("Предстоящие", actualReceptions));
            }

            if (latestReceptions.size() > 0)
            {
                parentModels.add(new ProfileParentModel("Прошедшие", latestReceptions));
            }

            ProfileAdapter adapter = new ProfileAdapter(this, parentModels);
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter.onGroupClick(0);
            recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this,
                    recyclerView, new RecyclerViewClickListener()
            {
                @Override
                public void onClick(View view, int position)
                {

                }

                @Override
                public void onLongClick(View view, int position)
                {

                }
            }));
        } else
        {
            showErrorScreen();
        }
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
                        fab.setVisibility(View.VISIBLE);
                    } else if (object instanceof RxEvents.noConnection)
                    {
                        tvNoCon.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.GONE);
                    }
                }));
    }

    @Override
    protected void onPause()
    {
        disposables.dispose();
        super.onPause();
    }

    @Override
    public void showErrorScreen()
    {
        recyclerView.setVisibility(View.GONE);
        errMessage.setVisibility(View.VISIBLE);
        errLoadBtn.setVisibility(View.VISIBLE);
        errLoadBtn.setOnClickListener(v -> presenter.getVisits());
    }

    private void callToCenter(String phone)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @Override
    public void runSendRegistrationService(String token, int userId)
    {
        Intent intent = SyncService.getStartIntent(this);
        intent.putExtra(SyncService.USER_REGISTRATION_ID, userId);
        startService(intent);
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
            drawer.closeDrawer(Gravity.START);
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

            case R.id.nav_item_logout:
                showLoginActivity();
                return true;

            case R.id.nav_item_main:
                return true;

            case R.id.nav_item_price:
                showSearchActivity();
                return true;

            case R.id.nav_item_result:
                showAnaliseActivity();
                return true;

            case R.id.nav_item_record:
                showSearchActivity();
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
    public void showSaleActivity()
    {
        Intent intent = SaleActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    private void showLoginActivity()
    {
        Intent intent = LoginActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        presenter.removePassword();
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
    public void showDoctorsActivity()
    {
        Intent intent = DoctorsActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume()
    {
        setupRxBus();
        super.onResume();

        if (!presenter.isNetworkMode())
        {
            tvNoCon.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        }

        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void setUp()
    {
        setupToolbar();
        setupDrawer();
        setupRefresh();

        presenter.updateToken();
        presenter.getVisits();
        presenter.updateHeaderInfo();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fab_profile)
    public void fabClick()
    {
        if (isNetworkConnected())
        {
            showSearchActivity();
        } else
        {
            showError(R.string.connection_error);
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
        headerTitle = headerLayout.findViewById(R.id.header_tv_title);
        navView.setNavigationItemSelectedListener(this);
    }

    private void setupRefresh()
    {
        swipeProfile.setOnRefreshListener(this);
        swipeProfile.setColorSchemeColors(getResources().getColor(android.R.color.holo_red_light));
    }

    @Override
    public void swipeDismiss()
    {
        swipeProfile.setRefreshing(false);
    }

    @Override
    public void onRefresh()
    {
        if (isNetworkConnected())
        {
            presenter.getVisits();
        } else
        {
            showError(R.string.connection_error);
            swipeDismiss();
        }
    }
}
