package com.wiipu.beautysalon_new_v2.response;

import com.wiipu.beautysalon_new_v2.data.BookOrderData;

import java.util.ArrayList;

/**
 * Created by xxx on 2016/3/14.
 */
public class GetBookOrderResponse {

    private int exe_success;

    private ArrayList<BookOrderData> book_order_list;



    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    public ArrayList<BookOrderData> getBook_order_list() {
        return book_order_list;
    }

    public void setBook_order_list(ArrayList<BookOrderData> book_order_list) {
        this.book_order_list = book_order_list;
    }
}
