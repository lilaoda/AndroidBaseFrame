package com.liheyu.baseframe.http.interceptor;

import com.liheyu.baseframe.http.ApiException;
import com.liheyu.baseframe.http.HttpResult;

import io.reactivex.functions.Function;

/**
 * Created by Liheyu on 2017/3/9.
 * Email:liheyu999@163.com
 * APP数据接口异常处理
 */

public class ExceptionFunction<T> implements Function<HttpResult<T>, T> {

    @Override
    public T apply(HttpResult<T> tHttpResult) throws Exception {

        //如果请求数据不成功，就抛异常走onError返回服务器返回的信息
        if (tHttpResult.status != 1) {
            throw new ApiException(tHttpResult.info);
        }

        return tHttpResult.data;
    }
}
