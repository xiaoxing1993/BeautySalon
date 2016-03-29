package com.wiipu.beautysalon_new_v2.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ken~Jc on 2016/3/8.
 * 以短时间显示Toast的工具
 */
public class ShowToastUtil {
    public static void showToast(Context context,String tips){
        Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
    }
}
