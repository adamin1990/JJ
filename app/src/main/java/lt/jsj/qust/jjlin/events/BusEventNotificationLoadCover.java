package lt.jsj.qust.jjlin.events;

import android.graphics.Bitmap;

/**
 * Created by Adam on 2015/1/3.
 */
public class BusEventNotificationLoadCover {
    private Bitmap mCoverPicture;

    public BusEventNotificationLoadCover(Bitmap bitmap){
        this.mCoverPicture = bitmap;
    }

    public Bitmap getCoverPicture(){
        return this.mCoverPicture;
    }
}
