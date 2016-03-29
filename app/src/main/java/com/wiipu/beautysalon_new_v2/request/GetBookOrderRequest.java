package com.wiipu.beautysalon_new_v2.request;

/**
 * Created by xxx on 2016/3/14.
 */
public class GetBookOrderRequest {

    private String personal_id;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPersonal_id() {
        return personal_id;
    }

    public void setPersonal_id(String personal_id) {
        this.personal_id = personal_id;
    }
}
