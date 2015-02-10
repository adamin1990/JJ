package lt.jsj.qust.jjlin.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import lt.jsj.qust.jjlin.MusicInAlbumActivity;
import lt.jsj.qust.jjlin.R;
import lt.jsj.qust.jjlin.adapter.AlbumAdapter;
import lt.jsj.qust.jjlin.beans.AlbumBean;
import lt.jsj.qust.jjlin.beans.Music;
import lt.jsj.qust.jjlin.common.FindView;
import lt.jsj.qust.jjlin.dao.AlbumDao;
import lt.jsj.qust.jjlin.dao.MusicDao;


public class AlbumFragment extends Fragment  implements  SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private AlbumAdapter adapter;
    private ArrayList<AlbumBean> albumBeans=new ArrayList<AlbumBean>();
    private ArrayList<AlbumBean> albumBeans2;


    public static AlbumFragment newInstance(int position) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumFragment() {
        // Required empty public constructor
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
        View view=inflater.inflate(R.layout.fragment_album,container,false);
        recyclerView = FindView.findViewById(view,R.id.recyclerView);
        swipeLayout =FindView.findViewById(view,R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light), getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        albumBeans2=new ArrayList<AlbumBean>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                albumBeans2= AlbumDao.getAlbum();
                Message message=new Message();
                message.obj=albumBeans2;
                handler.sendMessage(message);
            }
        }).start();
        adapter=new AlbumAdapter(albumBeans,getActivity());
       adapter.notifyDataSetChanged();
        adapter.setmOnItemClickLitener(new AlbumAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
//                Toast.makeText(getActivity(),String.valueOf(position),Toast.LENGTH_LONG).show();
                Intent intent=new Intent();
                intent.setClass(getActivity(),MusicInAlbumActivity.class);
                intent.putExtra("albumid",albumBeans.get(position).getId());
                intent.putExtra("albumname",albumBeans.get(position).getAlbumName());
                intent.putExtra("albumpicture",albumBeans.get(position).getImgUrl());
                startActivity(intent);
            }
        });
        return view;
    }
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
       albumBeans.addAll((java.util.Collection<? extends AlbumBean>) msg.obj);

        recyclerView.setAdapter(adapter);


    }
};
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                albumBeans.addAll(albumBeans);
                adapter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        }, 5000);
    }
}
