package lt.jsj.qust.jjlin.helpers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import lt.jsj.qust.jjlin.ActivityMusicPlayer;
import lt.jsj.qust.jjlin.R;
import lt.jsj.qust.jjlin.events.BusEventNotificationShow;
import lt.jsj.qust.jjlin.events.BusEventNotificationUpdate;
import lt.jsj.qust.jjlin.services.MusicPlaybackService;

/**
 * Created by Adam on 2015/1/3.
 */
public class SMusicNotificationBuilder extends NotificationCompat.Builder {

    private Context mContext;

    private RemoteViews notiContentView;

    private Notification mNotification;

    public static final String ACTION_NOTI_CLEAR = "lt.jsj.qust.jjlin.helpers.ACTION_NOTI_CLEAR";
    public static final String ACTION_NOTI_PLAY = "lt.jsj.qust.jjlin.helpers.ACTION_NOTI_PLAY";
    /**
     * Constructor.
     * <p/>
     * Automatically sets the when field to {@link System#currentTimeMillis()
     * System.currentTimeMillis()} and the audio stream to the
     * {@link Notification#STREAM_DEFAULT}.
     *
     * @param context A {@link android.content.Context} that will be used to construct the
     *                RemoteViews. The Context will not be held past the lifetime of this
     *                Builder object.
     */
    public SMusicNotificationBuilder(Context context) {
        super(context);
        this.mContext = context;

        notiContentView = new RemoteViews(mContext.getPackageName(), R.layout.layout_notification);
    }

    public Notification build(BusEventNotificationShow event) {

        Intent resultIntent = new Intent(mContext, ActivityMusicPlayer.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(ActivityMusicPlayer.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notiContentView.setTextViewText(R.id.noti_tv_title, event.getSongName());
        notiContentView.setTextViewText(R.id.noti_tv_artist, event.getSongArtist());

        Intent closeIntent = new Intent(mContext, MusicPlaybackService.class);
        closeIntent.setAction(SMusicNotificationBuilder.ACTION_NOTI_CLEAR);
        PendingIntent pendingClose = PendingIntent.getService(mContext, 0, closeIntent, 0);
        notiContentView.setOnClickPendingIntent(R.id.noti_btn_close, pendingClose);
        Intent pauseResumeIntent = new Intent(mContext, MusicPlaybackService.class);
        pauseResumeIntent.setAction(SMusicNotificationBuilder.ACTION_NOTI_PLAY);
        PendingIntent pendingPauseResume = PendingIntent.getService(mContext, 0, pauseResumeIntent, 0);
        notiContentView.setOnClickPendingIntent(R.id.noti_btn_play, pendingPauseResume);

        mNotification =  new NotificationCompat.Builder(mContext)
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.ico_notification)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContent(notiContentView)
                .setTicker(event.getSongName())
                .build();
        return mNotification;
    }

    public Notification update(BusEventNotificationUpdate event){
        notiContentView.setTextViewText(R.id.noti_tv_title, event.getSongName());
        notiContentView.setTextViewText(R.id.noti_tv_artist, event.getSongArtist());
        return new NotificationCompat.Builder(mContext)
                .setContent(notiContentView)
                .setTicker(event.getSongName())
                .build();
    }

    public Notification updatePlayPauseButton(boolean isPlaying){
        if (isPlaying){
            mNotification.contentView.setImageViewResource(R.id.noti_btn_play, R.drawable.ic_noti_pause);
        }else{
            mNotification.contentView.setImageViewResource(R.id.noti_btn_play, R.drawable.ic_noti_play);
        }
        return mNotification;
    }

    public Notification updateOnPlayCompletion(){
        Intent replayIntent = new Intent(mContext, MusicPlaybackService.class);
        replayIntent.setAction(SMusicNotificationBuilder.ACTION_NOTI_PLAY);
        PendingIntent pendingReplayIntent = PendingIntent.getService(mContext, 0, replayIntent, 0);
        mNotification.contentView.setOnClickPendingIntent(R.id.noti_btn_play, pendingReplayIntent);
        return mNotification;
    }

    public Notification updateCoverPicture(Bitmap coverPicture){
        mNotification.contentView.setImageViewBitmap(R.id.noti_cover, coverPicture);
        return mNotification;
    }
}
