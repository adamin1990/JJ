package lt.jsj.qust.jjlin.dao;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lt.jsj.qust.jjlin.beans.Music;
import lt.jsj.qust.jjlin.common.ParseJsonFromUrl;

/**
 * Created by adamin on 2014/12/13.
 */
public class MusicDao {
    static  String commonurl="http://s.music.163.com/search/get/?type=1&limit=10&offset=0&s=";



    public static ArrayList<Music> getMusicByAlbumId(int albumId) {
            ArrayList<Music> musics=new ArrayList<Music>();
        Music music =new Music();
            switch (albumId){
                case 1:
                    //乐行者
                    String [] yxz={"就是我","会读书","翅膀","星球","冻结","压力","女儿家","星空下的吻","让我心动的人","会有那么一天","不懂"};
                    for(int i=0;i<=yxz.length-1;i++){
                        music=getMusicByName(yxz[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;

                //第二天堂
                case 2:
                    String [] dett={"一开始","第二天堂","子弹列车","起床了","害怕","豆浆油条","江南","天使心","森林浴","距离","相信无限","精灵","美人鱼","未完成","endless road"};
                    for(int i=0;i<=dett.length-1;i++){
                        music=getMusicByName(dett[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
               //编号89757
                case 3:
                    String [] bh89757={"一千年以前","木乃伊","编号89757","莎士比亚的天份","突然累了","明天","简简单单","盗","一千年以后","无尽的思念","听不懂没关系","来不及了"};
                    for(int i=0;i<=bh89757.length-1;i++){
                        music=getMusicByName(bh89757[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
                //曹操
                case 4:
                    String [] cc={"曹操","原来","进化论","只对你说","熟能生巧","波间带","不死之身","爱情yogurt","now that shes gone","你要的不是我"};
                    for(int i=0;i<=cc.length-1;i++){
                        music=getMusicByName(cc[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
                    //西界
                case 5:
                    String [] xj={"独白 林俊杰","西界","杀手@续","无聊","单挑 林俊杰","K O 林俊杰","大男人小女孩","不流泪的机场 ","Baby Baby 林俊杰","Cries In A Distance","自由不变 林俊杰","LOVE 林俊杰"};
                    for(int i=0;i<=xj.length-1;i++){
                        music=getMusicByName(xj[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
                    //jj陆
                case 6:
                    String [] jjl={"Sixology","不潮不用花钱","小酒窝","黑武士","醉赤壁","由你选择","Always Online","街道","主角","我还想她","点一把火炬","期待爱","Cries in a Distance","爱与希望"};
                    for(int i=0;i<=jjl.length-1;i++){
                        music=getMusicByName(jjl[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
                //100天
                case 7:
                    String[] hundred={"X","第几个100天","加油!","曙光","无法克制","背对背拥抱","跟屁虫","一个又一个","爱不会绝迹","转动","妈妈的娜鲁娃","Still Moving Under Gunfire","表达爱"};
                    for(int i=0;i<=hundred.length-1;i++){
                        music=getMusicByName(hundred[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
                //shesays
                case 8:
                    String [] shesays={"她说","爱笑的眼睛","只对你有感觉","当你","一眼万年","保护色","握不住的他","心墙","我很想爱他","一生的爱","完美新世界","记得","I Am"};
                    for(int i=0;i<=shesays.length-1;i++){
                        music=getMusicByName(shesays[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
                case 9:
                    //2011 JJ林俊杰 I AM 世界巡回演唱会 小巨蛋
                    String [] j2011={"曹操","杀手","编号89757","冻结","只对你说","背对背拥抱","X","黑武士","第二天堂"
                    ,"她说","妈妈的娜鲁娃","加油","无法克制","一个又一个","I%20AM","记得","期待爱","豆浆油条","主角","街道","波间带"
                    ,"江南","小酒窝","爱笑的眼睛","不潮不用花钱","转动","一千年以后"};
                    for(int i=0;i<=j2011.length-1;i++){
                        music=getMusicByName(j2011[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
                //学不会
                case 10:
                    String [] lnf={"独奏","学不会","故事细腻","那些你很冒险的梦","白羊梦","灵魂的共鸣","We Together"
                    ,"Cinderella 林俊杰 ","白兰花","陌生老朋友","不存在的情人","LOVE U U"};
                    for(int i=0;i<=lnf.length-1;i++){
                        music=getMusicByName(lnf[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
                //因你而在
                case 11:
                String [] becauseu={"因你而在","零度的亲吻","黑暗骑士","修炼爱情","飞机","巴洛克先生","One Shot 林俊杰"
                        ,"裂缝中的阳光","友人说","十秒的冲动","以后要做的事","一千年后记得我"};
                    for(int i=0;i<=becauseu.length-1;i++){
                        music=getMusicByName(becauseu[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;
//                //iTunes Session
//                case 12:
//                    String[] itunes={"I%20Knew%20I%20Loved%20You","Falling%20Slowly","Better%20Man",
//                            "I%20Just%20Can't%20Stop%20Loving%20You","Bye%20Bye%20Bye","我笑%20(One%20Shot中文版)"};

                    //新地球
                case 12:
                    String []  newearth={"新地球","水仙","可惜没如果","回","浪漫血液","黑键","手心的蔷薇","I am alive","爱的鼓励","茉莉雨","生生 林俊杰","Lamando"};
                    for(int i=0;i<=newearth.length-1;i++){
                        music=getMusicByName(newearth[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;

                //单曲"小说 林俊杰",
                case 13:
                    String [] danqu={"流行主教","Singapore 林俊杰 ","Hurray一起摇","感动每一刻","半梦半醒之间","被风吹过的夏天",
                    "发现爱","聆听世界","唱响世界","抢玫瑰 林俊杰","我飞故我在","黑色幽默","天涯共此时 林俊杰"};
                    for(int i=0;i<=danqu.length-1;i++){
                        music=getMusicByName(danqu[i],i,albumId);
                        musics.add(music);

                    }
                    return  musics;


            }

        return  null;
    }
    public static Music getMusicByName(String musicname,int musicid,int albumId) {
        String search=commonurl+musicname;
        String json= ParseJsonFromUrl.readJSONFeed(search);
        Music music =new Music();
        try {

            JSONObject object=new JSONObject(json);
            JSONObject result=object.getJSONObject("result");
            JSONArray array=result.getJSONArray("songs");
            for(int i=0;i<array.length();i++){
                JSONObject obj=array.getJSONObject(i);
                JSONArray ja=obj.getJSONArray("artists");
                JSONObject jbb=ja.getJSONObject(0);
                //&&obj.getString("name").equals("Endless Road")
                if ((jbb.getString("name")).indexOf("林俊杰")>=0){
                   JSONObject album=obj.getJSONObject("album");
                    String pic=album.getString("picUrl");
                    String audio=obj.getString("audio");
                    music.setMusicimg(pic);
                    music.setMusicurl(audio);
                    music.setMusicName(musicname);
                    music.setMusicid(musicid);
                    music.setAlbumId(albumId);
                    return  music;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
