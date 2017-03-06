package com.liheyu.baseframe.http;

import android.content.Context;
import android.util.Log;

import com.liheyu.baseframe.utils.FileUtils;
import com.liheyu.baseframe.utils.NetworkUtls;

import java.io.IOException;
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
 * Created by lilaoda on 2016/8/30.
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

    public OkHttpClient getPubOKhttp() {
        return mOkHttpBuilder.addInterceptor(new GetParamsInterceptor()).build();
    }


    public OkHttpClient getCacheOKhttp(final Context context) {
        return mOkHttpBuilder
                .cache(new Cache(FileUtils.getCacheFile(context, "file_cache"), 1024 * 1024 * 100))
                .addInterceptor(new CacheIntercepter(context))
                .build();
    }

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
    static class GetParamsInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            HttpUrl oldUrl = oldRequest.url();
            HttpUrl newUrl = oldUrl
                    .newBuilder()
                    .scheme(oldUrl.scheme())
                    .host(oldUrl.host())
//                    .addQueryParameter(GlobeConstants.sid, account.getSid())
//                    .addQueryParameter(GlobeConstants.sign, account.getSign())
//                    .addQueryParameter(GlobeConstants.sign, Md5Utils.encode(account.getToken() + System.currentTimeMillis() + ""))
//                    .addQueryParameter(GlobeConstants.timestamp, String.valueOf(System.currentTimeMillis()))
                    .build();
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
        private static final String TAG = "request params";

        public PostParamsInterceptor() {
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request orgRequest = chain.request();
            RequestBody body = orgRequest.body();
            //收集请求参数，方便调试
            StringBuilder paramsBuilder = new StringBuilder();
            if (body != null) {
                RequestBody newBody = null;
                if (body instanceof FormBody) {
                    newBody = addParamsToFormBody((FormBody) body, paramsBuilder);
                } else if (body instanceof MultipartBody) {
                    newBody = addParamsToMultipartBody((MultipartBody) body, paramsBuilder);
                }

                if (null != newBody) {
                    Log.e(TAG, paramsBuilder.toString());
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
        private MultipartBody addParamsToMultipartBody(MultipartBody body, StringBuilder paramsBuilder) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            long temstemp = System.currentTimeMillis();
//
//            Account account = Account.getAccount();
//            builder.addFormDataPart("sid", account.getSid());
//            builder.addFormDataPart("sign", Md5Utils.encode(account.getToken() + temstemp + ""));
//            builder.addFormDataPart("timestamp", String.valueOf(temstemp));
//
//            paramsBuilder.append("sid=" + account.getSid());
//            paramsBuilder.append("&sign=" + Md5Utils.encode(account.getToken() + temstemp + ""));
//            paramsBuilder.append("&timestamp=" + String.valueOf(temstemp));
            //添加原请求体
            for (int i = 0; i < body.size(); i++) {
                builder.addPart(body.part(i));
            }
            return builder.build();
        }

        // 为FormBody类型请求体添加参数 paramsBuilder打印用
        private FormBody addParamsToFormBody(FormBody body, StringBuilder paramsBuilder) {
            FormBody.Builder builder = new FormBody.Builder();
//            Account account = Account.getAccount();
//            builder.add("sid", account.getSid());
//            paramsBuilder.append("sid=" + account.getSid());
//
//            long temstemp = System.currentTimeMillis();
//
//            builder.add("sign", Md5Utils.encode(account.getToken() + temstemp + ""));
//            paramsBuilder.append("&sign" + Md5Utils.encode(account.getToken() + temstemp + ""));
//            builder.add("timestamp", String.valueOf(temstemp));
//            paramsBuilder.append("&timestamp" + String.valueOf(temstemp));

            //添加原请求体
            for (int i = 0; i < body.size(); i++) {
                builder.addEncoded(body.encodedName(i), body.encodedValue(i));
                paramsBuilder.append("&");
                paramsBuilder.append(body.name(i));
                paramsBuilder.append("=");
                paramsBuilder.append(body.value(i));
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