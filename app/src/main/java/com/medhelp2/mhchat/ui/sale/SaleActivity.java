package com.medhelp2.mhchat.ui.sale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.data.model.SaleResponse;
import com.medhelp2.mhchat.ui.analise.AnaliseActivity;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.doctor.DoctorsActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;
import com.medhelp2.mhchat.ui.rate_app.RateFragment;
import com.medhelp2.mhchat.ui.search.SearchActivity;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.rx.RxEvents;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SaleActivity extends BaseActivity implements SaleViewHelper,
        NavigationView.OnNavigationItemSelectedListener
{
    @Inject
    SaleAdapter adapter;

    @Inject
    CompositeDisposable disposables;

    @Inject
    LinearLayoutManager layoutManager;

    @BindView(R.id.rv_sale)
    RecyclerView recyclerView;

    @Inject
    SalePresenterHelper<SaleViewHelper> presenter;

    @BindView(R.id.toolbar_sale)
    Toolbar toolbar;

    @BindView(R.id.tv_no_con_bottom)
    TextView tvNoCon;

    @BindView(R.id.err_tv_message)
    TextView errMessage;

    @BindView(R.id.err_load_btn)
    TextView errLoadBtn;

    @BindView(R.id.collapsing_toolbar_sale)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.drawer_sale)
    DrawerLayout drawer;

    @BindView(R.id.fab_sale)
    FloatingActionButton fab;

    @BindView(R.id.nav_view_sale)
    NavigationView navView;

    private TextView headerTitle;
    private ImageView headerLogo;
    private String phoneNumber;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, SaleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        setUp();
    }

    @Override
    public void updateHeader(CenterResponse response)
    {
        View headerLayout = navView.getHeaderView(0);
        headerLogo = headerLayout.findViewById(R.id.header_logo);
        headerTitle = headerLayout.findViewById(R.id.header_tv_title);

        if (response.getPhone() != null && response.getPhone().length() > 0)
        {
            phoneNumber = response.getPhone();
            fab.setVisibility(View.VISIBLE);
        } else
        {
            fab.setVisibility(View.GONE);
        }
        headerTitle.setText(response.getTitle());

        Picasso.with(this)
                .load(Uri.parse(response.getLogo() + "&token=" + AppConstants.API_KEY))
                .placeholder(R.drawable.holder_center)
                .error(R.drawable.holder_center)
                .into(headerLogo);
    }

    @SuppressWarnings("unused")
    private void setupToolbar()
    {
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

    @Override
    public void showErrorScreen()
    {
        errMessage.setVisibility(View.VISIBLE);
        errLoadBtn.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errLoadBtn.setOnClickListener(v ->
                presenter.updateSaleList());
    }

    @Override
    protected void onResume()
    {
        setupRxBus();
        super.onResume();
        if (drawer != null)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void setUp()
    {
        presenter.getCenterInfo();
        presenter.updateSaleList();

        if (!presenter.isNetworkMode())
        {
            tvNoCon.setVisibility(View.VISIBLE);
        }

        setupToolbar();
        setupDrawer();
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
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
        finish();
    }

    @Override
    public void showSearchActivity()
    {
        Intent intent = SearchActivity.getStartIntent(this);
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
    public void showLoginActivity()
    {
        Intent intent = LoginActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        presenter.removePassword();
        startActivity(intent);
        finish();
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
                showSearchActivity();
                return true;

            case R.id.nav_item_sale:
                return true;

            case R.id.nav_item_result:
                showAnaliseActivity();
                return true;

            case R.id.nav_item_record:
                showSearchActivity();
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
    public void showContactsActivity()
    {
        Intent intent = ContactsActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void onPause()
    {
        disposables.dispose();
        super.onPause();
    }

    @Override
    public void updateSaleData(List<SaleResponse> response)
    {
        recyclerView.setVisibility(View.VISIBLE);
        errMessage.setVisibility(View.GONE);
        errLoadBtn.setVisibility(View.GONE);
        adapter.addItems(response);
    }

    @OnClick(R.id.fab_sale)
    public void fabClick()
    {
        callToCenter(phoneNumber);
    }

    private void callToCenter(String phone)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }
}
