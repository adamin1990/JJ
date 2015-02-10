package lt.jsj.qust.jjlin.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Adam on 2015/1/2.
 */
public class MusicTextView extends TextView {
    public MusicTextView(Context context) {
        super(context);
        setFont();
    }

    public MusicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public MusicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MusicTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
