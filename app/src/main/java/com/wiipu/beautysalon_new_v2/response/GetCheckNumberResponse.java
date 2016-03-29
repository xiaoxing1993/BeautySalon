package com.wiipu.beautysalon_new_v2.response;

/**
 * Created by Ken~Jc on 2016/3/14.
 * 获取验证码的返回Response
 */
public class GetCheckNumberResponse {

    private String has_send;
    private String exe_success;

    public String getExe_success() {
        return exe_success;
    }

    public void setExe_success(String exe_success) {
        this.exe_success = exe_success;
    }


    public String getHas_send() {
        return has_send;
    }

    public void setHas_send(String has_send) {
        this.has_send = has_send;
    }

    @Override
    public String toString(){

        return  "[exe_success="+exe_success+",has_send="+has_send+"]";
    }

}
