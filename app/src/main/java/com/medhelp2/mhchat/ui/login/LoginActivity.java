package com.medhelp2.mhchat.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;
import com.medhelp2.mhchat.utils.rx.RxEvents;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements LoginViewHelper
{
    @Inject
    LoginPresenterHelper<LoginViewHelper> presenter;

    @Inject
    CompositeDisposable disposables;

    private static final String LOGIN_KEY = "LOGIN_KEY";
    private static final String PASSWORD_KEY = "PASSWORD_KEY";

    @BindView(R.id.et_login_username)
    EditText etUsername;

    @BindView(R.id.et_login_password)
    EditText etPassword;

    @BindView(R.id.chb_login_save)
    CheckBox chbSave;

    private String username;
    private String password;

    public static Intent getStartIntent(Context context)
    {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        if (!etUsername.getText().toString().trim().equals("") ||
                !etPassword.getText().toString().trim().equals(""))
        {
            outState.putString(LOGIN_KEY, username);
            outState.putString(PASSWORD_KEY, password);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        presenter.onAttach(LoginActivity.this);
        presenter.getUsername();

        if (savedInstanceState != null)
        {
            username = savedInstanceState.getString(LOGIN_KEY);
            etUsername.setText(username);
            password = savedInstanceState.getString(PASSWORD_KEY);
            etPassword.setText(password);
        }

        setUp();
    }

    @Override
    public void updateUsernameHint(String username)
    {
        if (username != null && username.length() > 0)
        {
            etUsername.setText(username);
            etUsername.setSelection(etUsername.getText().length());
        }
    }

    private void cleanUserData()
    {
        etUsername.setText("");
        etPassword.setText("");
    }

    @Override
    public boolean isNeedSave()
    {
        return chbSave.isChecked();
    }

    @Override
    protected void setUp()
    {
        setupRxBus();

        if (!presenter.isNetworkMode())
        {
            showNoConnectionDialog();
        }

        etPassword.setOnEditorActionListener((v, actionId, event) ->
        {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_NAVIGATE_NEXT))
                    || (actionId == EditorInfo.IME_ACTION_DONE))
            {
                userLogin();
            }
            return false;
        });
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
                        hideNoConnectionDialog();
                    } else if (object instanceof RxEvents.noConnection)
                    {
                        showNoConnectionDialog();
                    }
                }));
    }

    private void showNoConnectionDialog()
    {
    }

    private void hideNoConnectionDialog()
    {
    }

    @Override
    public void openProfileActivity()
    {
        Intent intent = ProfileActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void closeActivity()
    {
        finish();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_login_enter)
    void onLoginClick(View view)
    {
        userLogin();
    }

    private void userLogin()
    {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        presenter.onLoginClick(username, password);
        cleanUserData();
    }

    @Override
    protected void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }
}
