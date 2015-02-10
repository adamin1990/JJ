package lt.jsj.qust.jjlin.common;

import android.app.Activity;
import android.view.View;

/**
 * Created by adamin on 2014/12/13.
 */
public class FindView {
    public static <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    public static <T extends View> T findViewById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }
}
