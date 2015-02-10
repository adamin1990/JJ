package lt.jsj.qust.jjlin.events;

/**
 * Created by Adam on 2015/1/3.
 */
public class BusEventNotificationUpdate {
    private String mStrSongName;
    private String mStrSongArtist;

    public BusEventNotificationUpdate(String strSongName, String strSongArtist){
        this.mStrSongArtist = strSongArtist;
        this.mStrSongName = strSongName;
    }

    public String getSongName(){
        return this.mStrSongName;
    }

    public String getSongArtist(){
        return this.mStrSongArtist;
    }
}
