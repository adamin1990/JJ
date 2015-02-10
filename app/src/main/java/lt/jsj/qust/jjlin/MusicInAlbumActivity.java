package lt.jsj.qust.jjlin;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lt.jsj.qust.jjlin.beans.Music;
import lt.jsj.qust.jjlin.dao.MusicDao;
import lt.jsj.qust.jjlin.views.AlbumDetailArtCover;
import lt.jsj.qust.jjlin.views.MusicTextView;
import lt.jsj.qust.jjlin.views.ObservableScrollable;
import lt.jsj.qust.jjlin.views.OnScrollChangedCallback;


public class MusicInAlbumActivity extends ActionBarActivity implements OnScrollChangedCallback {
    private int albumid;
    private String albumname;
    private String albumimg;
    private List<Music> musics;
    List<Music> musiclist=new ArrayList<>();
    private ListView listView;
    private Toolbar mToolbar;
    private Drawable mActionBarBackgroundDrawable;
    private ImageView mHeader;
    private int mLastDampedScroll;
    private int mInitialStatusBarColor;
    private int mFinalStatusBarColor;
    private SystemBarTintManager mStatusBarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        albumid = intent.getIntExtra("albumid",1);
        albumname = intent.getStringExtra("albumname");
        albumimg=intent.getStringExtra("albumpicture");
        setContentView(R.layout.activity_music_in_album);
//        SwipeBack.attach(this, Position.LEFT).setContentView(R.layout.activity_music_in_album).setSwipeBackView(R.layout.swipeback_default);
        mToolbar = (Toolbar) findViewById(R.id.mtoolbar);
        mToolbar.setTitle(albumname);
        mToolbar.setSubtitle("林俊杰");
        listView= (ListView) findViewById(R.id.list_items);
        mActionBarBackgroundDrawable = mToolbar.getBackground();
        setSupportActionBar(mToolbar);

        mStatusBarManager = new SystemBarTintManager(this);
        mStatusBarManager.setStatusBarTintEnabled(true);
        mInitialStatusBarColor = Color.BLACK;
        mFinalStatusBarColor = getResources().getColor(R.color.primary_color_dark);

        mHeader = (ImageView) findViewById(R.id.header);

        ObservableScrollable scrollView = (ObservableScrollable) findViewById(R.id.scrollview);
        scrollView.setOnScrollChangedCallback(this);

        onScroll(-1, 0);
        Picasso.with(this)
        .load(albumimg)
                .placeholder(R.drawable.album_default_cover)
                .into( mHeader);
        new Thread( new Runnable() {
            @Override
            public void run() {
                musics=new ArrayList<Music>();
                musics= MusicDao.getMusicByAlbumId(albumid);
                Message msg=new Message();
                msg.obj=musics;
                handler.sendMessage(msg);
            }
        }).start();

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent playerActivity = new Intent(MusicInAlbumActivity.this,ActivityMusicPlayer.class);
               playerActivity.setAction("ACTION_CREATE_NEW");
               playerActivity.putExtra("songurl", musiclist.get(i).getMusicurl());
               playerActivity.putExtra("songimg",musiclist.get(i).getMusicimg());
               playerActivity.putExtra("songname",musiclist.get(i).getMusicName());
               playerActivity.putExtra("music",musiclist.get(i));
               startActivity(playerActivity);
           }
       });
    }
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
         musiclist= (List<Music>) msg.obj;
        listView.setAdapter(new MyAdapter(musiclist,getApplicationContext()));
    }
};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_in_album, menu);
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

    @Override
    public void onScroll(int l, int scrollPosition) {
        int headerHeight = mHeader.getHeight() - mToolbar.getHeight();
        float ratio = 0;
        if (scrollPosition > 0 && headerHeight > 0)
            ratio = (float) Math.min(Math.max(scrollPosition, 0), headerHeight) / headerHeight;

        updateActionBarTransparency(ratio);
        updateStatusBarColor(ratio);
        updateParallaxEffect(scrollPosition);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void updateActionBarTransparency(float scrollRatio) {
        int newAlpha = (int) (scrollRatio * 255);
        mActionBarBackgroundDrawable.setAlpha(newAlpha);
        mToolbar.setBackground(mActionBarBackgroundDrawable);
    }

    private void updateStatusBarColor(float scrollRatio) {
        int r = interpolate(Color.red(mInitialStatusBarColor), Color.red(mFinalStatusBarColor), 1 - scrollRatio);
        int g = interpolate(Color.green(mInitialStatusBarColor), Color.green(mFinalStatusBarColor), 1 - scrollRatio);
        int b = interpolate(Color.blue(mInitialStatusBarColor), Color.blue(mFinalStatusBarColor), 1 - scrollRatio);
        mStatusBarManager.setTintColor(Color.rgb(r, g, b));
    }

    private void updateParallaxEffect(int scrollPosition) {
        float damping = 0.5f;
        int dampedScroll = (int) (scrollPosition * damping);
        int offset = mLastDampedScroll - dampedScroll;
        mHeader.offsetTopAndBottom(-offset);

        mLastDampedScroll = dampedScroll;
    }

    private int interpolate(int from, int to, float param) {
        return (int) (from * param + to * (1 - param));
    }
    class  MyAdapter  extends BaseAdapter{
        private List<Music>  m;
       LayoutInflater inflater;
        Context context;
        MyAdapter(List<Music> m,Context context) {
            this.m = m;
            this.inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return m.size();
        }

        @Override
        public Object getItem(int i) {
            return m.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
             if(view==null){
                 viewHolder=new ViewHolder();
                 view= inflater.inflate(R.layout.layout_item_song, null);
                 viewHolder.t1= (MusicTextView) view.findViewById(R.id.item_song_title);
                 viewHolder.t2= (MusicTextView) view.findViewById(R.id.item_song_artist);
                 view.setTag(viewHolder);
             }else {
                 viewHolder= (ViewHolder) view.getTag();
             }
            viewHolder.t1.setText(m.get(i).getMusicName());
            viewHolder.t2.setText("林俊杰");
            return view;
        }
        class  ViewHolder{
            private MusicTextView t1;
            private MusicTextView t2;

        }
    }
}

