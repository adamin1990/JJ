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
import lt.jsj.qust.jjlin.beans.PictureCatergory;
import lt.jsj.qust.jjlin.common.FindView;

/**
 * Created by adamin on 2014/12/15.
 */
public class PicCategoryAdapter extends RecyclerView.Adapter<PicCategoryAdapter.Holder> implements  View.OnClickListener{
   private Context context;
    private List<PictureCatergory> pictureCatergoryList;

    @Override
    public void onClick(View v) {

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
    public PicCategoryAdapter(Context context, List<PictureCatergory> pictureCatergoryList) {
        this.context = context;
        this.pictureCatergoryList = pictureCatergoryList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_fragment_piccat,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Picasso.with(context).load(pictureCatergoryList.get(position).getThumb()).into(holder.picthumb);
        holder.title.setText((CharSequence) pictureCatergoryList.get(position).getCname());
        holder.shoujiliang.setText("总共"+(CharSequence) pictureCatergoryList.get(position).getPicnumber()+"张");
        holder.picthumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.OnItemClick(holder.picthumb,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pictureCatergoryList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private ImageView picthumb;
        private TextView title;
        private TextView shoujiliang;
        public Holder(View itemView) {
            super(itemView);
            picthumb= FindView.findViewById(itemView,R.id.item_category_image);
            title=FindView.findViewById(itemView,R.id.item_category_text);
            shoujiliang=FindView.findViewById(itemView,R.id.shoushiliang);
        }
    }

}
