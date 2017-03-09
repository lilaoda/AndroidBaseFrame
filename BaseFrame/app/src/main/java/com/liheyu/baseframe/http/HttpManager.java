package com.liheyu.baseframe.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.liheyu.baseframe.http.interceptor.ExceptionFunction;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Liheyu on 2017/3/3.
 * Email:liheyu999@163.com
 */

public class HttpManager {

    private static HttpManager instance = null;

    private Retrofit.Builder mRetrofitBuilder;
    private Retrofit mRetrofit;
    private Retrofit mCacheRetrofit;

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

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = mRetrofitBuilder.client(OkhttpManager.getInstance().getOKhttp()).build();
        }
        return mRetrofit;
    }

    public Retrofit getCacheRetrofit() {
        if (mCacheRetrofit == null) {
            mCacheRetrofit = mRetrofitBuilder.client(OkhttpManager.getInstance().getCacheOKhttp()).build();
        }
        return mCacheRetrofit;
    }

    /**
     * 统一对结果进行处理
     *
     * @param observable
     * @param <T>        结果
     * @return
     */
    public <T> Observable<T> toSurcible(Observable<HttpResult<T>> observable) {
        return observable.map(new ExceptionFunction<T>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
