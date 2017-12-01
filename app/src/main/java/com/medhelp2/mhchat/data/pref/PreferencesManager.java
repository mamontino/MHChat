package com.medhelp2.mhchat.data.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.medhelp2.mhchat.di.scope.PerApplication;
import com.medhelp2.mhchat.utils.main.AppConstants;

import javax.inject.Inject;


@PerApplication
public class PreferencesManager implements PreferencesHelper
{
    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_PASSWORD = "PREF_KEY_CURRENT_PASSWORD";
    private static final String PREF_KEY_CURRENT_NAME = "PREF_KEY_CURRENT_CLIENT_ID";
    private static final String PREF_KEY_CURRENT_TOKEN = "PREF_KEY_TOKEN";
    private static final String PREF_KEY_CURRENT_FB_TOKEN = "PREF_KEY_CURRENT_FB_TOKEN";
    private static final String PREF_KEY_NOTIFICATIONS = "notifications";
    private static final String PREF_KEY_CURRENT_CENTER_ID = "PREF_KEY_CURRENT_CENTER_ID";
    private static final String PREF_KEY_CURRENT_DOCTOR_ID = "PREF_KEY_CURRENT_DOCTOR_ID";

    private final SharedPreferences preferences;

    @Inject
    public PreferencesManager(@PerApplication Context context)
    {
        String prefName = AppConstants.PREF_NAME;
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    @Override
    public int getCurrentUserId()
    {
        return preferences.getInt(PREF_KEY_CURRENT_USER_ID, 0);
    }

    @Override
    public void setCurrentUserId(int userId)
    {
        preferences.edit().putInt(PREF_KEY_CURRENT_USER_ID, userId).apply();
    }

    @Override
    public String getCurrentUserName()
    {
        return preferences.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName)
    {
        preferences.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }

    @Override
    public String getCurrentName()
    {
        return preferences.getString(PREF_KEY_CURRENT_NAME, null);
    }

    @Override
    public void setCurrentName(String name)
    {
        preferences.edit().putString(PREF_KEY_CURRENT_NAME, name).apply();
    }

    @Override
    public String getFireBaseToken()
    {
        return preferences.getString(PREF_KEY_CURRENT_FB_TOKEN, null);
    }

    @Override
    public void setFireBaseToken(String token)
    {
        preferences.edit().putString(PREF_KEY_CURRENT_FB_TOKEN, token).apply();
    }


    @Override
    public String getCurrentPassword()
    {
        return preferences.getString(PREF_KEY_CURRENT_PASSWORD, null);
    }

    @Override
    public void setCurrentPassword(String password)
    {
        preferences.edit().putString(PREF_KEY_CURRENT_PASSWORD, password).apply();
    }

    @Override
    public void deleteCurrentPassword()
    {
        preferences.edit().remove(PREF_KEY_CURRENT_PASSWORD).apply();
    }

    @Override
    public void saveCurrentUser(int idUser, String name, String username)
    {
        preferences.edit().putInt(PREF_KEY_CURRENT_USER_ID, idUser).apply();
        preferences.edit().putString(PREF_KEY_CURRENT_NAME, name).apply();
        preferences.edit().putString(PREF_KEY_CURRENT_USER_NAME, username).apply();
    }

    @Override
    public void addNotification(String notification)
    {
        String oldNotifications = getNotifications();
        if (oldNotifications != null)
        {
            oldNotifications += "|" + notification;
        } else
        {
            oldNotifications = notification;
        }
        preferences.edit().putString(PREF_KEY_NOTIFICATIONS, oldNotifications).apply();
    }

    @Override
    public String getNotifications()
    {
        return preferences.getString(PREF_KEY_NOTIFICATIONS, null);
    }

    @Override
    public int getCurrentCenterId()
    {
        return preferences.getInt(PREF_KEY_CURRENT_CENTER_ID, 0);
    }

    @Override
    public void setCurrentCenterId(int id_center)
    {
        preferences.edit().putInt(PREF_KEY_CURRENT_CENTER_ID, id_center).apply();
    }

    @Override
    public int getCurrentDoctorId()
    {
        return preferences.getInt(PREF_KEY_CURRENT_DOCTOR_ID, 0);
    }

    @Override
    public void setCurrentDoctorId(int id_doctor)
    {
        preferences.edit().putInt(PREF_KEY_CURRENT_DOCTOR_ID, id_doctor).apply();
    }

    @Override
    public String getAccessToken()
    {
        return preferences.getString(PREF_KEY_CURRENT_TOKEN, null);
    }

    @Override
    public void setAccessToken(String token)
    {
        preferences.edit().putString(PREF_KEY_CURRENT_TOKEN, token).apply();
    }
}
