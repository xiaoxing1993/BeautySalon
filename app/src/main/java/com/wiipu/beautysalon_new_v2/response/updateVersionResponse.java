package com.wiipu.beautysalon_new_v2.response;

/**
 * Created by Ken~Jc on 2016/3/10.
 * 版本升级的返回Response
 */
public class updateVersionResponse {
    // "note":"添加卸载功能",
    // "version":"android_s_park_0.95",
    // "apk_url":"http:\/\/112.124.3.197:8101\/download\/android_s_park_0.95.apk",
    // "update_time":"1431324699",
    // "must_update":"0"
    private int exe_success;

    public int getExe_success() {
        return exe_success;
    }

    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }

    private String note;
    private String version;
    private String apk_url;
    private int must_update;
    private String update_time;

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public int getMust_update() {
        return must_update;
    }

    public void setMust_update(int must_update) {
        this.must_update = must_update;
    }

}
