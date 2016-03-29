package com.wiipu.beautysalon_new_v2.request;

/**
 * Created by Ken~Jc on 2016/3/15.
 * 查询用户信息的请求Request
 */
public class QueryCustomerInfoRequest {
    private String customer_id;
    private String customer_phone_num;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_phone_num() {
        return customer_phone_num;
    }

    public void setCustomer_phone_num(String customer_phone_num) {
        this.customer_phone_num = customer_phone_num;
    }
}
