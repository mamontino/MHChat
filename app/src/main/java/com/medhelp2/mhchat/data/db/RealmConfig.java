package com.medhelp2.mhchat.data.db;

import io.realm.RealmConfiguration;

class RealmConfig
{
    RealmConfiguration getConfig()
    {
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new RealmMigration())
                .build();
    }
}
