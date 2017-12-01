package com.medhelp2.mhchat.bg;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.ui.chat.chat_list.ChatListFragment;
import com.medhelp2.mhchat.ui.contacts.ContactsActivity;
import com.medhelp2.mhchat.ui.contacts.contacts_list.ContactsListFragment;
import com.medhelp2.mhchat.ui.splash.SplashActivity;
import com.medhelp2.mhchat.utils.main.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

import static com.medhelp2.mhchat.ui.chat.chat_list.ChatListFragment.BROADCAST_INCOMING_MESSAGE;


public class ChatFireBaseMessagingService extends FirebaseMessagingService
{
    public static final int NOTIFICATION_ID = 357;
    private int notificationId;

    private static final String ID_ROOM = "id_room";
    private static final String ID_DOCTOR = "id_doctor";
    private static final String MESSAGE = "message";
    private JSONObject data;
    private String message;
    private int idRoom;
    private int idDoctor;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Timber.d("Получено новое сообщение от: " + remoteMessage.getFrom());
        try
        {
            data = new JSONObject(remoteMessage.getData().toString()).getJSONObject("data");
            message = data.getString(MESSAGE);
            idRoom = Integer.parseInt(data.getString(ID_ROOM));
            idDoctor = Integer.parseInt(data.getString(ID_DOCTOR));

        } catch (JSONException e)
        {
            Timber.e(e.getMessage());
        }

        // TODO: Если remoteMessage содержит данные (data message)

        if (remoteMessage.getData().size() > 0)
        {
            Timber.d("Полученные данные: " + remoteMessage.getData());

            if (((MainApp) getApplicationContext()).isChatActivityStarted)
            {
                Timber.d("ChatActivity запущено");
                Timber.d("id_room: " + idRoom);

                try
                {
                    showChatActivity(idRoom);
                } catch (Exception e)
                {
                    Timber.e("Ошибка создания BroadcastReceiver: " + e.getMessage());
                }
            } else if (NotificationUtils.isAppIsInBackground(getApplicationContext()))
            {
                try
                {
                    Timber.d("Приложение остановлено");
                    showSplashActivity(message);
                } catch (Exception e)
                {
                    Timber.d("Ошибка запуска приложения: " + e.getMessage());
                }

            } else if (((MainApp) getApplicationContext()).isContactsActivityStarted)
            {
                try
                {
                    Timber.d("DoctorsActivity запущено");
                    Intent startActivity = new Intent(BROADCAST_INCOMING_MESSAGE);
                    startActivity.putExtra(ContactsListFragment.PARAM_STATUS, ContactsListFragment.STATUS_START);
                    sendBroadcast(startActivity);
                    startActivity.putExtra(ContactsListFragment.PARAM_STATUS, ContactsListFragment.STATUS_FINISH);
                } catch (Exception e)
                {
                    Timber.e("Ошибка создания BroadcastReceiver: " + e.getMessage());
                }
            } else
            {
                Timber.d("Приложение запущено");
                try
                {
                    showContactsActivity(message);
                } catch (Exception e)
                {
                    Timber.d("Ошибка перехода к DoctorsActivity: " + e.getMessage());
                }
            }

//            if (/* Check if data needs to be processed by long running job */ true) {
            // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
            // Handle message within 10 seconds
//                handleNow();
//            }

        }

        // TODO: Исли remoteMessage содержит Notification

        if (remoteMessage.getNotification() != null)
        {
            Timber.d("Message Notification Body: " + remoteMessage.getData());
        }
    }

    private void showChatActivity(int idRoom)
    {
        Intent startActivity = new Intent(BROADCAST_INCOMING_MESSAGE);
        startActivity.putExtra(ChatListFragment.PARAM_STATUS, ChatListFragment.STATUS_START);
        startActivity.putExtra(ChatListFragment.CHAT_ROOM_ID, idRoom);
        Timber.d("id_room: " + idRoom);
        sendBroadcast(startActivity);
        startActivity.putExtra(ChatListFragment.PARAM_STATUS, ChatListFragment.STATUS_FINISH);
    }

    private void showContactsActivity(String message)
    {
        Timber.d("sendNotification");

        Intent intent = new Intent(this, ContactsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        notificationId = NOTIFICATION_ID;

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_message_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_message_white_24dp))
                .setContentTitle(this.getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private void showSplashActivity(String message)
    {
        Timber.d("sendNotification");

        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        notificationId = NOTIFICATION_ID;

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_message_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_message_white_24dp))
                .setContentTitle(this.getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

//    private void sendNotification(String message)
//    {
//        Timber.d("sendNotification");
//
//        Intent intent = new Intent(this, DoctorsActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        final PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        notificationId = NOTIFICATION_ID;
//
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_message_white_24dp)
//                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_message_white_24dp))
//                .setContentTitle(this.getString(R.string.app_name))
//                .setContentText(message)
//                .setContentIntent(resultPendingIntent)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(notificationId, notificationBuilder.build());
//    }
//
//    private void showIncommingMessage()
//    {
//
//    }
//
//    private void showChatActivity(int idRoom, String name)
//    {
//        Intent intent = new Intent(this, ChatActivity.class);
//        intent.putExtra(AppConstants.ID_ROOM, idRoom);
//        intent.putExtra(AppConstants.ROOM_NAME, name);
//        startActivity(intent);
//    }
}