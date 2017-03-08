package com.liheyu.baseframe.http;

/**
 * Created by Liheyu on 2017/3/8.
 * Email:liheyu999@163.com
 */

public class AppException extends Exception {


    public static final int codetest=001;

    public AppException() {
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }

    public AppException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AppException(Throwable throwable) {
        super(throwable);
    }
}
