package lt.jsj.qust.jjlin.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lt.jsj.qust.jjlin.AlbumActivity;
import lt.jsj.qust.jjlin.R;
import lt.jsj.qust.jjlin.adapter.PicCategoryAdapter;
import lt.jsj.qust.jjlin.beans.PictureCatergory;
import lt.jsj.qust.jjlin.common.FindView;
import lt.jsj.qust.jjlin.dao.PictureDao;


public class Pic_CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
   private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<PictureCatergory> pictureCatergoryList=new ArrayList<PictureCatergory>();
    private PicCategoryAdapter picCategoryAdapter;

    StaggeredGridLayoutManager staggeredGridLayoutManager;
   private static String ARG_POSITION;
    public Pic_CategoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_pic__category,container,false);
        recyclerView= FindView.findViewById(view,R.id.piccatergoryrecycyleview);
        swipeRefreshLayout=FindView.findViewById(view,R.id.piccatswipetorefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light), getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
          new Thread(new Runnable() {
              @Override
              public void run() {
                  pictureCatergoryList= PictureDao.getPicCatergory();
                  Message message =new Message();
                  message.obj=pictureCatergoryList;
                  handler.sendMessage(message);

              }
          }).start();

        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        return view;
    }

Handler handler =new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        pictureCatergoryList= (List<PictureCatergory>) msg.obj;
        picCategoryAdapter=new PicCategoryAdapter(getActivity(),pictureCatergoryList);
        picCategoryAdapter.notifyDataSetChanged();
        picCategoryAdapter.setmOnItemClickLitener(new PicCategoryAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent =new Intent();
                intent.setClass(getActivity(),AlbumActivity.class);
                intent.putExtra("albumid",pictureCatergoryList.get(position).getCid());
                intent.putExtra("albumname",pictureCatergoryList.get(position).getCname());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(picCategoryAdapter);
    }
};
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 5000);
    }
    public static Pic_CategoryFragment newInstance(int position) {
        Pic_CategoryFragment f = new Pic_CategoryFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }
}
