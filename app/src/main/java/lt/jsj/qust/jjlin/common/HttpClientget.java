package lt.jsj.qust.jjlin.common;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by adamin on 2015/1/8.
 */
public class HttpClientget extends BaseImageDownloader{
Context context;
    public HttpClientget(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpRequest = new HttpGet(imageUri.toString());
        HttpResponse response = httpClient.execute(httpRequest);
        HttpEntity entity = response.getEntity();
        BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
        return bufHttpEntity.getContent();
    }
}
