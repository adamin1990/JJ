package lt.jsj.qust.jjlin.beans;

import java.io.Serializable;

/**
 * Created by Adam on 2014/12/7.
 */
public class YinYueTaiPageModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 音悦台的域名
     */
    private String pageStart;
    /**
     * 选择页的链接头部
     */
    private String pageHead;
    /**
     * 选择页的链接尾部
     */
    private String pageEnd;
    /**
     * 选择页的数目
     */
    private int pageNum;

    public String getPageStart() {
        return pageStart;
    }
    public void setPageStart(String pageStart) {
        this.pageStart = pageStart;
    }
    public String getPageHead() {
        return pageHead;
    }
    public void setPageHead(String pageHead) {
        this.pageHead = pageHead;
    }
    public String getPageEnd() {
        return pageEnd;
    }
    public void setPageEnd(String pageEnd) {
        this.pageEnd = pageEnd;
    }
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
