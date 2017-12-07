package com.medhelp2.mhchat.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.CenterResponse;
import com.medhelp2.mhchat.ui.about.AboutFragment;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.doctor.DoctorsActivity;
import com.medhelp2.mhchat.ui.login.LoginActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;
import com.medhelp2.mhchat.ui.rating.RateFragment;
import com.medhelp2.mhchat.ui.schedule.decorators.DayDecorator;
import com.medhelp2.mhchat.ui.schedule.decorators.SelectDecorator;
import com.medhelp2.mhchat.ui.search.SearchActivity;
import com.medhelp2.mhchat.ui.settings.SettingsActivity;
import com.medhelp2.mhchat.utils.view.GridDecorator;
import com.medhelp2.mhchat.utils.view.RecyclerViewClickListener;
import com.medhelp2.mhchat.utils.view.RecyclerViewTouchListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ScheduleActivity extends BaseActivity implements ScheduleViewHelper,
        NavigationView.OnNavigationItemSelectedListener, OnDateSelectedListener
{
    @Inject
    SchedulePresenter<ScheduleViewHelper> presenter;

    @BindView(R.id.rv_schedule)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_schedule)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar_schedule)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.drawer_schedule)
    DrawerLayout drawer;

    @BindView(R.id.nav_view_schedule)
    NavigationView navView;

    @BindView(R.id.calendar_schedule)
    MaterialCalendarView calendarView;

    private ActionBarDrawerToggle drawerToggle;

    private TextView headerTitle;
    private TextView headerDesc;
    private ImageView headerLogo;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, ScheduleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        getActivityComponent().inject(this);
        setUp();
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

    public void setRecyclerView()
    {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridDecorator(5, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this, recyclerView, new RecyclerViewClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
//                Toast.makeText(ScheduleActivity.this, "Item Click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position)
            {
//                Toast.makeText(ScheduleActivity.this, "Item Click LONG", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    protected void setUp()
    {
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        presenter.getCenterInfo();
        setCalendar();
        setupToolbar();
        setupDrawer();
        presenter.getData();
        setRecyclerView();
    }

    private void setCalendar()
    {
        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_YEAR, 0);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, 30);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(min)
                .setMaximumDate(max)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getStringArray(R.array.calendar_months_array)));
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getStringArray(R.array.calendar_days_array)));
        calendarView.setOnDateChangedListener(this);
        calendarView.addDecorators(new SelectDecorator(this));

        presenter.updateCalendar();
    }

    @Override
    public void updateDateInfo(List<String> response)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);

        ArrayList<CalendarDay> calendarDays = new ArrayList<>();

        for (int i = 0; i < 30; i++)
        {
            CalendarDay day = CalendarDay.from(calendar);
            calendarDays.add(day);

            if (i % 3 == 0)
            {
                calendarView.addDecorator(new DayDecorator(this, day, DayDecorator.DAY_MODE_MANY));
                calendar.add(Calendar.DATE, 1);
            } else if (i % 2 == 0)
            {
                calendarView.addDecorator(new DayDecorator(this, day, DayDecorator.DAY_MODE_FEW));
                calendar.add(Calendar.DATE, 1);
            } else
            {
                calendarView.addDecorator(new DayDecorator(this, day, DayDecorator.DAY_MODE_NOT));
                calendar.add(Calendar.DATE, 1);
            }
        }

        recyclerView.setAdapter(new ItemAdapter(this, response));
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected)
    {
//        Toast.makeText(this, "Выбрана дата: " + date, Toast.LENGTH_SHORT).show();

    }

    private int dpToPx(int dp)
    {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
    protected void onDestroy()
    {
        Timber.d("onDestroy");
        presenter.onDetach();
        super.onDestroy();
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
    public void showSearchActivity()
    {
        Intent intent = SearchActivity.getStartIntent(this);
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
    public void showRateFragment()
    {
        RateFragment.newInstance().show(getSupportFragmentManager());
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

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_schedule);
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
    public void showSettingsActivity()
    {
        Intent intent = SettingsActivity.getStartIntent(this);
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

