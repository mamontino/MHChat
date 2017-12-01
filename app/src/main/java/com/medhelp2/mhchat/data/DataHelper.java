package com.medhelp2.mhchat.data;

import com.medhelp2.mhchat.data.db.RealmHelper;
import com.medhelp2.mhchat.data.network.NetworkHelper;
import com.medhelp2.mhchat.data.pref.PreferencesHelper;


public interface DataHelper extends PreferencesHelper, NetworkHelper, RealmHelper
{
    void saveCurrentUser(
            int idUser,
            String name,
            String username
    );

    boolean checkNetwork();
}
