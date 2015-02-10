package lt.jsj.qust.jjlin.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Adam on 2015/1/1.
 */
public class AlbumDetailArtCover extends ImageView {
    private Context mContext;

    public AlbumDetailArtCover(Context context, AttributeSet attrs, Context mContext) {
        super(context, attrs);
        this.mContext = mContext;
        initView();
    }

    public AlbumDetailArtCover(Context context, AttributeSet attrs, int defStyleAttr, Context mContext) {
        super(context, attrs, defStyleAttr);
        this.mContext = mContext;
        initView();
    }


    public AlbumDetailArtCover(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }
    private void initView() {
        this.setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        this.setMeasuredDimension(widthMeasureSpec, screenHeight / 3);
    }
}
