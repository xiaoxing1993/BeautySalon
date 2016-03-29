package com.wiipu.beautysalon_new_v2.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.wiipu.beautysalon_new_v2.network.NetworkManager;

/**
 * Created by Ken~Jc on 2016/3/15.
 * 程序入口
 */
public class MyApplication extends Application{

    static {
        application=new MyApplication();
    }

    /**
     * 静态的全局对象
     */
    private static MyApplication application;

    /**
     * @return 返回一个application
     */
    public MyApplication getInstance() {
        return application;
    }

    public static void init() {

    }

    @Override
    public void onCreate(){
        super.onCreate();

        // 初始化网络请求队列
        NetworkManager.getInstance().init(getApplicationContext());
    }

}
