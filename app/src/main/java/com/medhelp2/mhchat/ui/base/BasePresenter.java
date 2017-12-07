package com.medhelp2.mhchat.ui.base;

import android.util.Log;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V>
{
    private static final String TAG = "BasePresenter";

    private final DataHelper dataHelper;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;

    private V mvpView;

    @Inject
    public BasePresenter(DataHelper dataHelper,
            SchedulerProvider schedulerProvider,
            CompositeDisposable compositeDisposable)
    {
        this.dataHelper = dataHelper;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView)
    {
        this.mvpView = mvpView;
    }

    @Override
    public void onDetach()
    {
        compositeDisposable.dispose();
        mvpView = null;
    }

    public boolean isViewAttached()
    {
        return mvpView != null;
    }

    public V getMvpView()
    {
        return mvpView;
    }

    public void checkViewAttached()
    {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataHelper getDataHelper()
    {
        return dataHelper;
    }

    public SchedulerProvider getSchedulerProvider()
    {
        return schedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable()
    {
        return compositeDisposable;
    }

    @Override
    public void handleApiError(ANError error)
    {
        if (error == null || error.getErrorBody() == null)
        {
            getMvpView().showError(R.string.api_default_error);
            return;
        }

        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR))
        {
            getMvpView().showError(R.string.connection_error);
            return;
        }

        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR))
        {
            getMvpView().showError(R.string.api_retry_error);
            return;
        }

        final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        try
        {
            Log.d(TAG, "Error download");

        } catch (JsonSyntaxException | NullPointerException e)
        {
            Log.e(TAG, "handleApiError", e);
            getMvpView().showError(R.string.api_default_error);
        }
    }

    @Override
    public void setUserAsLoggedOut()
    {
    }

    public static class MvpViewNotAttachedException extends RuntimeException
    {
        public MvpViewNotAttachedException()
        {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
