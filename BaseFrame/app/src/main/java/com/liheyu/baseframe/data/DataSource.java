package com.liheyu.baseframe.data;

import com.liheyu.baseframe.data.bean.User;

/**
 * Created by Liheyu on 2017/3/2.
 * Email:liheyu999@163.com
 *
 * 全局数据源
 */

public interface DataSource {

    /**
     * 登陆结果回调接口
     */
    interface LoginCallback {

        void loginSuccess(User user);

        void loginFailed(String s);

    }
}
