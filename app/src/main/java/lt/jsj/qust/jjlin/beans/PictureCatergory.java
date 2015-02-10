package lt.jsj.qust.jjlin.beans;

import java.io.Serializable;

/**
 * Created by adamin on 2014/12/15.
 */
public class PictureCatergory  implements Serializable {
    public static final long serialVersionUID = 1L;
    public  String cid;
    public  String   cname;
    public String picnumber;   //收集数
    public String thumb;
    public String pages;

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPicnumber() {
        return picnumber;
    }

    public void setPicnumber(String picnumber) {
        this.picnumber = picnumber;
    }
}
