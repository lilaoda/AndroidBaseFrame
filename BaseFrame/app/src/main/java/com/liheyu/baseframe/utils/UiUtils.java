package com.liheyu.baseframe.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


/**
 * Created by lilaoda on 2016/8/23.
 */
public class UiUtils {

    private UiUtils() {

    }

    /**
     * 获取Rescources
     *
     * @return
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * dp too px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        float density = getResources(context).getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px too dp
     *
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        float density = getResources(context).getDisplayMetrics().density;
        return (int) (px * density + 0.5f);
    }


    /**
     * 得到系统当前时间：时间格式 2016-08-15 12：00：00
     *
     * @return
     */
    public static CharSequence getCurrentTime() {
        return android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date());
    }

    /**
     * 移除view的父容器
     *
     * @param view
     */
    public static void removerParent(View view) {
        ViewParent parent = view.getParent();
        //当fragment销毁后通过反射调用无参construction创建新的fragment实例时，parent为空
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    public static String getStringfromStream(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        try {
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static String getString(TextView v) {
        return v.getText().toString().trim();
    }

    public static String getString(EditText v) {
        return v.getText().toString().trim();
    }


    /**
     * 重新计算Listview高度，解决scrollview嵌套冲突
     *
     * @param listView
     */
    public static void setListViewHeightByChild(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
