package com.liheyu.baseframe;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    <R>LifecycleTransformer<R> getLifeTransformer();
}
