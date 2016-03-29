package com.wiipu.beautysalon_new_v2.request;

import com.wiipu.beautysalon_new_v2.data.ServiceItemsPlusData;

import java.util.ArrayList;

/**
 * Created by Ken~Jc on 2016/3/25.
 */
public class PostOrderInfoRequest {
    private String service_id;

    private String shop_id;

    private String barber_id;

    private String assistant_id;

    private String customer_id;

    private String customer_name;

    private String customer_phone_num;

    private String customer_sex;

    private ArrayList<ServiceItemsPlusData> service_items ;

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getBarber_id() {
        return barber_id;
    }

    public void setBarber_id(String barber_id) {
        this.barber_id = barber_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getAssistant_id() {
        return assistant_id;
    }

    public void setAssistant_id(String assistant_id) {
        this.assistant_id = assistant_id;
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

    public ArrayList<ServiceItemsPlusData> getService_items() {
        return service_items;
    }

    public void setService_items(ArrayList<ServiceItemsPlusData> service_items) {
        this.service_items = service_items;
    }
}
