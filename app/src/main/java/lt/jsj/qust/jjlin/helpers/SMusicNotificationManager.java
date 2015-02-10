package lt.jsj.qust.jjlin.helpers;

import android.app.Notification;
import android.app.NotificationManager;

/**
 * Created by Adam on 2015/1/3.
 */
public class SMusicNotificationManager {

    public static final int NOTIFICATION_ID = 301;
    private final NotificationManager mNotificationManager;


    public SMusicNotificationManager(NotificationManager notificationManager) {
        this.mNotificationManager = notificationManager;
    }

    public void showNotification(Notification notification){
        this.mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void hideNotification(){
        this.mNotificationManager.cancel(NOTIFICATION_ID);
    }

    public void updatePlayPauseButtom(Notification notification){
        this.mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void updateCoverPicture(Notification notification){
        this.mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    public int getNotificationId(){
        return NOTIFICATION_ID;
    }
}
