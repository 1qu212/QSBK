package msz.myapplication.okhttp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import msz.myapplication.util.NetworkUtil;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Title: MyCaheInterceptor
 * @Package msz.myapplication.okhttp
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/29 1:24
 */
public class MyCaheInterceptor implements Interceptor {

    private Context context;
    public MyCaheInterceptor(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtil.isNetworkAvailable(context)) {
            Response response = chain.proceed(request);
            // read from cache for 60 s
            int maxAge = 300;
            String cacheControl = request.cacheControl().toString();
            Log.e("Tamic", maxAge+ "s load cahe:" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            Log.e("Tamic", " no network load cahe");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            //set cahe times is 3 days
            int maxStale = 60 * 60 * 24 * 3;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }

}