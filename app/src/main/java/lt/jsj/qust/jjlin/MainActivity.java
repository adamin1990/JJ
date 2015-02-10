package lt.jsj.qust.jjlin;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.yixia.vparser.VParser;
import com.yixia.vparser.model.Video;

import java.util.ArrayList;
import java.util.List;

import lt.jsj.qust.jjlin.beans.DuitangImageBean;
import lt.jsj.qust.jjlin.beans.JJYinYueTaiMvModel;
import lt.jsj.qust.jjlin.beans.JJYinYueTaiMvWithTime;
import lt.jsj.qust.jjlin.beans.PictureCatergory;
import lt.jsj.qust.jjlin.beans.YinYueTaiMvModel;
import lt.jsj.qust.jjlin.dao.PictureDao;
import lt.jsj.qust.jjlin.dao.YinYueTaiDao;


public class MainActivity extends ActionBarActivity {
    private Toolbar bar;
    private VParser paser;
    private Video video=new Video();
    private String duitangs="http://www.duitang.com/search/?kw=%E5%8F%91%E5%9E%8B&type=feed&page=1";
    private String duitangdetail="http://www.duitang.com/people/mblog/265305178/detail/";
    //  public String url="http://mv.yinyuetai.com//all?pageType=page&sort=weekViews&page=84&tag=HyperCrystal&tab=allmv&parenttab=mv";
    private List<YinYueTaiMvModel> list=new ArrayList<YinYueTaiMvModel>();
    private List<JJYinYueTaiMvModel> mvModelLists= new ArrayList<JJYinYueTaiMvModel>();
    private List<JJYinYueTaiMvWithTime> mvsWithTime=new ArrayList<JJYinYueTaiMvWithTime>();
    private List<PictureCatergory>  pictureCatergories=new ArrayList<PictureCatergory>();
    private  List<DuitangImageBean> images=new ArrayList<DuitangImageBean>();
    //    private String url="http://mv.yinyuetai.com/all?sort=weekViews&tag=HyperCrystal";
    private String weibo="http://weibo.com/jjlin";  //微博
    private String youku="http://www.youku.com/v_olist/c_96_a__s__g__r__lg__im__st__mt__d_1_et_0_ag_0_fv_1_fl__fc__fe_1_o_10.html?from=y1.3-movie-index-10059-20090.200405-200406.0-1";
    /**
     * 视频部分
     */
    private String mvurl="http://www.yinyuetai.com/fanclub/mv/22/toTop/";    //MV
    private String allmvurl="http://www.yinyuetai.com/fanclub/mv-all/22/toNew";// 全部视频
    private String liveurl="http://www.yinyuetai.com/fanclub/mv-live/22/toNew"; //现场视频  url时间分离
    private String  concerturl="http://www.yinyuetai.com/fanclub/mv-concert/22/toNew"; //演唱会视频
    private String fantuanurl="http://www.yinyuetai.com/fanclub/mv-fan/22/toNew"; //饭团视频
    private String subtitleurl="http://www.yinyuetai.com/fanclub/mv-subtitle/22/toNew"; //字幕版

    /**
     * 资料
     */
  private String profileurl="http://www.yinyuetai.com/fanclub/data/22";
    private String surl="http://www.yinyuetai.com/fanclub/data/22";
    private String news="http://www.jjlin.com/newslist.html";
    private String mp3s="http://music.baidu.com/artist/1052";
    String youkutv="http://v.youku.com/v_show/id_XODMxNDE4MTQ0.html";
String  duitang="http://www.duitang.com/search/?kw=%E6%9E%97%E4%BF%8A%E6%9D%B0&type=album";
    String huaban="http://www.digu.com/search/boards/%E6%9E%97%E4%BF%8A%E6%9D%B0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                paser=new VParser(getApplicationContext());
//                video=paser.parse(youkutv);
//                String t=video.title;
//                String q=video.videoSiteUri;
//                String l=video.videoUri;
//                   mvModelLists=YinYueTaiDao.getJJYinYueTaiMvs(duitangs);
                images=YinYueTaiDao.getDuitangsbyPage(1);
//                pictureCatergories= PictureDao.getPicCatergory();
//                list= YinYueTaiDao.getYinYueTaiAll(url);
//                mvModelLists= YinYueTaiDao.getJJYinYueTaiMvs(allmvurl);
//                mvsWithTime=YinYueTaiDao.getJJYinYueTaiMvsWtime(youku);
//                String s=mvsWithTime.get(0).getImg();
//                String m=mvsWithTime.get(0).getOntime();
//                Log.d("", list.toString());
//                for(int i=0;i<520;i++) {
//                    System.out.println("      秋杰:++++++++++"+"+++++生日快乐！"+"@不要完美要真实"+"+++"+(i+1));
//                }
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
