package com.wiipu.beautysalon_new_v2.request;

/**
 * Created by Ken~Jc on 2016/3/11.
 * Modified by xxx on 2016/3/14
 */
public class LoginRequest {
    private String phone_num;
    private String verify_code;

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }
}
