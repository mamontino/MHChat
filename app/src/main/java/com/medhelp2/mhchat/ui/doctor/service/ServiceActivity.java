package com.medhelp2.mhchat.ui.doctor.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.model.CategoryResponse;
import com.medhelp2.mhchat.data.model.ServiceResponse;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.utils.rx.RxEvents;
import com.medhelp2.mhchat.utils.view.ItemListDecorator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ServiceActivity extends BaseActivity implements ServiceViewHelper,
        Spinner.OnItemSelectedListener
{
    public static final String EXTRA_DATA_ID_DOCTOR = "EXTRA_DATA_ID_DOCTOR";

    @Inject
    ServicePresenter<ServiceViewHelper> presenter;

    @Inject
    CompositeDisposable disposables;

    @Inject
    LinearLayoutManager layoutManager;

    @BindView(R.id.rv_service)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_con_center)
    TextView tvNoCon;

    @BindView(R.id.err_tv_message)
    TextView errMessage;

    @BindView(R.id.err_load_btn)
    Button errLoadBtn;

    @BindView(R.id.spinner_service)
    Spinner spinner;

    @BindView(R.id.toolbar_service)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar_service)
    CollapsingToolbarLayout toolbarLayout;

    private ServiceAdapter adapter;
    private List<CategoryResponse> filterList;
    private List<ServiceResponse> serviceCash;

    private int idDoctor;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, ServiceActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        setUp();
    }

    @Override
    protected void setUp()
    {
        spinner.setVisibility(View.GONE);

        if (!presenter.isNetworkMode())
        {
            tvNoCon.setVisibility(View.VISIBLE);
        }

        setupToolbar();
        serviceCash = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            idDoctor = bundle.getInt(EXTRA_DATA_ID_DOCTOR);
            presenter.getData(idDoctor);
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
    protected void onResume()
    {
        setupRxBus();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        disposables.dispose();
    }

    @Override
    @SuppressWarnings("all")
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
                if (filteredModelList != null && filteredModelList.size() > 0)
                {
                    adapter.setFilter(filteredModelList);

                }
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
        errMessage.setVisibility(View.GONE);
        errLoadBtn.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        adapter = new ServiceAdapter(serviceCash, idDoctor);
        recyclerView.addItemDecoration(new ItemListDecorator(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        filterList = new ArrayList<>();
        filterList.add(0, new CategoryResponse("Все"));
        filterList.addAll(categories);

        ServiceSpinnerAdapter spinnerAdapter = new ServiceSpinnerAdapter(this, filterList);
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
    public void showErrorScreen()
    {
        recyclerView.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        errMessage.setVisibility(View.VISIBLE);
        errLoadBtn.setVisibility(View.VISIBLE);
        errLoadBtn.setOnClickListener(v -> presenter.getData(idDoctor));
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
}

