package com.wiipu.beautysalon_new_v2.response;

/**
 * Created by Ken~Jc on 2016/3/10.
 * 启动第一屏的返回Response
 */
public class WelcomeImageResponse {
    //	"exe_success":1,
//	"list":{"img_url":"http:\/\/112.124.3.197:8006\/images\/2015\/06\/upload_1434080406316.jpg"}}}
    private int exe_success;
    private String img_url;
    public int getExe_success() {
        return exe_success;
    }
    public void setExe_success(int exe_success) {
        this.exe_success = exe_success;
    }
    public String getImg_url() {
        return img_url;
    }
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString(){
        return "WelcomeImage:[exe_success="+String.valueOf(exe_success)+",img_url="+img_url+"]";
    }

}
