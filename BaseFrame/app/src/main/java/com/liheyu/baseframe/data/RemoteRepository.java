package com.liheyu.baseframe.data;

import android.support.annotation.NonNull;

import com.liheyu.baseframe.data.bean.User;

/**
 * Created by Liheyu on 2017/3/2.
 * Email:liheyu999@163.com
 * <p>
 * 网络数据仓库 执行网络请求获取结果
 */

public class RemoteRepository {

    private static RemoteRepository INSTANCE = null;

    private RemoteRepository() {
    }

    public static RemoteRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRepository();
        }
        return INSTANCE;
    }

    public void login(String userName, String password, @NonNull DataSource.LoginCallback callback) {
        if (userName.equals("liheyu") && password.equals("123456")) {
            User user = new User(userName, password);
            callback.loginSuccess(user);
        } else {
            callback.loginFailed("登陆失败了...");
        }
    }
}
