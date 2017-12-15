package com.medhelp2.mhchat.data.pref;

public interface PreferencesHelper
{
    int getCurrentUserId();

    void setCurrentUserId(int userId);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getCurrentName();

    void setCurrentName(String name);

    String getFireBaseToken();

    void setFireBaseToken(String token);

    String getCurrentPassword();

    void setCurrentPassword(String name);

    void deleteCurrentPassword();

    void saveCurrentUser(int idUser, String name, String username);

    void addNotification(String notification);

    String getNotifications();

    int getCurrentCenterId();

    void setCurrentCenterId(int id_center);

    int getCurrentDoctorId();

    void setCurrentDoctorId(int id_doctor);

    String getAccessToken();

    void setAccessToken(String token);
}
