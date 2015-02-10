package lt.jsj.qust.jjlin.beans;

import java.io.Serializable;

/**
 * Created by adamin on 2014/12/13.
 */
public class Music  implements Serializable {
    private  int albumId;
    private String musicName;
    private String musicurl;
    private String musicimg;
    private int musicid;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicurl() {
        return musicurl;
    }

    public void setMusicurl(String musicurl) {
        this.musicurl = musicurl;
    }

    public String getMusicimg() {
        return musicimg;
    }

    public void setMusicimg(String musicimg) {
        this.musicimg = musicimg;
    }

    public int getMusicid() {
        return musicid;
    }

    public void setMusicid(int musicid) {
        this.musicid = musicid;
    }
}
