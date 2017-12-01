package com.medhelp2.mhchat.ui.login.login_fr;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.di.component.ActivityComponent;
import com.medhelp2.mhchat.ui.base.BaseFragment;
import com.medhelp2.mhchat.ui.chat.ChatActivity;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.profile.ProfileActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class FormFragment extends BaseFragment implements FormViewHelper
{
    public static final String TAG = "FormFragment";

    @Inject
    FormPresenterHelper<FormViewHelper> presenter;

    @BindView(R.id.et_login_username)
    EditText etUsername;

    @BindView(R.id.et_login_password)
    EditText etPassword;

    @BindView(R.id.chb_login_save)
    CheckBox chbSave;

    @BindView(R.id.btn_login_enter)
    Button btnLogin;

    public static FormFragment newInstance()
    {
        Timber.d("newInstance");
        Bundle args = new Bundle();
        FormFragment fragment = new FormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Timber.d("onCreateView");
        ActivityComponent component = getActivityComponent();
        if (component != null)
        {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_login_enter)
    void onLoginClick(View view)
    {
        Timber.d("onLoginClick");
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        Timber.d("onLoginClick: " + "username: " + username + " password: " + password);
        presenter.onLoginClick(username, password);
        cleanUserData();
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
    public void closeActivity()
    {
        Timber.d("closeActivity");
        getActivity().finish();
    }

    @Override
    protected void setUp(View view)
    {
        Timber.d("setUp");
    }

    @Override
    public void openContactsActivity()
    {
        Timber.d("openContactsActivity");
        Intent intent = ContactsActivity.getStartIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void onDestroyView()
    {
        Timber.d("onDestroyView");
        presenter.onDetach();
        closeActivity();
        super.onDestroyView();
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
        Intent intent = ProfileActivity.getStartIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void openChatActivity()
    {
        Timber.d("openChatActivity");
        Intent intent = ChatActivity.getStartIntent(getContext());
        startActivity(intent);
    }
}
