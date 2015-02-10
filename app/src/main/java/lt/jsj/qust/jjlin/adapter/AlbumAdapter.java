package lt.jsj.qust.jjlin.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lt.jsj.qust.jjlin.MusicInAlbumActivity;
import lt.jsj.qust.jjlin.R;
import lt.jsj.qust.jjlin.beans.AlbumBean;
import lt.jsj.qust.jjlin.beans.Music;
import lt.jsj.qust.jjlin.common.FindView;

/**
 * Created by adamin on 2014/12/13.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.Holder> {
   public static ArrayList<AlbumBean> albums=new ArrayList<AlbumBean>();
   public static Context context;
//    private DisplayImageOptions options = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.drawable.ic_launcher)
//            .showImageForEmptyUri(R.drawable.ic_launcher)
//            .showImageOnFail(R.drawable.ic_launcher)
//            .cacheInMemory(true)
//            .cacheOnDisk(true)
//            .considerExifParams(true)
//            .bitmapConfig(Bitmap.Config.RGB_565)
//            .build();
    public AlbumAdapter(ArrayList<AlbumBean> albums,Context context) {
        this.albums = albums;
        this.context=context;
    }
    /**
     * 点击事件接口
     */
    public interface  OnItemClickListener{
        void OnItemClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickLitener;
    public void setmOnItemClickLitener(OnItemClickListener mOnItemClickLitener){
        this.mOnItemClickLitener=mOnItemClickLitener;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
//        Picasso.with(context).load(albums.get(position).getImgUrl()).into(holder.picture, new Callback() {
//            @Override
//            public void onSuccess() {
//                //成功
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
        ImageLoader.getInstance().displayImage(albums.get(position).getImgUrl(),holder.picture);
        holder.albumname.setText(albums.get(position).getAlbumName());

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class Holder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView picture;
        TextView albumname;
            View homeview;
        public IMyViewHolderClicks mListener;

        public Holder(final View itemView) {
            super(itemView);
            this.homeview=itemView;
            picture= FindView.findViewById(itemView,R.id.cardimage);
            albumname=FindView.findViewById(itemView,R.id.cartext);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),String.valueOf(getPosition()),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.setClass(context,MusicInAlbumActivity.class);
                    intent.putExtra("albumid",albums.get(getPosition()).getId());
                    intent.putExtra("albumname",albums.get(getPosition()).getAlbumName());
                    intent.putExtra("albumpicture",albums.get(getPosition()).getImgUrl());
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            if(v instanceof ImageView){

            }
            else {
                mListener.onTomato((ImageView)v);
            }

        }

        public static interface IMyViewHolderClicks {
            public void onPotato(View caller);
            public void onTomato(ImageView callerImage);
        }
    }
}
