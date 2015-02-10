package lt.jsj.qust.jjlin.events;

/**
 * Created by Adam on 2015/1/3.
 */
public class BusEventNotificationShow {
    private String mStrSongName;
    private String mStrSongArtist;

    public BusEventNotificationShow(String strSongName, String strSongArtist){
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
