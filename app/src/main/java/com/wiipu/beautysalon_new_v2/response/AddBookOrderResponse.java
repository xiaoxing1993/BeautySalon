package com.wiipu.beautysalon_new_v2.response;

/**
 * Created by xxx on 2016/3/14.
 */
public class AddBookOrderResponse {

    private int exe_success;

    private String add_book_status;

    public String getAdd_book_status() {
        return add_book_status;
    }

    public void setAdd_book_status(String add_book_status) {
        this.add_book_status = add_book_status;
    }

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }
}
