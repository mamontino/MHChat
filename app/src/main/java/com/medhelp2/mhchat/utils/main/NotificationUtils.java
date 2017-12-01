package com.medhelp2.mhchat.utils.main;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.data.DataManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


public class NotificationUtils
{
    @Inject
    DataManager dataManager;

    private Context context;

    public NotificationUtils(Context context)
    {
        this.context = context;
    }

    public void showNotificationMessage(final String username, final String message, final String timeStamp, Intent intent)
    {
        if (TextUtils.isEmpty(message))
        {
            return;
        }

        final int icon = R.drawable.ic_message_white_24dp;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/notification");
        showSoundNotification(builder, icon, username, message, timeStamp, resultPendingIntent, alarmSound);
        playNotificationSound();
    }


    private void showSoundNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound)
    {

    }

    private void showVibrateNotification()
    {

    }

    private void showNoSondNotification()
    {

    }

    /**
     * Загрузка изображения push-уведомления перед его отображением в ветке уведомлений
     */

    public Bitmap getBitmapFromURL(String strURL)
    {
        try
        {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Воспроизведение звука уведомления
     */

    public void playNotificationSound()
    {
        try
        {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/notification");
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSound);
            ringtone.play();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Метод проверяет, находится ли приложение в фоновом режиме или нет.
     */

    public static boolean isAppIsInBackground(Context context)
    {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH)
        {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses)
            {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                {
                    for (String activeProcess : processInfo.pkgList)
                    {
                        if (activeProcess.equals(context.getPackageName()))
                        {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else
        {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName()))
            {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    /**
     * Удаление сообщения из ветки уведомлений
     */

    public static void clearNotifications(Context context)
    {
        NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    /**
     * Получение даты входящего сообщения
     */

    public static long getTimeMilliSec(String timeStamp)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public void showSplashActivity()
    {
    }
}
