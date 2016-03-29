package com.wiipu.beautysalon_new_v2.data;

/**
 * Created by Ken~Jc on 2016/3/15.
 * 用户持有的所有免费服务的data
 */
public class CustomerFreeItemsData {
    private String item_id;
    private String item_name;
    private String item_price;
    private String item_deadline;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_deadline() {
        return item_deadline;
    }

    public void setItem_deadline(String item_deadline) {
        this.item_deadline = item_deadline;
    }
}
