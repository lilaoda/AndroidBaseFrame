package com.liheyu.baseframe.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;

import com.liheyu.baseframe.tinker.BaseApplicationContext;
import com.liheyu.baseframe.tinker.Log.MyLogImp;
import com.liheyu.baseframe.tinker.TinkerManager;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Liheyu on 2017/3/1.
 * Email:liheyu999@163.com
 */

@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.liheyu.baseframe.app.MyApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class BaseApplicationLike extends DefaultApplicationLike {


    private static BaseApplicationLike instance;
    private static Application application;
    public static int mAPPTid;
    public static Context context;

    public List<AppCompatActivity> activitys = new LinkedList<>();
    public List<Service> services = new LinkedList<>();


    public BaseApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = getApplication();
        instance = this;
        mAPPTid = android.os.Process.myTid();
        context=getApplication().getApplicationContext();
        initLeakCanary();
    }

    public static BaseApplicationLike getInstance() {
        return instance;
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        BaseApplicationContext.application = getApplication();
        BaseApplicationContext.context = getApplication();
        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    public static Context getContext() {
        return context;
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        LeakCanary.install(application);
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
                application.stopService(new Intent(application, service.getClass()));
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

























//
//
//    //此处的上下文为应用程序的上下文 伴随着程序的存在而存在 并不实际影响Java内存处理；
//
//    public List<AppCompatActivity> activitys = new LinkedList<>();
//    public List<Service> services = new LinkedList<>();
//
//    private static Context context = null;
//
//    public static Context getContext() {
//        return context;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        context = this;
//        Log.d("baseapplication", "oncreate");
//
//    }
//
//    private void initPatchManager() {
////        PatchManager patchManager = new PatchManager(this);
////        patchManager.init("1");
////        patchManager.loadPatch();
////        String path = Environment.getDataDirectory().getAbsolutePath() + "andfixtext.apatch";
////        Log.e(this.getClass().getName(), path);
////        File file = new File(path);
////        if (file.exists()) {
////            Log.e(this.getClass().getName(), "有补丁");
////            try {
////                patchManager.addPatch(path);
////                Log.e(this.getClass().getName(), "补丁成功了");
////
////            } catch (IOException e) {
////                e.printStackTrace();
////                Log.e(this.getClass().getName(), "补丁出错了");
////
////            }
////        } else {
////            Log.e(this.getClass().getName(), "没有补丁");
////
////        }
//
//    }
//
//    public void addActivity(AppCompatActivity activity) {
//        activitys.add(activity);
//    }
//
//    public void removeActivity(AppCompatActivity activity) {
//        activitys.remove(activity);
//    }
//
//    public void closeApplication() {
//        closeActivity();
//        closeService();
//    }
//
//    public void closeService() {
//        ListIterator<Service> listIterator = services.listIterator();
//        while (listIterator.hasNext()) {
//            Service service = listIterator.next();
//            if (service != null) {
//                stopService(new Intent(this, service.getClass()));
//            }
//        }
//    }
//
//    public void closeActivity() {
//        ListIterator<AppCompatActivity> listIterator = activitys.listIterator();
//        while (listIterator.hasNext()) {
//            AppCompatActivity activity = listIterator.next();
//            if (activity != null) {
//                activity.finish();
//            }
//        }
//    }
//
//    public void finishOtherActivity(Activity nowAct) {
//        ListIterator<AppCompatActivity> listIterator = activitys.listIterator();
//        while (listIterator.hasNext()) {
//            AppCompatActivity activity = listIterator.next();
//            if (activity != null && activity != nowAct) {
//                activity.finish();
//            }
//        }
//    }
}

