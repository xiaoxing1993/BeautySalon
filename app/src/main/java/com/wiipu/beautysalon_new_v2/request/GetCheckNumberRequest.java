package com.wiipu.beautysalon_new_v2.request;

/**
 * Created by Ken~Jc on 2016/3/14.
 * 获取验证码的请求Request
 */
public class GetCheckNumberRequest {
    private String phoneNumber;
    private int type;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
