package com.wiipu.beautysalon_new_v2.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.wiipu.beautysalon_new_v2.constants.MethodConstant;
import com.wiipu.beautysalon_new_v2.constants.SalonContants;
import com.wiipu.beautysalon_new_v2.network.NetworkManager;
import com.wiipu.beautysalon_new_v2.network.beans.ErrorHook;
import com.wiipu.beautysalon_new_v2.network.beans.JsonReceive;
import com.wiipu.beautysalon_new_v2.network.beans.ResponseHook;
import com.wiipu.beautysalon_new_v2.request.GetCheckNumberRequest;
import com.wiipu.beautysalon_new_v2.response.GetCheckNumberResponse;
import com.wiipu.beautysalon_new_v2.ui.LoginActivity;

/**
 * Created by Ken~Jc on 2016/3/14.
 * 获取验证码的工具request
 * 包括获取
 */
public class SendCheckNumberUtil {
    public final static int TYPE_LOGIN = 2;

    public static void sendGetCheckNumberRequest(String phoneNum,int type){
        final GetCheckNumberRequest request=new GetCheckNumberRequest();
        request.setPhoneNumber(phoneNum);
        request.setType(type);
        NetworkManager.getInstance().post(MethodConstant.GET_CHECKNUM, request,
                new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        GetCheckNumberResponse response = (GetCheckNumberResponse) receive.getResponse();
                        if (response != null) {
                            if (receive.getStatus() == 503) {
                                Toast.makeText(context, "非公司注册用户！", Toast.LENGTH_SHORT).show();
                                LoginActivity.totalTime = 0;
                            } else if (receive.getStatus() == 502) {
                                Toast.makeText(context, "手机号码错误", Toast.LENGTH_SHORT).show();
                                LoginActivity.totalTime = 0;
                            }
                        }
                        Log.d("发送验证码", response.toString());
                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {
                        Log.d("获取验证码错误", "处理错误");
                    }
                },GetCheckNumberResponse.class);
    }

    public static boolean checkLogin(Context context){
        if (SalonContants.uid==-1){
            context.startActivity(new Intent(context,LoginActivity.class));
            return false;
        }
        return true;
    }
}
