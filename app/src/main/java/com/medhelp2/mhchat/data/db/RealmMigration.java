package com.medhelp2.mhchat.data.db;

import android.support.annotation.NonNull;

import io.realm.DynamicRealm;
import io.realm.RealmSchema;

public class RealmMigration implements io.realm.RealmMigration
{
    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion)
    {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0)
        {
            oldVersion++;
        }
    }
}
