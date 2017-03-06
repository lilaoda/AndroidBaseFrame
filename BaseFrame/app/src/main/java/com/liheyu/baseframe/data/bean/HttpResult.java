package com.liheyu.baseframe.data.bean;

/**
 * Created by Liheyu on 2017/3/6.
 * Email:liheyu999@163.com
 */

public class HttpResult<T> {
    public int code;
    public String message;
    public T data;
}
