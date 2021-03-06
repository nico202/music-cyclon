package max.music_cyclon.service;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Random;

import max.music_cyclon.R;


public class ProgressUpdater {

    public static int NOTIFICATION_ID = new Random().nextInt();

    private Context context;

    private int maximum = 0;
    private final NotificationManagerCompat notificationManager;

    private int downloadCount = 0;

    public ProgressUpdater(Context context) {
        this.context = context;

        this.notificationManager = NotificationManagerCompat.from(context);
    }

    public void showMessage(String message, Object... args) {
        showMessage(String.format(message, args));
    }

    public void showMessage(String message) {
        NotificationCompat.Builder builder = notificationBuilder();
        builder.setContentTitle(message);

        updateNotification(builder);
    }

    public void showOngoingMessage(String message, Object... args) {
        showOngoingMessage(String.format(message, args));
    }


    public void showOngoingMessage(String message) {
        NotificationCompat.Builder builder = notificationBuilder();
        builder.setContentTitle(message);
        builder.setProgress(0, 0, true);
        builder.setOngoing(true);

        updateNotification(builder);
    }

    public synchronized void increment() {
        NotificationCompat.Builder builder = progressNotificationBuilder();
        downloadCount++;

        builder.setContentTitle("Aktualisiere Musik");
        builder.setContentText(downloadCount + "/" + maximum);
        builder.setProgress(maximum, downloadCount, false);
        builder.setOngoing(true);
        updateNotification(builder);
    }

    public void setMaximumProgress(int maximum) {
        this.maximum = maximum;
    }

    public void updateNotification(NotificationCompat.Builder builder) {
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public NotificationCompat.Builder notificationBuilder() {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_sync_white_24dp);
    }

    private NotificationCompat.Builder progressNotificationBuilder() {
        return notificationBuilder().setUsesChronometer(true)
                .setOngoing(true)
                .setProgress(0, 0, true);
    }
}
