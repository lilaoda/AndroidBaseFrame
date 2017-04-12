package com.liheyu.baseframe.http;

import com.liheyu.baseframe.app.BaseApplicationLike;
import com.liheyu.baseframe.http.interceptor.CacheIntercepter;
import com.liheyu.baseframe.utils.FileUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

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
                .readTimeout(READTIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS));//明文Http与比较新的Https
    }

    public static OkhttpManager getInstance() {
        if (instance == null) {
            instance = new OkhttpManager();
        }
        return instance;
    }

    /**
     * 不带缓存的Okhttp客户端
     *
     * @return
     */
    public OkHttpClient getOKhttp() {
        return mOkHttpBuilder.build();
    }

    /**
     * 带缓存的OKhttp客户羰
     *
     * @return
     */
    public OkHttpClient getCacheOKhttp() {
        return mOkHttpBuilder
                .cache(new Cache(FileUtils.getCacheFile(BaseApplicationLike.getContext(), "file_cache"), 1024 * 1024 * 100))
                .addInterceptor(new CacheIntercepter(BaseApplicationLike.getContext()))
                .build();
    }
}