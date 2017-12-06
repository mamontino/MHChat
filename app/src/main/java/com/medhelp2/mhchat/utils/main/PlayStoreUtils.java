package com.medhelp2.mhchat.utils.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.medhelp2.mhchat.R;


public final class PlayStoreUtils
{
    private PlayStoreUtils()
    {
    }

    public static void openPlayStoreForApp(Context context)
    {
        final String appPackageName = context.getPackageName();
        try
        {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e)
        {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }
}
