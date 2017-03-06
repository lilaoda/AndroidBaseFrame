package com.liheyu.baseframe.data.remote;


import com.liheyu.baseframe.data.bean.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Liheyu on 2017/3/3.
 * Email:liheyu999@163.com
 */

public interface ApiService {

    String BASE_URL = "www.baidu.com";

    @GET
    Observable<HttpResult> getBaidu();
}
