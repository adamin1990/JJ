package lt.jsj.qust.jjlin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import lt.jsj.qust.jjlin.common.FindView;
import lt.jsj.qust.jjlin.views.TouchImageView;


public class ImageDetail extends ActionBarActivity {
    private String url;
    private String rurl;
    private String fileName;// 图片下载后的文件名称
    private TouchImageView imageView;
    private final static String DOWNLOAD_PATH = Environment
            .getExternalStorageDirectory() + "/JJlin/download/";// 下载文件夹
    final DateFormat time = DateFormat.getDateTimeInstance(DateFormat.FULL,
            DateFormat.FULL); // 时间格式
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        int index = url.indexOf(".thumb");
        int isjpeg = url.indexOf(".png");
//		int isgif=url.indexOf(".gif");
        String s = null;
        if (isjpeg == -1) {
            s = url.substring(index, url.length() - 5);
        } else {
            s = url.substring(index, url.length() - 4);
        }

        rurl = url.replaceAll(s, "");
        imageView= FindView.findViewById(ImageDetail.this,R.id.touchimg);
        Picasso.with(getApplicationContext()).load(rurl).into(imageView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);
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
        }else if(id==R.id.download){
            Toast.makeText(getApplication(), "图片下载",
                    Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        URL url = new URL(rurl);
                        HttpURLConnection conn = (HttpURLConnection) url
                                .openConnection();
                        conn.connect();
                        conn.setConnectTimeout(5000);
                        InputStream in = conn.getInputStream();
                        Bitmap map = BitmapFactory.decodeStream(in);
                        File dirFile = new File(DOWNLOAD_PATH);
                        if (!dirFile.exists()) {
                            dirFile.mkdir();
                        }
                        Date now = new Date();
                        fileName = time.format(now) + ".jpg";
                        File myCaptureFile = new File(DOWNLOAD_PATH
                                + fileName);
                        Toast.makeText(getApplication(), "下载完成!",
                                Toast.LENGTH_SHORT).show();
                        BufferedOutputStream bos = new BufferedOutputStream(
                                new FileOutputStream(myCaptureFile));
                        map.compress(Bitmap.CompressFormat.JPEG,
                                80, bos);
                        bos.flush();
                        bos.close();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            }).start();
            return  true;
        }


        return super.onOptionsItemSelected(item);
    }
}
