package lt.jsj.qust.jjlin.beans;

import java.io.Serializable;

/**
 * Created by Adam on 2014/12/7.
 */
public class YinYueTaiMvModel implements Serializable{
    public static final long serialVersionUID = 1L;
    /**
     * 网页上的视频链接
     */
    private String href;
    /**
     * 解析后视频真正链接
     */
    private String RealHref;
    /**
     * 视频图片
     */
    private String img;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频清晰度
     */
    private String shdIco;
    /**
     * 视频的时间
     */
    private String v_time_num;
    /**
     * 视频艺人
     */
    private String man;
    /**
     * 视频描述
     */
    private String description;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRealHref() {
        return RealHref;
    }

    public void setRealHref(String realHref) {
        RealHref = realHref;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShdIco() {
        return shdIco;
    }

    public void setShdIco(String shdIco) {
        this.shdIco = shdIco;
    }

    public String getV_time_num() {
        return v_time_num;
    }

    public void setV_time_num(String v_time_num) {
        this.v_time_num = v_time_num;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "YinYueTaiMvModel{" +
                "href='" + href + '\'' +
                ", RealHref='" + RealHref + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", shdIco='" + shdIco + '\'' +
                ", v_time_num='" + v_time_num + '\'' +
                ", man='" + man + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
