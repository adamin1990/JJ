package lt.jsj.qust.jjlin.dao;

import android.print.PrintDocumentInfo;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.jsj.qust.jjlin.beans.DuitangImageBean;
import lt.jsj.qust.jjlin.beans.JJYinYueTaiMvModel;
import lt.jsj.qust.jjlin.beans.JJYinYueTaiMvWithTime;
import lt.jsj.qust.jjlin.beans.YinYueTaiMvModel;
import lt.jsj.qust.jjlin.beans.YinYueTaiPageModel;

/**
 * Created by Adam on 2014/12/7.
 */
public class YinYueTaiDao {
    public static ArrayList<YinYueTaiMvModel> getYinYueTaiMvs(String  url){
       ArrayList<YinYueTaiMvModel> models=new ArrayList<YinYueTaiMvModel>();
        try {
            Document doc= Jsoup.connect(url).get();
            Element mv_list_vertical = doc.getElementById("mvlist");
            Elements mv_lists = mv_list_vertical.select("li");
            System.out.println(mv_lists.size());
            for (Element mv : mv_lists) {
                YinYueTaiMvModel yinYueTaiModel = new YinYueTaiMvModel();
                Element thumb_mv = mv.select("div.thumb_mv").first();
                Element href_a = thumb_mv.getElementsByTag("a").first();
                yinYueTaiModel.setHref(href_a.attr("href"));
                Element img = href_a.getElementsByTag("img").first();
                yinYueTaiModel.setImg(img.attr("src"));
                yinYueTaiModel.setTitle(img.attr("title"));
                yinYueTaiModel.setShdIco(thumb_mv.select("div.shdIco").first().text());
                yinYueTaiModel.setV_time_num(thumb_mv.select("div.v_time_num").text());
                Element info = mv.select("div.info").first();
                yinYueTaiModel.setMan(info.select("a.name").first().text());
                yinYueTaiModel.setDescription(info.select("p.description").first().text());
                models.add(yinYueTaiModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  models;
    }
    /**
     * 获取当前选择的TV类型的页数
     */
    public  static YinYueTaiPageModel getYinYueTaiPage(String url){
        YinYueTaiPageModel yinYueTaiPageModel = new YinYueTaiPageModel();
        try {
            Document doc = Jsoup.connect(url).get();
            Element page_nav = doc.getElementById("pageNav");  //这个是所有的视频
//            Element page_nav = doc.
            Element onePage = page_nav.getElementsByTag("a").first();
            String href = onePage.attr("href");
            if ("http://mv.yinyuetai.com/".indexOf(href) == -1) {
                yinYueTaiPageModel.setPageStart("http://mv.yinyuetai.com/");
            }else {
                yinYueTaiPageModel.setPageStart("");
            }
            String[] temp = href.split(onePage.text());
            yinYueTaiPageModel.setPageHead(temp[0]);
            yinYueTaiPageModel.setPageEnd(temp[1]);
            Elements Pages = page_nav.select("span.separator");
            for (Element element : Pages) {
                if (element.text().indexOf("页") != -1) {
                    StringBuilder pages = new StringBuilder();
                    pages.append(element.text());
                    String numString = pages.substring(1, pages.length()-1);
                    try {
                        yinYueTaiPageModel.setPageNum(Integer.parseInt(numString));
                    } catch (Exception e) {
                        yinYueTaiPageModel.setPageNum(0);
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return yinYueTaiPageModel;
    }
    /**
     * 解析所有的链接的TV视频
     */
    public static ArrayList<YinYueTaiMvModel> getYinYueTaiAll(String url){
        ArrayList<YinYueTaiMvModel> listData = new ArrayList<YinYueTaiMvModel>();
        try {
            YinYueTaiPageModel pageModel = getYinYueTaiPage(url);
            int count = pageModel.getPageNum();
            for (int i = 1; i <= count; i++) {
                StringBuilder builder = new StringBuilder();
                builder.append(pageModel.getPageStart()+pageModel.getPageHead()+i+pageModel.getPageEnd());
                System.out.println(builder.toString());
                try {
                    Document doc = Jsoup.connect(builder.toString()).get();
                    Element mv_list_vertical = doc.getElementById("mvlist");
                    Elements mv_lists = mv_list_vertical.select("li");
                    for (Element mv : mv_lists) {
                        YinYueTaiMvModel yinYueTaiModel = new YinYueTaiMvModel();
                        Element thumb_mv = mv.select("div.thumb_mv").first();
                        Element href_a = thumb_mv.getElementsByTag("a").first();
                        yinYueTaiModel.setHref(href_a.attr("href"));
                        Element img = href_a.getElementsByTag("img").first();
                        yinYueTaiModel.setImg(img.attr("src"));
                        yinYueTaiModel.setTitle(img.attr("title"));
                        Elements shdIcos = thumb_mv.select("div.shdIco");
                        if (shdIcos != null && shdIcos.size() > 0) {
                            yinYueTaiModel.setShdIco(shdIcos.first().text());
                        }else {
                            shdIcos = thumb_mv.select("div.hdIco");
                            if (shdIcos != null && shdIcos.size() > 0) {
                                yinYueTaiModel.setShdIco(shdIcos.first().text());
                            }else {
                                yinYueTaiModel.setShdIco("普清");
                            }
                        }
                        yinYueTaiModel.setV_time_num(thumb_mv.select("div.v_time_num").text());
                        Element info = mv.select("div.info").first();
                        yinYueTaiModel.setMan(info.select("a.name").first().text());
                        yinYueTaiModel.setDescription(info.select("p.description").first().text());
                        System.out.println(yinYueTaiModel.toString());
                        listData.add(yinYueTaiModel);
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    public static ArrayList<JJYinYueTaiMvModel> getJJYinYueTaiMvs(String  url){
        ArrayList<JJYinYueTaiMvModel> models=new ArrayList<JJYinYueTaiMvModel>();
        try {
            Document doc= Jsoup.connect(url).timeout(10000).get();
            Element mv_pages=doc.select("span.separator").last();
            StringBuilder pages = new StringBuilder();
            pages.append(mv_pages.text());
            String numString = pages.substring(1, pages.length()-1);

            Element mv_list_vertical = doc.select("div[class=mv_list]").first();
            Elements mv_lists = mv_list_vertical.select("li");
            System.out.println(mv_lists.size());
            for (Element mv : mv_lists) {
                JJYinYueTaiMvModel yinYueTaiModel = new JJYinYueTaiMvModel();
                Element thumb_mv = mv.select("div.thumb").first();
                Element href_a = thumb_mv.getElementsByTag("a").first();
                yinYueTaiModel.setHref(href_a.attr("href"));
                yinYueTaiModel.setTitle(href_a.attr("title"));
                Element img = href_a.getElementsByTag("img").first();
                yinYueTaiModel.setImg(img.attr("src"));
                Element info = mv.select("div.title").get(1);
                Element t=info.select("a").first();
                yinYueTaiModel.setMan(t.attr("title"));
                Element time=mv.select("div.title").last();
                yinYueTaiModel.setTimes(time.text());
                yinYueTaiModel.setPages(numString);
                models.add(yinYueTaiModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  models;
    }
    /**
     * 获取图片后带时间的MV
     */
    public static ArrayList<JJYinYueTaiMvWithTime> getJJYinYueTaiMvsWtime(String  url){
        ArrayList<JJYinYueTaiMvWithTime> models=new ArrayList<JJYinYueTaiMvWithTime>();
        try {
            Document doc= Jsoup.connect(url).get();
            Element mv_pages=doc.select("span.separator").last();
            StringBuilder pages = new StringBuilder();
            pages.append(mv_pages.text());
            String numString = pages.substring(1, pages.length()-1);

            Element mv_list_vertical = doc.select("div[class=mv_list]").first();
            Elements mv_lists = mv_list_vertical.select("li");
            System.out.println(mv_lists.size());
            for (Element mv : mv_lists) {
                JJYinYueTaiMvWithTime yinYueTaiModel = new JJYinYueTaiMvWithTime();
                Element thumb_mv = mv.select("div.thumb").first();
                Element href_a = thumb_mv.getElementsByTag("a").first();
                yinYueTaiModel.setHref(href_a.attr("href"));
                yinYueTaiModel.setTitle(href_a.attr("title"));
                Element img = href_a.getElementsByTag("img").first();
                yinYueTaiModel.setImg(img.attr("src"));
                Element info = mv.select("div.title").get(1);
                Element t=info.select("a").first();
                yinYueTaiModel.setMan(t.attr("title"));
                Element time=mv.select("div.title").last();
                yinYueTaiModel.setTimes(time.text());
                yinYueTaiModel.setPages(numString);
                models.add(yinYueTaiModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  models;
    }
    public static  ArrayList<DuitangImageBean> getDuitangsbyPage(int page){
        String  url="http://www.duitang.com/search/?kw=%E5%8F%91%E5%9E%8B&type=feed&page="+page;
         ArrayList<DuitangImageBean>  images =new ArrayList<DuitangImageBean>();
        try {
            Document doc=Jsoup.connect(url).timeout(10000).get();
            Element element0=doc.select("div[class=pbr woo-mpbr]").last();
            Element element00=element0.select("li[class=pbrw]").last();
            Element element000=element00.select("span").first();

            String pages=element000.text();
            Elements htmlimages=doc.select("div[class=woo]");
            for(Element e:htmlimages){
                DuitangImageBean imageBean=new DuitangImageBean();
                imageBean.setPages(pages);
                Element element1=e.select("div[class=mbpho]").first();
                Element element2=element1.getElementsByTag("a").first();
                Element element3=element2.getElementsByTag("img").first();
                imageBean.setId(element3.attr("data-rootid"));
                imageBean.setDesc(element3.attr("alt"));
                imageBean.setThumburl(element3.attr("src"));
                images.add(imageBean);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  images;
    }
}

