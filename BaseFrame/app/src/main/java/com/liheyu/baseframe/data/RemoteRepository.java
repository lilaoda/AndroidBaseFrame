package com.liheyu.baseframe.data;

import com.liheyu.baseframe.data.bean.User;
import com.liheyu.baseframe.http.ApiService;
import com.liheyu.baseframe.http.HttpManager;

import io.reactivex.Observable;

/**
 * Created by Liheyu on 2017/3/2.
 * Email:liheyu999@163.com
 * <p>
 * 网络数据仓库 主要功能获取相应的要求的observable
 */

public class RemoteRepository {

    private static RemoteRepository INSTANCE = null;
    private ApiService mApiService;
    private final HttpManager mHttpManager;

    private RemoteRepository() {
        mHttpManager = HttpManager.getInstance();
        mApiService = mHttpManager.getRetrofit().create(ApiService.class);
    }

    public static RemoteRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRepository();
        }
        return INSTANCE;
    }

    public Observable<User> login(String userName, String password) {
        return mHttpManager.toSurcible(mApiService.login(userName, password));
    }
}
