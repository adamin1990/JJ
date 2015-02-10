package lt.jsj.qust.jjlin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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


public class YmtDetail extends ActionBarActivity {
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
        setContentView(R.layout.activity_ymt_detail);
        Intent intent=getIntent();
        url=intent.getStringExtra("ymturl");
        rurl=url.replace("/m","/l");
        imageView= FindView.findViewById(YmtDetail.this, R.id.ymttouchimg);
//        Picasso.with(getApplicationContext()).load(rurl).into(imageView);
        ImageLoader.getInstance().displayImage(rurl,imageView);
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
        } else if (id == R.id.download) {
            Toast.makeText(getApplication(), "图片下载",
                    Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
//                    try {
//                        URL url = new URL(rurl);
//                        HttpURLConnection conn = (HttpURLConnection) url
//                                .openConnection();
//                        conn.connect();
//                        conn.setConnectTimeout(5000);
//                        InputStream in = conn.getInputStream();
//                        Bitmap map = BitmapFactory.decodeStream(in);
//                        File dirFile = new File(DOWNLOAD_PATH);
//                        if (!dirFile.exists()) {
//                            dirFile.mkdir();
//                        }
//                        Date now = new Date();
//                        fileName = time.format(now) + ".jpg";
//                        File myCaptureFile = new File(DOWNLOAD_PATH
//                                + fileName);
//                        Toast.makeText(getApplication(), "下载完成!",
//                                Toast.LENGTH_SHORT).show();
//                        BufferedOutputStream bos = new BufferedOutputStream(
//                                new FileOutputStream(myCaptureFile));
//                        map.compress(Bitmap.CompressFormat.JPEG,
//                                80, bos);
//                        bos.flush();
//                        bos.close();
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    DefaultHttpClient httpclient = new DefaultHttpClient();
                    HttpGet httpget = new HttpGet(rurl);
                    try {
                        HttpResponse resp = httpclient.execute(httpget);
                        //判断是否正确执行
                        if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
                            //将返回内容转换为bitmap
                            HttpEntity entity = resp.getEntity();
                            InputStream in = entity.getContent();
                       Bitmap     bitmap = BitmapFactory.decodeStream(in);

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
                        bitmap.compress(Bitmap.CompressFormat.JPEG,
                                80, bos);
                        bos.flush();
                        bos.close();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        setTitle(e.getMessage());
                    }
                    Looper.loop();
                }
            }).start();
            return true;

        }
        return  true;
    }
}
