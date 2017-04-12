package com.liheyu.baseframe.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Liheyu on 2017/3/2.
 * Email:liheyu999@163.com
 */

@Entity
public class User {

    @Id
    String userName;

    String password;

    @Transient
    String tempDes;

    @Generated(hash = 1929056389)
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
