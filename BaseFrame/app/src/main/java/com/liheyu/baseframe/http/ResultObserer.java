package com.liheyu.baseframe.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Liheyu on 2017/3/6.
 * Email:liheyu999@163.com
 */

public abstract class ResultObserer<T> implements Observer<T> {
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
