package com.wiipu.beautysalon_new_v2.response;

import com.wiipu.beautysalon_new_v2.data.CustomerFreeItemsData;

import java.util.ArrayList;

/**
 * Created by Ken~Jc on 2016/3/15.
 * 查询用户信息的返回Response
 */
public class QueryCustomerInfoResponse {
    private int exe_success;
    private String customer_id;
    private String customer_phone_num;
    private String customer_name;
    private String customer_sex;
    private String customer_total_money;
    private String customer_left_money;
    private String customer_left_coins;
    private ArrayList<CustomerFreeItemsData> list;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_sex() {
        return customer_sex;
    }

    public void setCustomer_sex(String customer_sex) {
        this.customer_sex = customer_sex;
    }

    public String getCustomer_total_money() {
        return customer_total_money;
    }

    public void setCustomer_total_money(String customer_total_money) {
        this.customer_total_money = customer_total_money;
    }

    public String getCustomer_left_money() {
        return customer_left_money;
    }

    public void setCustomer_left_money(String customer_left_money) {
        this.customer_left_money = customer_left_money;
    }

    public String getCustomer_left_coins() {
        return customer_left_coins;
    }

    public void setCustomer_left_coins(String customer_left_coins) {
        this.customer_left_coins = customer_left_coins;
    }

    public ArrayList<CustomerFreeItemsData> getList() {
        return list;
    }

    public void setList(ArrayList<CustomerFreeItemsData> list) {
        this.list = list;
    }
}
