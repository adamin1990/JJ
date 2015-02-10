package lt.jsj.qust.jjlin.beans;

import java.io.Serializable;
import java.util.ArrayList;

import lt.jsj.qust.jjlin.dao.MusicDao;

/**
 * Created by adamin on 2014/12/13.
 */
public class AlbumBean implements Serializable {
    public static final long serialVersionUID = 1L;
    private String  imgUrl;
    private String albumName;
    private String publicTime;
    private int id;
    private ArrayList<Music> musics ;
    private  String desc;

    public String getCorpoate() {
        return corpoate;
    }

    public void setCorpoate(String corpoate) {
        this.corpoate = corpoate;
    }

    private String corpoate;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Music> getMusics()  {
        return MusicDao.getMusicByAlbumId(getId());
    }

    public void setMusics(ArrayList<Music> musics) {
        this.musics = musics;
    }
}
