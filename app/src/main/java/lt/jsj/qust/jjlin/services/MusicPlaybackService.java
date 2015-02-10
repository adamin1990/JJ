package lt.jsj.qust.jjlin.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.SyncStateContract;
import android.util.Log;

import java.io.IOException;

import lt.jsj.qust.jjlin.ActivityMusicPlayer;
import lt.jsj.qust.jjlin.events.BusEventMediaPlayerCompleted;
import lt.jsj.qust.jjlin.events.BusEventMediaPlayerPrepared;
import lt.jsj.qust.jjlin.events.BusEventNotificationCancel;
import lt.jsj.qust.jjlin.events.BusEventNotificationLoadCover;
import lt.jsj.qust.jjlin.events.BusEventNotificationPlayPause;
import lt.jsj.qust.jjlin.events.BusEventNotificationShow;
import lt.jsj.qust.jjlin.helpers.BusProvider;
import lt.jsj.qust.jjlin.helpers.SMusicNotificationBuilder;
import lt.jsj.qust.jjlin.helpers.SMusicNotificationManager;
import lt.jsj.qust.jjlin.helpers.SMusicNotificationWatcher;

/**
 * Created by Adam on 2015/1/3.
 */
public class MusicPlaybackService extends Service implements MediaPlayer.OnPreparedListener
        , MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener{

    private static final String SERVICE_NAME = "MusicPlaybackService";

    private boolean hasBoundClient = false;

    private MediaPlayer mMediaPlayer = null;
    private boolean isPlaying = false;

    private String strPlayingSongName;
    private String strPlayingSongArtist;

    // This is the object that receives interactions from clients. See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new MusicServiceBinder();

    // Notification
    private SMusicNotificationManager mNotificationManager;
    private SMusicNotificationBuilder mNotificationBuilder;
    private SMusicNotificationWatcher mNotificationWatcher;


    public MusicPlaybackService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.setupMediaPlayer();

        mNotificationManager = new SMusicNotificationManager((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        mNotificationBuilder = new SMusicNotificationBuilder(this);
        mNotificationWatcher = new SMusicNotificationWatcher(mNotificationManager, mNotificationBuilder);
        mNotificationWatcher.register();
    }


    private void setupMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
//        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
    }

    public void setHasBoundClient(boolean status){
        this.hasBoundClient = status;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String intentAction = intent.getAction();
            if (intentAction != null){
                if (intentAction.equals(SMusicNotificationBuilder.ACTION_NOTI_CLEAR)){
                    Log.d("service", SERVICE_NAME + ": onStartCommand - action CLEAR_NOTIFICATION: hasBoundClient = " + hasBoundClient);
                    BusProvider.getInstance().post(new BusEventNotificationCancel());
                    if (!hasBoundClient){
                        pause();
                    }
                }else if (intentAction.equals(ActivityMusicPlayer.ACTION_SERVICE_CREATE)){
                    //just create service, do nothing now, waiting for play intent
                }else if (intentAction.equals(ActivityMusicPlayer.ACTION_SERVICE_BIND)) {
                    //Bind action
                }else if (intentAction.equals(SMusicNotificationBuilder.ACTION_NOTI_PLAY)){
                    BusProvider.getInstance().post(new BusEventNotificationPlayPause(isPlaying()));
                    if(!hasBoundClient) {
                        if (isPlaying()){
                            pause();
                        }else{
                            resume();
                        }
                    }
                }
            }
            else{
                Bundle extras = intent.getExtras();
                String url = (String) extras.get("url");
                this.strPlayingSongName = (String) extras.get("title");
                this.strPlayingSongArtist = (String) extras.get("artist");
                this.initMediaPlayer(url);
            }
        }
        return START_STICKY;
    }

    public void resume() {
        if (mMediaPlayer != null && !this.isPlaying()) {
            mMediaPlayer.start();
            BusProvider.getInstance().post(new BusEventNotificationShow(strPlayingSongName, strPlayingSongArtist));
            this.isPlaying = true;
        }
    }

    public boolean isPlaying() {
        boolean isPlaying = false;
        if (mMediaPlayer != null){
            try {
                isPlaying = mMediaPlayer.isPlaying();
                return isPlaying;
            }catch (IllegalStateException exception){
            }
        }
        return isPlaying;
    }

    public void pause() {
        if (mMediaPlayer != null && this.isPlaying()) {
            mMediaPlayer.pause();
            this.isPlaying = false;
        }
    }

    private void initMediaPlayer(String url) {

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.reset();
            mMediaPlayer.setLooping(true);
        }
        try {
            mMediaPlayer.setDataSource(url);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    public int getCurrentPosition(){
        if (mMediaPlayer != null && this.isPlaying()){
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration(){
        if (mMediaPlayer != null){
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    public void seekTo(long whereTo){
        if (mMediaPlayer != null && this.isPlaying){
            mMediaPlayer.seekTo((int) whereTo);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        hasBoundClient = true;
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        hasBoundClient = false;
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
//        Log.d(Constants.LOG_TAG, SERVICE_NAME + ": start playing...");
//        Log.d(Constants.LOG_TAG, SERVICE_NAME + ": duration = " + mp.getDuration());
        this.isPlaying = true;
        BusProvider.getInstance().post(new BusEventMediaPlayerPrepared(mp.getDuration()));
        BusProvider.getInstance().post(new BusEventNotificationShow(strPlayingSongName, strPlayingSongArtist));
        if (mCoverPicture != null){
            BusProvider.getInstance().post(new BusEventNotificationLoadCover(mCoverPicture));
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        this.isPlaying = false;
        mMediaPlayer.stop();
        BusProvider.getInstance().post(new BusEventMediaPlayerCompleted());

        /**
         * Remove notification: need updating after adding auto-next feature
         */

        //BusProvider.getInstance().post(new BusEventActivityPlayPause(true));

        //stopSelf();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }
    public class MusicServiceBinder extends Binder {
        public MusicPlaybackService getService() {
            return MusicPlaybackService.this;
        }
    }
    private Bitmap mCoverPicture;
    public void setBitmap(Bitmap coverPicture){
        mCoverPicture = coverPicture;
    }
    }

