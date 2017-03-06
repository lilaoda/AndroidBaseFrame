package com.liheyu.baseframe.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.liheyu.baseframe.data.remote.ApiService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Liheyu on 2017/3/3.
 * Email:liheyu999@163.com
 */

public class RetrofitManager {

    private static RetrofitManager instance = null;
    private final Retrofit.Builder mRetrofitBuilder;

    private RetrofitManager() {
        mRetrofitBuilder = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static synchronized RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return mRetrofitBuilder.client(new OkHttpClient()).build();
    }

    public <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    static abstract class MyObserver<T> implements Observer<T> {


        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(T value) {
            onSuccess(value);
        }

        @Override
        public void onError(Throwable e) {
            onFailure(e);
        }

        @Override
        public void onComplete() {

        }

        abstract void onSuccess(T value);

        abstract void onFailure(Throwable e);
    }
}
