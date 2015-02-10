package lt.jsj.qust.jjlin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import lt.jsj.qust.jjlin.beans.Music;
import lt.jsj.qust.jjlin.events.BusEventActivityPlayPause;
import lt.jsj.qust.jjlin.events.BusEventMediaPlayerCompleted;
import lt.jsj.qust.jjlin.events.BusEventMediaPlayerPrepared;
import lt.jsj.qust.jjlin.events.BusEventNotificationCancel;
import lt.jsj.qust.jjlin.events.BusEventNotificationPlayPause;
import lt.jsj.qust.jjlin.events.BusEventOnServiceRebind;
import lt.jsj.qust.jjlin.helpers.BusProvider;
import lt.jsj.qust.jjlin.helpers.SMusicNotificationManager;
import lt.jsj.qust.jjlin.services.MusicPlaybackService;
import lt.jsj.qust.jjlin.util.Utils;
import lt.jsj.qust.jjlin.views.MusicTextView;


public class ActivityMusicPlayer extends ActionBarActivity {
    public static final String ACTION_SERVICE_CREATE = "lt.jsj.qust.jjlin.ACTION_SERVICE_CREATE";
    public static final String ACTION_SERVICE_BIND = "lt.jsj.qust.jjlin.ACTION_SERVICE_BIND";

    private MusicTextView mSongTitle = null;
    private MusicTextView mSongArtist = null;
    private MusicTextView mSongTotalListen = null;
    private MusicTextView mSongDuration = null;
    private MusicTextView mSongCurrentDuration = null;
    private ImageButton mBtnRepeat = null;
    private ImageButton mBtnShuffle = null;
    private ImageButton mBtnNext = null;
    private ImageButton mBtnPrev = null;
    private ImageButton mBtnPlayPause = null;
    private ImageView mSongCover = null;
    private SeekBar mSeekbar = null;
    Music mPlayingSong=null;
    private static final String CLASS_NAME = "ActivityMusicPlayer";
    private Intent mReceiveIntent = null;
    private Handler mHandler = new Handler();

    // Notification
    public static final int NOTIFICATION_ID = 0;
    private Notification notification;
    private NotificationCompat.Builder mNotifyBuilder;
    private NotificationManager mNotiManager;

    private boolean mIsBound = false;
    private MusicPlaybackService mPlaybackService;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        // This is called when the connection with the service has been
        // established, giving us the service object we can use to
        // interact with the service. Because we have bound to a explicit
        // service that we know is running in our own process, we can
        // cast its IBinder to a concrete class and directly access it.
        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.d(Constants.LOG_TAG, CLASS_NAME + " - onServiceConnected");
            mPlaybackService = ((MusicPlaybackService.MusicServiceBinder) service).getService();
            mIsBound = true;
            if (mPlaybackService.isPlaying()) {
                mHandler.postDelayed(mUpdateTimeTask, 100);
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
//            Log.d(Constants.LOG_TAG, CLASS_NAME + " - onServiceDisconnected");
            mPlaybackService = null;
        }
    };
    //Update seekbar
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            // Running this thread after 100 milliseconds
            if (mIsBound && mPlaybackService != null && mPlaybackService.isPlaying()) {
                int currentPos = mPlaybackService.getCurrentPosition();
                mSongCurrentDuration.setText(Utils.makeTimeString(currentPos));
                int progress = Utils.getProgressPercentage(currentPos, mPlaybackService.getDuration());
                mSeekbar.setProgress(progress);
                mHandler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_music_player);
        //Initialize notfication
        this.mNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        this.mNotifyBuilder = new NotificationCompat.Builder(this);

        getExtraData();
        findViews();
        initializeViews();
        startMusicservice(mPlayingSong);
        Intent startServiceIntent = new Intent(this, MusicPlaybackService.class);
        startServiceIntent.setAction(ActivityMusicPlayer.ACTION_SERVICE_CREATE);
        startService(startServiceIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent bindIntent = new Intent(this, MusicPlaybackService.class);
        bindIntent.setAction(ActivityMusicPlayer.ACTION_SERVICE_BIND);
        this.doBindService(bindIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.doUnbindService();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    private void doUnbindService() {
        if (mIsBound) {
//            Log.d(Constants.LOG_TAG, CLASS_NAME + " - doUnbindService");
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    private void doBindService(Intent bindIntent) {
        // Establish a connection with the service. We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
//        Log.d(Constants.LOG_TAG, CLASS_NAME + " - doBindService");
        if (!mIsBound){
            bindService(bindIntent, mConnection,
                    Context.BIND_AUTO_CREATE);
        }
    }

    private void initializeViews() {
        if (mPlayingSong != null) {
            mSongTitle.setText(mPlayingSong.getMusicName());
            mSongArtist.setText("林俊杰");
//            mSongTotalListen.setText(String.valueOf(mPlayingSong.getTotalListen()));
            mBtnPlayPause.setOnClickListener(mPauseListener);
            mSeekbar.setOnSeekBarChangeListener(mSeekbarListener);
            Picasso.with(getApplicationContext()).load(mPlayingSong.getMusicimg()).into(mSongCover);
        }
    }

    private void findViews() {
        mSongTitle = (MusicTextView) findViewById(R.id.player_song_title);
        mSongArtist = (MusicTextView) findViewById(R.id.player_song_artist);
        mSongCover = (ImageView) findViewById(R.id.player_song_cover);
        mSongTotalListen = (MusicTextView) findViewById(R.id.player_song_total_listen);
        mSongDuration = (MusicTextView) findViewById(R.id.player_duration_total);
        mSongCurrentDuration = (MusicTextView) findViewById(R.id.player_duration_current);
        mSeekbar = (SeekBar) findViewById(R.id.player_seekbar);
        mBtnPlayPause = (ImageButton) findViewById(R.id.player_play);
        mBtnNext = (ImageButton) findViewById(R.id.player_next);
        mBtnPrev = (ImageButton) findViewById(R.id.player_previous);
        mBtnRepeat = (ImageButton) findViewById(R.id.player_repeat);
        mBtnShuffle = (ImageButton) findViewById(R.id.player_shuffle);
    }

    private void getExtraData() {
        mReceiveIntent = this.getIntent();
        mPlayingSong = (Music) mReceiveIntent.getSerializableExtra("music");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null){
            String action=intent.getAction();
            if(action!=null){
                if(action.equals("ACTION_CREATE_NEW")){
                    mPlayingSong = (Music) intent.getSerializableExtra("music");
                    initializeViews();
//                    loadSongDetail();
                    startMusicservice(mPlayingSong);

                    //Start service if not started
                    //If the service was already started, only onStartCommand will be called
                    Intent startServiceIntent = new Intent(this, MusicPlaybackService.class);
                    startServiceIntent.setAction(ActivityMusicPlayer.ACTION_SERVICE_CREATE);
                    startService(startServiceIntent);
                }
            }
        }
    }

    private void startMusicservice(Music mPlayingSong) {
        Intent serviceIntent = new Intent(this, MusicPlaybackService.class);
        serviceIntent.putExtra("url", mPlayingSong.getMusicurl());
        serviceIntent.putExtra("title", mPlayingSong.getMusicName());
        serviceIntent.putExtra("artist", "林俊杰");
        startService(serviceIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_music_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private boolean isNotificationVisible(){
        Intent notificationIntent = new Intent(this, ActivityMusicPlayer.class);
        PendingIntent test = PendingIntent.getActivity(this, SMusicNotificationManager.NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", mSongTitle.getText().toString());
        outState.putString("artist", mSongArtist.getText().toString());
//        outState.putString("listen_count", mSongTotalListen.getText().toString());
        outState.putString("duration", mSongDuration.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mSongTitle.setText(savedInstanceState.getString("title"));
        }
    }

    @Subscribe
    public void onRebindService(BusEventOnServiceRebind event){
        //A rebind event occurs, continue updating seekbar
        if (mPlaybackService.isPlaying()) {
            mHandler.postDelayed(mUpdateTimeTask, 100);
        }
    }

    @Subscribe
    public void onMediaPlayerPrepared(BusEventMediaPlayerPrepared event) {
        mSongDuration.setText(Utils.makeTimeString(event.getSongDuration()));
        setPlayPauseButton();
        mSeekbar.setMax(100);
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }


    @Subscribe
    public void onNotificationClear(BusEventNotificationCancel event){
        if (mPlaybackService != null) {
            if (mPlaybackService.isPlaying()) {
                mPlaybackService.pause();
            }
            setPlayPauseButton();
        }
    }

    @Subscribe
    public void onNotificationPlayPause(BusEventNotificationPlayPause event){
        doPauseResume();
    }

    @Subscribe
    public void onMediaPlayerCompleted(BusEventMediaPlayerCompleted event) {
        /**
         * Need updated clearNotification if automatic next song featured is implemented
         * Currently the application stop playing after finishing a song so the notification should be cleared
         */
        this.clearNotification();
        mSongCurrentDuration.setText("00:00");
        mSongDuration.setText("00:00");
        setPlayPauseButton();
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    private View.OnClickListener mPauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doPauseResume();
        }
    };


    private SeekBar.OnSeekBarChangeListener mSeekbarListener = new SeekBar.OnSeekBarChangeListener() {

        /**
         * Notification that the user has started a touch gesture. Clients may want to use this
         * to disable advancing the seekbar.
         *
         * @param seekBar The SeekBar in which the touch gesture began
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mHandler.removeCallbacks(mUpdateTimeTask);
        }

        /**
         * Notification that the user has finished a touch gesture. Clients may want to use this
         * to re-enable advancing the seekbar.
         *
         * @param seekBar The SeekBar in which the touch gesture began
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mIsBound && mPlaybackService.isPlaying()) {
                int totalDuration = mPlaybackService.getDuration();
                int newPos = Utils.progressToTimer(seekBar.getProgress(),
                        totalDuration);
                mPlaybackService.seekTo(newPos);
                mHandler.postDelayed(mUpdateTimeTask, 100);
            }
        }

        /**
         * Notification that the progress level has changed. Clients can use the fromUser parameter
         * to distinguish user-initiated changes from those that occurred programmatically.
         *
         * @param seekBar  The SeekBar whose progress has changed
         * @param progress The current progress level. This will be in the range 0..max where max
         *                 was set by {@link android.widget.ProgressBar#setMax(int)}. (The default value for max is 100.)
         * @param fromUser True if the progress change was initiated by the user.
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }
    };


    private void setPlayPauseButton() {
        if (mPlaybackService != null && mPlaybackService.isPlaying()) {
            mBtnPlayPause.setImageResource(R.drawable.ic_pause_black_48dp);
        } else {
            mBtnPlayPause.setImageResource(R.drawable.ic_play_arrow_black_48dp);
        }
    }

    private void doPauseResume() {
        if (mPlaybackService != null) {
            if (mPlaybackService.isPlaying()) {
                mPlaybackService.pause();
                BusProvider.getInstance().post(new BusEventActivityPlayPause(true));
            } else {
                mPlaybackService.resume();
                BusProvider.getInstance().post(new BusEventActivityPlayPause(false));
            }
//            refreshNow();
            setPlayPauseButton();
        }
    }


    private void shareCurrentTrack(){
        Toast.makeText(this, "Sharing...", Toast.LENGTH_SHORT).show();
    }

    // build notification
    private void buildNotification(String strSongName, String strArtistName) {
        Intent resultIntent = new Intent(this, ActivityMusicPlayer.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ActivityMusicPlayer.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews notiContentView = new RemoteViews(getPackageName(), R.layout.layout_notification);
        notiContentView.setTextViewText(R.id.noti_tv_title, strSongName);
        notiContentView.setTextViewText(R.id.noti_tv_artist, strArtistName);
        Intent closeIntent = new Intent("CLOSE_APPLICATION");
        PendingIntent pendingClose = PendingIntent.getBroadcast(this, 0, closeIntent, 0);
        notiContentView.setOnClickPendingIntent(R.id.noti_btn_close, pendingClose);

//        this.notification = this.mNotifyBuilder
//                .setContentIntent(resultPendingIntent)
//                .setSmallIcon(R.drawable.ico_notification)
//                .setContentTitle(strSongName)
//                .setContentText(strArtistName)
//                .setAutoCancel(false)
//                .setOngoing(true)
//                .build();
        this.notification = this.mNotifyBuilder
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.ico_notification)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContent(notiContentView)
                .setTicker(strSongName)
                .build();
        this.mNotiManager.notify(NOTIFICATION_ID, notification);

        closeButtonListener = new CloseButtonListener();
        registerReceiver(closeButtonListener, new IntentFilter("CLOSE_APPLICATION"));
    }

    //Update notification content
    private void updateNotification(String strNewSongName, String strNewArtistName){
        this.notification = this.mNotifyBuilder
                .setContentTitle(strNewSongName)
                .setContentText(strNewArtistName)
                .build();
        this.mNotiManager.notify(NOTIFICATION_ID, this.notification);

    }

    // Cancel notification
    private void clearNotification(){
        this.mNotiManager.cancel(NOTIFICATION_ID);
    }

    private CloseButtonListener closeButtonListener;

    private class CloseButtonListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            clearNotification();
            doPauseResume();
            unregisterReceiver(closeButtonListener);
        }
    }
}


