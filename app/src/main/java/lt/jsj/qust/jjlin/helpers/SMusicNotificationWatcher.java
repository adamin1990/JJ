package lt.jsj.qust.jjlin.helpers;

import com.squareup.otto.Subscribe;

import lt.jsj.qust.jjlin.events.BusEventActivityPlayPause;
import lt.jsj.qust.jjlin.events.BusEventNotificationCancel;
import lt.jsj.qust.jjlin.events.BusEventNotificationLoadCover;
import lt.jsj.qust.jjlin.events.BusEventNotificationPlayPause;
import lt.jsj.qust.jjlin.events.BusEventNotificationShow;

/**
 * Created by Adam on 2015/1/3.
 */
public class SMusicNotificationWatcher {
    private final SMusicNotificationManager mNotificationManager;
    private final SMusicNotificationBuilder mNotificationBuilder;


    public SMusicNotificationWatcher(SMusicNotificationManager notificationManager, SMusicNotificationBuilder notificationBuilder) {
        this.mNotificationManager = notificationManager;
        this.mNotificationBuilder = notificationBuilder;
    }

    public void register(){
        BusProvider.getInstance().register(this);
    }

    public void unregister(){
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void on(BusEventNotificationShow event){
        mNotificationManager.showNotification(mNotificationBuilder.build(event));
    }

    @Subscribe
    public void on(BusEventNotificationCancel eventCancel){
        mNotificationManager.hideNotification();
    }

    @Subscribe
    public void on(BusEventNotificationPlayPause event){
        mNotificationManager.updatePlayPauseButtom(mNotificationBuilder.updatePlayPauseButton(event.isPlaying()));
    }

    @Subscribe
    public void on(BusEventActivityPlayPause event){
        mNotificationManager.updatePlayPauseButtom(mNotificationBuilder.updatePlayPauseButton(event.isPlaying()));
    }

    @Subscribe
    public void on(BusEventNotificationLoadCover event){
        mNotificationManager.updateCoverPicture(mNotificationBuilder.updateCoverPicture(event.getCoverPicture()));
    }
}
