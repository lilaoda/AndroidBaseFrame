package com.liheyu.baseframe.data.bean;

/**
 * Created by Liheyu on 2017/3/2.
 * Email:liheyu999@163.com
 */

public class User {
    String password;
    String userName;

    public User(String userName,String password) {
        this.password = password;
        this.userName = userName;
    }
}
