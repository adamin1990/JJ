package lt.jsj.qust.jjlin.events;

/**
 * Created by Adam on 2015/1/3.
 */
public class BusEventMediaPlayerPrepared {
    private int mSongDuration = 0;

    public BusEventMediaPlayerPrepared(int duration){
        mSongDuration = duration;
    }

    public int getSongDuration() {
        return mSongDuration;
    }
}
