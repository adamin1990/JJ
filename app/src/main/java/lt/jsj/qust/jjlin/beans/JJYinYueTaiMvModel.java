package lt.jsj.qust.jjlin.beans;

import java.io.Serializable;

/**
 * Created by Adam on 2014/12/7.
 */
public class JJYinYueTaiMvModel implements Serializable {
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
     * 播放次数
     */
    private String times;

    /**
     * 视频页数
     *
     */
    private String pages;

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getHref() {
        return "http://v.yinyuetai.com/"+href;
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

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "JJYinYueTaiMvModel{" +
                "href='" + href + '\'' +
                ", RealHref='" + RealHref + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", shdIco='" + shdIco + '\'' +
                ", v_time_num='" + v_time_num + '\'' +
                ", man='" + man + '\'' +
                ", times='" + times + '\'' +
                ", pages='" + pages + '\'' +
                '}';
        /**
         * {href='/video/3023',
         * RealHref='null',
         * img='http://img4.yytcdn.com/uploads/artists/22/f7319e6044f84caa8a38e08c2e378677.jpg',
         * title='江南',
         * shdIco='null',
         * v_time_num='null',
         * man='林俊杰',
         * times='播放927500次',
         * pages='8'}
         */
    }
}
