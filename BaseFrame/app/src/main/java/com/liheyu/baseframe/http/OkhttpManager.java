package com.liheyu.baseframe.http;

import android.content.Context;
import android.util.Log;

import com.liheyu.baseframe.utils.FileUtils;
import com.liheyu.baseframe.utils.NetworkUtls;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp管理类，可添加缓存，添加公共请求参数
 */
public class OkhttpManager {

    private static OkhttpManager instance;
    private final OkHttpClient.Builder mOkHttpBuilder;

    private static final int CONNECTIMEOUT = 10000;
    private static final int READTIMEOUT = 10000;

    private OkhttpManager() {
        mOkHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECTIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READTIMEOUT, TimeUnit.MILLISECONDS);
    }

    public static OkhttpManager getInstance() {
        if (instance == null) {
            instance = new OkhttpManager();
        }
        return instance;
    }

    public OkHttpClient getOKhttp() {
        return mOkHttpBuilder.build();
    }


    public OkHttpClient getCacheOKhttp( Context context) {
        return mOkHttpBuilder
                .cache(new Cache(FileUtils.getCacheFile(context, "file_cache"), 1024 * 1024 * 100))
                .addInterceptor(new CacheIntercepter(context))
                .build();
    }

    /**
     * 添加缓存
     */
    static class CacheIntercepter implements Interceptor {

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

    /**
     * GET增加公共参数
     */
    private static class GetParamsInterceptor implements Interceptor {

        HashMap<String,String> paramsMap;

        public GetParamsInterceptor(HashMap<String, String> paramsMap) {
            this.paramsMap = paramsMap;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request oldRequest = chain.request();
            HttpUrl oldUrl = oldRequest.url();

            HttpUrl.Builder builder = oldUrl
                    .newBuilder()
                    .scheme(oldUrl.scheme())
                    .host(oldUrl.host());

            if (paramsMap != null) {
                Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    builder.addQueryParameter(entry.getKey(),entry.getValue());
                }
            }

            HttpUrl newUrl = builder.build();
            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(newUrl)
                    .build();
            return chain.proceed(newRequest);
        }
    }

    /**
     * POST添加公共参数
     */
    static class PostParamsInterceptor implements Interceptor {

        HashMap<String,String> paramsMap;

        public PostParamsInterceptor(HashMap<String, String> paramsMap) {
            this.paramsMap = paramsMap;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request orgRequest = chain.request();
            RequestBody body = orgRequest.body();

            if (body != null) {
                RequestBody newBody = null;
                if (body instanceof FormBody) {
                    newBody = addParamsToFormBody((FormBody) body);
                } else if (body instanceof MultipartBody) {
                    newBody = addParamsToMultipartBody((MultipartBody) body);
                }

                if (null != newBody) {
                    Request newRequest = orgRequest.newBuilder()
                            .url(orgRequest.url())
                            .method(orgRequest.method(), newBody)
                            .build();

                    return chain.proceed(newRequest);
                }
            }
            return chain.proceed(orgRequest);
        }

        // 为MultipartBody类型请求体添加参数 paramsBuilder打印用
        private MultipartBody addParamsToMultipartBody(MultipartBody body) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            if (paramsMap != null) {
                Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    builder.addFormDataPart(entry.getKey(),entry.getValue());
                }
            }

            //添加原请求体
            for (int i = 0; i < body.size(); i++) {
                builder.addPart(body.part(i));
            }
            return builder.build();
        }

        /**
         * 为FormBody类型请求体添加参数
         */
        private FormBody addParamsToFormBody(FormBody body) {
            FormBody.Builder builder = new FormBody.Builder();

            if (paramsMap != null) {
                Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    builder.add(entry.getKey(),entry.getValue());
                }
            }

            //添加原请求体
            for (int i = 0; i < body.size(); i++) {
                builder.addEncoded(body.encodedName(i), body.encodedValue(i));
            }
            return builder.build();
        }
    }

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.e("request:", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers(), request.body().toString()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.e("response:", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }
}