package lt.jsj.qust.jjlin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.hannesdorfmann.swipeback.transformer.SlideSwipeBackTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.jsj.qust.jjlin.adapter.AlbumDetailAdapter;
import lt.jsj.qust.jjlin.beans.DuitangInfo;
import lt.jsj.qust.jjlin.common.FindView;
import lt.jsj.qust.jjlin.common.ParseJsonFromUrl;


public class AlbumActivity extends ActionBarActivity {
    public  String categoryid;
    private String categoryname;
    private static boolean isscroll=true;
    private static boolean isload=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBack.attach(this, Position.RIGHT).
        setDrawOverlay(true)
                .setDivider(getResources().getDrawable(R.drawable.ic_launcher))
                .setDividerEnabled(true) // Must be called to enable, setDivider() is not enough
                .setSwipeBackTransformer(new SlideSwipeBackTransformer())
        .setContentView(R.layout.activity_album)
        .setSwipeBackView(R.layout.swipeback_default);
        Intent intent =getIntent();
        categoryid=intent.getStringExtra("albumid");
        categoryname=intent.getStringExtra("albumname");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(categoryid,categoryname))
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{
        private String cid;
        private String cname;
        private int pageindex=1;
        private RecyclerView recyclerView;
        private List<DuitangInfo>  infos=new ArrayList<DuitangInfo>();
        private AlbumDetailAdapter albumDetailAdapter;
        private SwipeRefreshLayout swipeRefreshLayout;
        StaggeredGridLayoutManager staggeredGridLayoutManager;
     private String url= null;


        public PlaceholderFragment(String cid, String cname) {
            this.cid = cid;
            this.cname = cname;
            url = "http://www.duitang.com/album/"+cid+"/masn/p/"
                    + pageindex + "/10/";
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_album2, container, false);
            recyclerView = FindView.findViewById(rootView,R.id.albumcategory);
            swipeRefreshLayout=FindView.findViewById(rootView,R.id.albumswipetorefresh);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                    getResources().getColor(android.R.color.holo_green_light), getResources().getColor(android.R.color.holo_orange_light),
                    getResources().getColor(android.R.color.holo_red_light));
            staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            infos=new ArrayList<>();
            albumDetailAdapter=new AlbumDetailAdapter(infos,getActivity());
            recyclerView.setAdapter(albumDetailAdapter);
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(recyclerView.getScrollState()==RecyclerView.SCROLL_STATE_IDLE){
                        isscroll=false;
                    }else isscroll=true;
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                        pageindex += 1;
                        String url = "http://www.duitang.com/album/" + cid
                                + "/masn/p/" + pageindex + "/10/";
                        ContentTask task = new ContentTask(getActivity());
                        task.execute(url);

//                    }

                }
            });

            ContentTask task=new ContentTask(getActivity());
            task.execute(url);
//            if(!isload){
//                ContentTask task2=new ContentTask(getActivity());
//                task2.execute(url);
//                recyclerView.setAdapter(albumDetailAdapter);
//
//            }
            return rootView;
        }

        @Override
        public void onRefresh() {

        }


        private class ContentTask extends AsyncTask<String, Integer, List<DuitangInfo>> {

            private Context mContext;

            public ContentTask(Context context) {
                super();
                mContext = context;
            }

            @Override
            protected List<DuitangInfo> doInBackground(String... params) {
                try {
                    return parseNewsJSON(params[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<DuitangInfo> result) {
//                      infos.clear();
                infos.addAll(result);
                isload=false;
//                recyclerView.setAdapter(albumDetailAdapter);
                albumDetailAdapter.notifyDataSetChanged();

                           albumDetailAdapter.SetOnItemClickListener(new AlbumDetailAdapter.OnItemClickListener() {
            @Override
             public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(AlbumActivity.this,ImageDetail.class);
                intent.putExtra("url",infos.get(position).getIsrc());
                startActivity(intent);
               }
          });

            }

            @Override
            protected void onPreExecute() {
                isload=true;
            }

            public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
                List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
                String json = "";

                        json = ParseJsonFromUrl.readJSONFeed(url);



                try {
                    if (null != json) {
                        JSONObject newsObject = new JSONObject(json);
                        JSONObject jsonObject = newsObject.getJSONObject("data");
                        JSONArray blogsJson = jsonObject.getJSONArray("blogs");
                        for (int i = 0; i < blogsJson.length(); i++) {
                            JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
                            DuitangInfo newsInfo1 = new DuitangInfo();
                            newsInfo1.setAlbid(newsInfoLeftObject.isNull("albid") ? "": newsInfoLeftObject.getString("albid"));
                            newsInfo1.setIsrc(newsInfoLeftObject.isNull("isrc") ? "": newsInfoLeftObject.getString("isrc"));
                            newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? "": newsInfoLeftObject.getString("msg"));
                            newsInfo1.setAva(newsInfoLeftObject.isNull("ava") ? "": newsInfoLeftObject.getString("ava"));
                            duitangs.add(newsInfo1);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return duitangs;
            }
        }
    }
}
