package com.example.delivery.Model;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.delivery.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class Messaging extends FirebaseMessagingService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        System.out.println(remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            System.out.println(remoteMessage.getData());

            JSONObject data = new JSONObject(remoteMessage.getData());
            String name = null;
            try {
                name = data.getString("Patient");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String time = null;
            try {
                time = data.getString("Arrival");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            createNotificationChannel();

            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "dajt")
                    .setSmallIcon(R.drawable.italy)
                    .setContentTitle("Delivery Status")
                    .setContentText(name + " your delivery is arriving in " + time + " minutes.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            notificationManager.notify(100, builder.build());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(){

        CharSequence name = "dajtChannel";
        String description = "Channel for Dajt";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("dajt", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager= getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

}
