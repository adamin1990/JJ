package lt.jsj.qust.jjlin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import lt.jsj.qust.jjlin.R;
import lt.jsj.qust.jjlin.beans.YmtBean;
import lt.jsj.qust.jjlin.common.FindView;
import lt.jsj.qust.jjlin.common.HttpClientget;

/**
 * Created by Adam on 2015/1/7.
 */
public class YmtAdapter extends RecyclerView.Adapter<YmtAdapter.Holder> {
    private List<YmtBean> beans;
    private Context context;
   private DisplayImageOptions options = new DisplayImageOptions.Builder()
           .showImageOnLoading(R.drawable.ic_launcher)
           .showImageForEmptyUri(R.drawable.ic_launcher)
           .showImageOnFail(R.drawable.ic_launcher)
           .cacheInMemory(true)
           .cacheOnDisk(true)
           .considerExifParams(true)
           .bitmapConfig(Bitmap.Config.RGB_565)
           .build();
    // Initialize ImageLoader with configuration.

    public interface OnItemClickListener2{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener2 itemClickListener;
    public  void SetOnItemClickListener (OnItemClickListener2 onItemClickListener){
        this.itemClickListener=onItemClickListener;
    }

    public YmtAdapter(List<YmtBean> beans, Context context) {
        this.beans = beans;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ymt_item_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        ImageLoader.getInstance().displayImage(beans.get(position).getMurl(),holder.imageView,options);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(holder.itemView,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    class Holder extends  RecyclerView.ViewHolder{
        private ImageView imageView;
//        private TextView textView;
        public Holder(View itemView) {
            super(itemView);
            imageView= FindView.findViewById(itemView, R.id.ymtcardimage);
//            textView=FindView.findViewById(itemView,R.id.ymtcartext);
        }
    }
}
