package com.liheyu.baseframe.http.interceptor;

import android.content.Context;
import android.util.Log;

import com.liheyu.baseframe.utils.NetworkUtls;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Liheyu on 2017/3/9.
 * Email:liheyu999@163.com
 * 缓存拦截器 添加缓存 只针对GET有效
 */
public class CacheIntercepter implements Interceptor {

    private Context context;

    public CacheIntercepter(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtls.isConnceted(context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        } else {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);
        if (NetworkUtls.isConnceted(context)) {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .addHeader("Chche-Control", "public,max-age=10")
                    .build();
            Log.e("reponse", "有网");
        } else {
            long maxage = 3600 * 24 * 4;//4天
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .addHeader("Chche-Control", "public,only-if-cache,max-stale=" + maxage)
                    .build();
            Log.e("reponse", "无网");
        }
        return response;
    }
}