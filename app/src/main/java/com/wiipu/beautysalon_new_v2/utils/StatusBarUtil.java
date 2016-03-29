package com.wiipu.beautysalon_new_v2.utils;

import android.app.Activity;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wiipu.beautysalon_new_v2.R;

/**
 * * 设置状态栏的工具类
 * Created by Ken~Jc on 2016/3/11.
 */
public class StatusBarUtil {

    /**
     * 设置灰色沉浸式状态栏
     * @param activity
     */
    public static final void setTransparentStatusBar(Activity activity){
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(activity.getResources().getColor(R.color.gray));
    }

}
