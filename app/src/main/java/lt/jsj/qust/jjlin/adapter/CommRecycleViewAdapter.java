package lt.jsj.qust.jjlin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by adamin on 2014/12/15.
 */
public class CommRecycleViewAdapter extends RecyclerView.Adapter<CommRecycleViewAdapter.CommonHolder> {
    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CommonHolder extends RecyclerView.ViewHolder {
        public CommonHolder(View itemView) {
            super(itemView);
        }
    }
}
