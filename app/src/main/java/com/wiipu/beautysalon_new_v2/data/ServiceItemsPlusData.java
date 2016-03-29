package com.wiipu.beautysalon_new_v2.data;

/**
 * Created by Ken~Jc on 2016/3/25.
 */
public class ServiceItemsPlusData {
    private String item_id;

    private String is_current_send;

    private String current_send_times;

    private String current_send_deadline;

    private String is_item_free;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getIs_current_send() {
        return is_current_send;
    }

    public void setIs_current_send(String is_current_send) {
        this.is_current_send = is_current_send;
    }

    public String getCurrent_send_times() {
        return current_send_times;
    }

    public void setCurrent_send_times(String current_send_times) {
        this.current_send_times = current_send_times;
    }

    public String getCurrent_send_deadline() {
        return current_send_deadline;
    }

    public void setCurrent_send_deadline(String current_send_deadline) {
        this.current_send_deadline = current_send_deadline;
    }

    public String getIs_item_free() {
        return is_item_free;
    }

    public void setIs_item_free(String is_item_free) {
        this.is_item_free = is_item_free;
    }
}
