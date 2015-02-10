package lt.jsj.qust.jjlin.events;

/**
 * Created by Adam on 2015/1/3.
 */
public class BusEventActivityPlayPause {
    private boolean isPlaying = false;

    public BusEventActivityPlayPause(boolean isPlaying){
        this.isPlaying = isPlaying;
    }

    public boolean isPlaying(){
        return this.isPlaying;
    }
}
