package com.liheyu.baseframe.http;


import com.liheyu.baseframe.data.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Liheyu on 2017/3/3.
 * Email:liheyu999@163.com
 */

public interface ApiService {

    String BASE_URL = "http://h5.waqudao.com/";

    @FormUrlEncoded
    @POST("User/Public/appLogin")
    Observable<HttpResult<User>> login(@Field("mobile") String username, @Field("passwd") String passwd);
}
