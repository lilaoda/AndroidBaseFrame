package com.liheyu.baseframe.data.remote;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Liheyu on 2017/3/3.
 * Email:liheyu999@163.com
 */

public interface ApiService {

    String BASE_URL = "www.baidu.com";

    @GET
    Observable<String> getBaidu(@Url String url);
}
