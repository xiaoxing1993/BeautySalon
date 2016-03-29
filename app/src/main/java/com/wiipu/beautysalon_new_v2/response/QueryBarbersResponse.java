package com.wiipu.beautysalon_new_v2.response;

import com.wiipu.beautysalon_new_v2.data.BarberFreeTimeData;

import java.util.ArrayList;

/**
 * Created by Ken~Jc on 2016/3/20.
 * 查询所有空闲理发师的返回Response
 *
 */
public class QueryBarbersResponse {
    private int exe_success;
    private ArrayList<BarberFreeTimeData> list;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public ArrayList<BarberFreeTimeData> getList() {
        return list;
    }

    public void setList(ArrayList<BarberFreeTimeData> list) {
        this.list = list;
    }
}
