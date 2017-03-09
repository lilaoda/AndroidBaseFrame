package com.liheyu.baseframe;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.squareup.leakcanary.LeakCanary;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 */

public class BaseApplication extends Application {


    //此处的上下文为应用程序的上下文 伴随着程序的存在而存在 并不实际影响Java内存处理；
    private static Context context = null;
    public static int mAPPTid;

    public List<AppCompatActivity> activitys = new LinkedList<>();
    public List<Service> services = new LinkedList<>();


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        mAPPTid = android.os.Process.myTid();

        initLeakCanary();
    }

    public static Context getContext() {
        return context;
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }


    public void addActivity(AppCompatActivity activity) {
        activitys.add(activity);
    }

    public void removeActivity(AppCompatActivity activity) {
        activitys.remove(activity);
    }

    public void closeApplication() {
        closeActivity();
        closeService();
    }

    public void closeService() {
        ListIterator<Service> listIterator = services.listIterator();
        while (listIterator.hasNext()) {
            Service service = listIterator.next();
            if (service != null) {
                stopService(new Intent(this, service.getClass()));
            }
        }
    }

    public void closeActivity() {
        ListIterator<AppCompatActivity> listIterator = activitys.listIterator();
        while (listIterator.hasNext()) {
            AppCompatActivity activity = listIterator.next();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void finishOtherActivity(Activity nowAct) {
        ListIterator<AppCompatActivity> listIterator = activitys.listIterator();
        while (listIterator.hasNext()) {
            AppCompatActivity activity = listIterator.next();
            if (activity != null && activity != nowAct) {
                activity.finish();
            }
        }
    }
}

