package com.liheyu.baseframe.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Liheyu on 2017/3/17.
 * Email:liheyu999@163.com
 */

@Entity
public class Custom {

    @Id
    String id;
    String name;
    int age;
    String love;
    @Generated(hash = 1737562138)
    public Custom(String id, String name, int age, String love) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.love = love;
    }
    @Generated(hash = 62298964)
    public Custom() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getLove() {
        return this.love;
    }
    public void setLove(String love) {
        this.love = love;
    }
}
