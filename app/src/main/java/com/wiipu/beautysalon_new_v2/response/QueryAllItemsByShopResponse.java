package com.wiipu.beautysalon_new_v2.response;

import com.wiipu.beautysalon_new_v2.data.ServiceItemsData;

import java.util.ArrayList;

/**
 * Created by Ken~Jc on 2016/3/17.
 * 查询店铺所有服务的返回Response
 */
public class QueryAllItemsByShopResponse {
    private int exe_success;
    private ArrayList<ServiceItemsData> list;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public ArrayList<ServiceItemsData> getList() {
        return list;
    }

    public void setList(ArrayList<ServiceItemsData> list) {
        this.list = list;
    }
}
