package com.medhelp2.mhchat.data.db;


import io.realm.RealmConfiguration;

public class RealmConfig
{
    public RealmConfiguration getConfig()
    {
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new RealmMigration())
                .build();
    }
}
