package com.liheyu.baseframe.contract;

import com.liheyu.baseframe.BasePresenter;
import com.liheyu.baseframe.BaseView;
import com.liheyu.baseframe.data.bean.User;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 * <p>
 * 登陆页面 view presenter做的事情  便于管理
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void loginSuccess(User user);

        void loginFailure(String string);

    }

    interface Presenter extends BasePresenter {

        void login(String userName, String password);

    }
}
