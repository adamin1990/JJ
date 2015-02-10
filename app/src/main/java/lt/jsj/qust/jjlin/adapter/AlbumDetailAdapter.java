package lt.jsj.qust.jjlin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import lt.jsj.qust.jjlin.R;
import lt.jsj.qust.jjlin.beans.DuitangInfo;
import lt.jsj.qust.jjlin.common.FindView;

/**
 * Created by adamin on 2014/12/19.
 */
public class AlbumDetailAdapter extends RecyclerView.Adapter<AlbumDetailAdapter.VHolder> {
    private List<DuitangInfo> duitangInfoList;
    private Context context;
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener itemClickListener;
    public  void SetOnItemClickListener (OnItemClickListener onItemClickListener){
        this.itemClickListener=onItemClickListener;
    }
    public AlbumDetailAdapter(List<DuitangInfo> duitangInfoList, Context context) {
        this.duitangInfoList = duitangInfoList;
        this.context = context;
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item,parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(final VHolder holder, final int position) {
        Picasso.with(context).load(duitangInfoList.get(position).getIsrc()).into(holder.imageView);
        holder.textView.setText(duitangInfoList.get(position).getMsg());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(holder.imageView,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return duitangInfoList.size();
    }

    public class VHolder extends  RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView  textView;
        public VHolder(View itemView) {
            super(itemView);
            imageView= FindView.findViewById(itemView,R.id.cardimage);
            textView=FindView.findViewById(itemView,R.id.cartext);
        }
    }
}
