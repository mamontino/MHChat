package com.medhelp2.mhchat.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.DateResponse;
import com.medhelp2.mhchat.data.model.ScheduleResponse;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.schedule.decorators.DayDecorator;
import com.medhelp2.mhchat.ui.schedule.decorators.SelectDecorator;
import com.medhelp2.mhchat.utils.main.TimesUtils;
import com.medhelp2.mhchat.utils.rx.RxEvents;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ScheduleActivity extends BaseActivity implements ScheduleViewHelper,
        OnDateSelectedListener, OnMonthChangedListener
{
    public static final String EXTRA_DATA_ID_DOCTOR = "EXTRA_DATA_ID_DOCTOR";
    public static final String EXTRA_DATA_ID_SERVICE = "EXTRA_DATA_ID_SERVICE";
    public static final String EXTRA_DATA_ADM = "EXTRA_DATA_ADM";

    @Inject
    SchedulePresenter<ScheduleViewHelper> presenter;

    @Inject
    CompositeDisposable disposables;

    @BindView(R.id.rv_schedule)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_schedule)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar_schedule)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.calendar_schedule)
    MaterialCalendarView calendarView;

    @BindView(R.id.err_schedule)
    TextView errorSchedule;

    @BindView(R.id.empty_schedule)
    TextView emptySchedule;

    @BindView(R.id.tv_no_con_center)
    TextView tvNoCon;

    @BindView(R.id.err_tv_message)
    TextView errMessage;

    @BindView(R.id.err_load_btn)
    TextView errLoadBtn;

    @BindView(R.id.hol_schedule)
    TextView holSchedule;

    @BindView(R.id.holder_schedule_day)
    TextView holScheduleDay;

    @BindView(R.id.holder_schedule_time)
    TextView holScheduleTime;

    @BindView(R.id.schedule_description)
    LinearLayout scheduleDescription;

    private String todayString;
    private int idDoctor;
    private int idService;
    private int adm;

    private SectionedRecyclerViewAdapter sectionAdapter;
    private List<ScheduleResponse> timeList = new ArrayList<>();
    private List<DateState> listSelected = new ArrayList<>();

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, ScheduleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        listSelected = new ArrayList<>();

        idDoctor = getIntent().getIntExtra(EXTRA_DATA_ID_DOCTOR, 0);
        idService = getIntent().getIntExtra(EXTRA_DATA_ID_SERVICE, 0);
        adm = getIntent().getIntExtra(EXTRA_DATA_ADM, 0);

        getActivityComponent().inject(this);
        setUp();
    }

    @Override
    protected void setUp()
    {
        setUnBinder(ButterKnife.bind(this));

        holScheduleDay.setVisibility(View.GONE);
        holScheduleTime.setVisibility(View.GONE);
        scheduleDescription.setVisibility(View.GONE);

        if (!presenter.isNetworkMode())
        {
            tvNoCon.setVisibility(View.VISIBLE);
        }

        setupToolbar();
        setupCalendarView();

        presenter.onAttach(this);

        if (idDoctor != 0)
        {
            presenter.getDateFromDoctor(idDoctor, idService, adm);
        } else
        {
            presenter.getDateFromService(idService, adm);
        }
    }

    @Override
    protected void onResume()
    {
        setupRxBus();
        super.onResume();
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
    protected void onPause()
    {
        super.onPause();
        disposables.dispose();
    }

    private void setupCalendarView()
    {
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getStringArray(R.array.calendar_months_array)));
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getStringArray(R.array.calendar_days_array)));
        calendarView.setVisibility(View.GONE);
    }

    public void updateRecyclerView(List<DateState> responses)
    {
        sectionAdapter = new SectionedRecyclerViewAdapter();

        for (DateState state : responses)
        {
            sectionAdapter.addSection(new TimeSection(state.getName(), state.getCategory()));
        }

        GridLayoutManager glm = new GridLayoutManager(this, 4);

        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                switch (sectionAdapter.getSectionItemViewType(position))
                {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 4;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(glm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sectionAdapter);
    }

    @Override
    public void setupCalendar(DateResponse today, List<ScheduleResponse> response)
    {
        todayString = today.getToday();
        String lastMondayString = today.getLastMonday();

        CalendarDay min = CalendarDay.from(TimesUtils.getDateSchedule(todayString));

        Calendar max = Calendar.getInstance();
        max.setTime(TimesUtils.getDateSchedule(todayString));
        max.add(Calendar.DAY_OF_MONTH, 28);

        calendarView.state().edit()
                .setMinimumDate(min)
                .setMaximumDate(max)
                .commit();

        calendarView.setVisibility(View.VISIBLE);
        calendarView.addDecorators(new SelectDecorator(this));
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnMonthChangedListener(this);
        updateCalendar(lastMondayString, response);
    }

    @Override
    public void updateCalendar(String day, List<ScheduleResponse> response)
    {
        recyclerView.setVisibility(View.VISIBLE);
        errorSchedule.setVisibility(View.GONE);
        errMessage.setVisibility(View.GONE);
        errLoadBtn.setVisibility(View.GONE);
        emptySchedule.setVisibility(View.VISIBLE);
        holScheduleDay.setVisibility(View.VISIBLE);
        holScheduleTime.setVisibility(View.VISIBLE);
        scheduleDescription.setVisibility(View.VISIBLE);

        timeList.clear();
        timeList.addAll(response);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimesUtils.getDateSchedule(todayString));
        calendar.add(Calendar.MONTH, 0);

        for (ScheduleResponse scheduleResponse : response)
        {
            String currentDay = scheduleResponse.getAdmDay();
            Date date = TimesUtils.getDateSchedule(currentDay);
            CalendarDay calendarDay = CalendarDay.from(date);

            if (scheduleResponse.isWork())
            {
                if (scheduleResponse.getAdmTime() == null)
                {
                    calendarView.addDecorator(new DayDecorator(this, calendarDay, DayDecorator.DAY_MODE_NO));
                    calendar.add(Calendar.DATE, 1);
                } else if (scheduleResponse.getAdmTime().size() > 3)
                {
                    calendarView.addDecorator(new DayDecorator(this, calendarDay, DayDecorator.DAY_MODE_MANY));
                    calendar.add(Calendar.DATE, 1);
                } else
                {
                    calendarView.addDecorator(new DayDecorator(this, calendarDay, DayDecorator.DAY_MODE_FEW));
                    calendar.add(Calendar.DATE, 1);
                }
            } else
            {
                calendarView.addDecorator(new DayDecorator(this, calendarDay, DayDecorator.DAY_MODE_NOT));
                calendar.add(Calendar.DATE, 1);
            }
        }
    }

    @Override
    public void showErrorScreen()
    {
        recyclerView.setVisibility(View.GONE);
        holScheduleDay.setVisibility(View.GONE);
        holScheduleTime.setVisibility(View.GONE);
        scheduleDescription.setVisibility(View.GONE);

        errMessage.setVisibility(View.VISIBLE);
        errLoadBtn.setVisibility(View.VISIBLE);
        errLoadBtn.setVisibility(View.VISIBLE);
        errLoadBtn.setOnClickListener(v ->
        {
            if (idDoctor != 0)
            {
                presenter.getDateFromDoctor(idDoctor, idService, adm);
            } else
            {
                presenter.getDateFromService(idService, adm);
            }
        });
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date)
    {
        calendarView.clearSelection();
        emptySchedule.setVisibility(View.VISIBLE);
        errorSchedule.setVisibility(View.GONE);
        holSchedule.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        Date selectedDate = date.getDate();
        String nextDate = TimesUtils.getDateSchedule(selectedDate);

        if (idDoctor != 0)
        {
            presenter.getScheduleByDoctor(idDoctor, nextDate, adm);
        } else
        {
            presenter.getScheduleByService(idService, nextDate, adm);
        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date,
            boolean selected)
    {
        emptySchedule.setVisibility(View.GONE);
        errorSchedule.setVisibility(View.GONE);
        holSchedule.setVisibility(View.GONE);

        listSelected = new ArrayList<>();

        for (ScheduleResponse dateResponse : timeList)
        {
            Date selectedDate = TimesUtils.getDateSchedule(dateResponse.getAdmDay());
            CalendarDay selectedDay = CalendarDay.from(selectedDate);

            if (selectedDay != null && selectedDay.equals(date))
            {
                if (dateResponse.isWork())
                {
                    if (dateResponse.getAdmTime() != null
                            && dateResponse.getAdmTime().size() > 0)
                    {
                        errorSchedule.setVisibility(View.GONE);
                        emptySchedule.setVisibility(View.GONE);
                        holSchedule.setVisibility(View.GONE);

                        List<String> listTime = new ArrayList<>();
                        listTime.addAll(dateResponse.getAdmTime());
                        DateState dateState = new DateState(dateResponse.getFullName(), listTime);
                        listSelected.add(dateState);
                    } else
                    {
                        errorSchedule.setVisibility(View.VISIBLE);
                        emptySchedule.setVisibility(View.GONE);
                        holSchedule.setVisibility(View.GONE);
                    }
                } else
                {
                    errorSchedule.setVisibility(View.GONE);
                    emptySchedule.setVisibility(View.GONE);
                    holSchedule.setVisibility(View.VISIBLE);
                }
            }

            recyclerView.setVisibility(View.VISIBLE);
            updateRecyclerView(listSelected);
        }
    }

    @SuppressWarnings("unused")
    private void setupToolbar()
    {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) toolbarLayout.getLayoutParams();
        if (actionBar != null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        presenter.unSubscribe();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }

    private class TimeSection extends StatelessSection
    {
        boolean expanded = true;
        String title;
        List<String> list;

        TimeSection(String title, List<String> list)
        {
            super(new SectionParameters.Builder(R.layout.item_date)
                    .headerResourceId(R.layout.item_groupe)
                    .build());

            this.title = title;
            this.list = list;
        }

        @Override
        public int getContentItemsTotal()
        {
            return expanded ? list.size() : 0;
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view)
        {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String name = title;
            String category = list.get(position);

            itemHolder.tvItem.setText(name);
            itemHolder.tvItem.setText(category);
            itemHolder.rootView.setOnClickListener(v ->
            {

            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view)
        {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder)
        {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.tvTitle.setText(title);
            headerHolder.rootView.setOnClickListener(v ->
            {
                expanded = !expanded;
                headerHolder.tvTitle.setCompoundDrawablesWithIntrinsicBounds
                        (0, 0, expanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down, 0);
                sectionAdapter.notifyDataSetChanged();
            });
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        private final View rootView;
        private final TextView tvTitle;

        HeaderViewHolder(View view)
        {
            super(view);
            rootView = view;
            tvTitle = view.findViewById(R.id.tv_profile_item_title);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder
    {
        private final View rootView;
        private final TextView tvItem;

        ItemViewHolder(View view)
        {
            super(view);

            rootView = view;
            tvItem = view.findViewById(R.id.tv_date_item_row);
        }
    }

    private class DateState
    {
        String name;
        List<String> category;

        DateState(String name, List<String> category)
        {
            this.name = name;
            this.category = category;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        List<String> getCategory()
        {
            return category;
        }
    }
}

