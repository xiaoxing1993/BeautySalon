package com.wiipu.beautysalon_new_v2.request;

/**
 * Created by Ken~Jc on 2016/3/20.
 * 查询所有空闲理发师的Request
 */
public class QueryBarbersRequest {
    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    private String shop_id;

}
