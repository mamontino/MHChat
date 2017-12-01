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
//            schema.create("UserResponse")
//                    .addField(AppNames.ID_USER, int.class)
//                    .addField(AppNames.USERNAME, String.class)
//                    .addField(AppNames.EMAIL, String.class)
//                    .addField(AppNames.TIMESTAMP, String.class)
//                    .addField(AppNames.FB_KEY, String.class);
//
//            schema.create("RoomResponse")
//                    .addField(AppNames.ID_ROOM, int.class)
//                    .addField(AppNames.TITLE, String.class)
//                    .addField(AppNames.TIMESTAMP, String.class);
//
//            schema.create("UserResponse")
//                    .addField(AppNames.ID_USER, int.class)
//                    .addField(AppNames.USERNAME, String.class)
//                    .addField(AppNames.EMAIL, String.class)
//                    .addField(AppNames.TIMESTAMP, String.class)
//                    .addField(AppNames.FB_KEY, String.class);
//
//            schema.create("MessageResponse")
//                    .addField(AppNames.ID_MESSAGE, int.class)
//                    .addField(AppNames.MESSAGE, String.class)
//                    .addField(AppNames.ID_ROOM, int.class)
//                    .addField(AppNames.TIMESTAMP, String.class)
//                    .addField(AppNames.ID_USER, int.class)
//                    .addField(AppNames.IS_READ, boolean.class);

            oldVersion++;
        }
    }
}
