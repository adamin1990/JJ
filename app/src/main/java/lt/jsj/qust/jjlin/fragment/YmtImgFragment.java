package lt.jsj.qust.jjlin.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

import lt.jsj.qust.jjlin.ImageDetail;
import lt.jsj.qust.jjlin.R;
import lt.jsj.qust.jjlin.YmtDetail;
import lt.jsj.qust.jjlin.adapter.AlbumDetailAdapter;
import lt.jsj.qust.jjlin.adapter.YmtAdapter;
import lt.jsj.qust.jjlin.beans.YmtBean;
import lt.jsj.qust.jjlin.common.FindView;
import lt.jsj.qust.jjlin.dao.PictureDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class YmtImgFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener{

    private List<YmtBean> beans=new ArrayList<>();
    private int pageindex=1;
    private RecyclerView recyclerView;
    YmtAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private GridLayoutManager linearLayoutManager;
    private String url="http://www.topit.me/items/search?query=%E6%9E%97%E4%BF%8A%E6%9D%B0&p=";
    private String murl=url+pageindex;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int[] pastVisiblesItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_album2, container, false);
        recyclerView = FindView.findViewById(rootView, R.id.albumcategory);
        swipeRefreshLayout=FindView.findViewById(rootView,R.id.albumswipetorefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light), getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager=new GridLayoutManager(getActivity(),getSpanCount());
        recyclerView.setLayoutManager(linearLayoutManager);
        beans=new ArrayList<>();
        adapter=new YmtAdapter(beans,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                visibleItemCount=staggeredGridLayoutManager.getChildCount();
//                totalItemCount=staggeredGridLayoutManager.getItemCount();
//
//                int[] visibleItems = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPositions(null);
//                  int total = 0;
//                for(int i:visibleItems){
//                    total+=i;
//                }
//                if(totalItemCount-total<3) {
                int lastVisibleItem = ((LinearLayoutManager) linearLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                    pageindex += 1;
                    ContentTask task = new ContentTask(getActivity());
                    task.execute(pageindex);
                }

//                }
            }
        });
        ContentTask task=new ContentTask(getActivity());
        task.execute(pageindex);
        return rootView;
    }

    private int getSpanCount() {
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            return 3;
        }
        else {
            return 2;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        linearLayoutManager.setSpanCount(getSpanCount());
    }

    @Override
    public void onRefresh() {
//        swipeRefreshLayout.setRefreshing(true);
     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             pageindex+=1;
             ContentTask task = new ContentTask(getActivity());
             task.execute(pageindex);
         }
     },3000);
        swipeRefreshLayout.setRefreshing(false);

    }
    private class ContentTask extends AsyncTask<Integer, Integer,List<YmtBean>>{
        private Context mContext;

        public ContentTask(Context context) {
            super();
            mContext = context;
        }
        @Override
        protected List<YmtBean> doInBackground(Integer... params) {

            return PictureDao.getYmts(url+pageindex);
//            return null;
        }

        @Override
        protected void onPostExecute(List<YmtBean> ymtBeans) {
            beans.addAll(ymtBeans);
//            adapter=new YmtAdapter(beans,getActivity());
            adapter.notifyDataSetChanged();
            adapter.SetOnItemClickListener(new YmtAdapter.OnItemClickListener2() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),YmtDetail.class);
                    intent.putExtra("ymturl",beans.get(position).getMurl());
                    startActivity(intent);
                }
            });
        }
    }
    public static YmtImgFragment newInstance(int position) {
        YmtImgFragment fragment = new YmtImgFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}
