package com.wiipu.beautysalon_new_v2.request;

/**
 * Created by xxx on 2016/3/14.
 */
public class AddBookOrderRequest {

    private String personal_id;
    private String book_name;
    private String book_phone_num;
    private String book_time;
    private String book_remark;

    public String getPersonal_id() {
        return personal_id;
    }

    public void setPersonal_id(String personal_id) {
        this.personal_id = personal_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_phone_num() {
        return book_phone_num;
    }

    public void setBook_phone_num(String book_phone_num) {
        this.book_phone_num = book_phone_num;
    }

    public String getBook_time() {
        return book_time;
    }

    public void setBook_time(String book_time) {
        this.book_time = book_time;
    }

    public String getBook_remark() {
        return book_remark;
    }

    public void setBook_remark(String book_remark) {
        this.book_remark = book_remark;
    }
}
