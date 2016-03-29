package com.wiipu.beautysalon_new_v2.hook;

import android.content.Context;

import com.android.volley.VolleyError;

import com.wiipu.beautysalon_new_v2.network.beans.ErrorHook;
import com.wiipu.beautysalon_new_v2.network.NetworkManager;
import com.wiipu.beautysalon_new_v2.network.utils.LogType;
import com.wiipu.beautysalon_new_v2.network.utils.LogUtil;

/**
 * Created by Ken~Jc on 2016/3/11.
 * 错误回调hook
 */
public class ErrorHandleHook implements ErrorHook {

    public void deal(Context context,VolleyError error){
        LogUtil.log(LogType.DEBUG, NetworkManager.class, error.toString());
    }
}
