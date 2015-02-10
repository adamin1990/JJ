package lt.jsj.qust.jjlin.dao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lt.jsj.qust.jjlin.beans.PictureCatergory;
import lt.jsj.qust.jjlin.beans.YmtBean;

/**
 * Created by adamin on 2014/12/15.
 * fetch all the category of lamjj
 */
public class PictureDao {
    public static List<PictureCatergory> getPicCatergory(){
        String url="http://www.duitang.com/search/?kw=%E6%9E%97%E4%BF%8A%E6%9D%B0&type=album";
      List<PictureCatergory>  pictureCatergories=new ArrayList<PictureCatergory>();
        String numString;
        try {
            Document doc= Jsoup.connect(url).timeout(10000).get();
            Element page=doc.select("span").last();
            StringBuilder pages = new StringBuilder();
            pages.append(page.text());
           numString = pages.substring(0, pages.length());   //页数
            Element element=doc.getElementsByAttributeValueContaining("class","woo-pcont nomyname stalbums clr ").first();
            Elements elements=element.getElementsByClass("woo");
            for (int i=0;i<elements.size();i++){
                PictureCatergory pictureCatergory=new PictureCatergory();
                Element element1=elements.get(i);
                pictureCatergory.setCid(element1.attr("data-id"));
                Element albbigimg=element1.getElementsByClass("albbigimg").first();
                Element lev0=albbigimg.select("a").first();
                Element img=lev0.getElementsByTag("img").first();
                pictureCatergory.setCname(img.attr("alt"));
                pictureCatergory.setThumb(img.attr("src"));
                Element collect=element1.getElementsByTag("ul").first();
                Element collectnum=collect.select("li").first();
                Element spancollectnum=collectnum.select("span").first();
                String text=spancollectnum.text();
                int index=text.lastIndexOf("个收集");
                String numberofcollect=text.substring(0,index);
                pictureCatergory.setPicnumber(numberofcollect);
                pictureCatergory.setPages(numString);
                pictureCatergories.add(pictureCatergory);



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  pictureCatergories;
    }
    public static  ArrayList<YmtBean>  getYmts(String url){
//            String url="http://www.topit.me/items/search?query=%E6%9E%97%E4%BF%8A%E6%9D%B0&p=1";
            ArrayList<YmtBean>  images =new ArrayList<YmtBean>();
            Document doc;
            try {

                doc = Jsoup.connect(url).timeout(10000).get();
                Element catalog=doc.select("div[class=catalog]").first();
                Elements  ems=catalog.select("div[class=e m]");
                for(Element e:ems){
                    YmtBean  ymtBean=new YmtBean();
                    Element ymt=e.getElementsByTag("a").last();
                    Element img=ymt.select("img[class=img]").first();
                    if(img.attr("data-original").equals("")) {
                        ymtBean.setMurl(img.attr("src"));
                    }
                    else {
                        ymtBean.setMurl(img.attr("data-original"));
                    }
                    ymtBean.setTitle(img.attr("alt"));
                    images.add(ymtBean);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return  images;
    }
}
