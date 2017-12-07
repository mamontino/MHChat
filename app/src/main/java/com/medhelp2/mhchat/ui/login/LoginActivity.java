package com.medhelp2.mhchat.ui.login;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.ui.base.BaseActivity;
import com.medhelp2.mhchat.ui.chat.ChatActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends BaseActivity implements LoginViewHelper
{
    @Inject
    LoginPresenterHelper<LoginViewHelper> presenter;

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

        if (savedInstanceState == null)
        {
            username = "";
            password = "";
        } else
        {
            username = savedInstanceState.getString(LOGIN_KEY);
            etUsername.setText(username);
            password = savedInstanceState.getString(PASSWORD_KEY);
            etPassword.setText(password);
        }
        setUp();

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

    private void cleanUserData()
    {
        Timber.d("cleanUserData");
        etUsername.setText("");
        etPassword.setText("");
    }

    @Override
    public boolean isNeedSave()
    {
        Timber.d("isNeedSave: " + chbSave.isChecked());
        return chbSave.isChecked();
    }

    @Override
    protected void setUp()
    {

    }

    @Override
    public void openNetworkSettings()
    {
        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        ComponentName cName = new ComponentName("com.android.phone", "com.android.phone.Settings");
        intent.setComponent(cName);
    }

    @Override
    public void openProfileActivity()
    {
        Timber.d("openProfileActivity");
        Intent intent = ProfileActivity.getStartIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void openChatActivity()
    {
        Timber.d("openChatActivity");
        Intent intent = ChatActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void openContactsActivity()
    {
        Timber.d("openContactsActivity");
        Intent intent = ContactsActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void closeActivity()
    {
        Timber.d("closeActivity");
        finish();
    }

    @OnClick(R.id.btn_login_enter)
    void onLoginClick(View view)
    {
        Timber.d("onLoginClick");
        userLogin();
    }

    private void userLogin()
    {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        Timber.d("onLoginClick: " + "username: " + username + " password: " + password);
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
