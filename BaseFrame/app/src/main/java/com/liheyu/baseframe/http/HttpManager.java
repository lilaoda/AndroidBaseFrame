package com.liheyu.baseframe.http;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Liheyu on 2017/3/3.
 * Email:liheyu999@163.com
 */

public class HttpManager {

    private static HttpManager instance = null;

    private  Retrofit.Builder mRetrofitBuilder;
    private ApiService mApiService;
    private ApiService mCacheApiService;

    private HttpManager() {
        mRetrofitBuilder = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static synchronized HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public ApiService getService() {
        if (mApiService == null) {
            mApiService = mRetrofitBuilder
                    .client(OkhttpManager.getInstance().getOKhttp())
                    .build()
                    .create(ApiService.class);
        }
        return mApiService;
    }

    public ApiService getCacheService(Context context) {
        if (mCacheApiService == null) {
            mCacheApiService = mRetrofitBuilder.client(OkhttpManager.getInstance().getCacheOKhttp(context))
                    .build()
                    .create(ApiService.class);
        }
        return mCacheApiService;
    }

    /**
     *  可在此统一对结果进行处理
     * @param observable
     * @param <T> 结果
     * @return
     */
    public  <T> Observable<T> toSurcible(Observable<HttpResult<T>> observable) {
        return observable.map(new Function<HttpResult<T>, T>() {
            @Override
            public T apply(HttpResult<T> tHttpResult) throws Exception {
                if (tHttpResult.status != 1) {
                    throw new AppException(tHttpResult.info);
                }
                return tHttpResult.data;
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
