package com.wiipu.beautysalon_new_v2.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.wiipu.beautysalon_new_v2.constants.MethodConstant;
import com.wiipu.beautysalon_new_v2.network.NetworkManager;
import com.wiipu.beautysalon_new_v2.network.beans.ErrorHook;
import com.wiipu.beautysalon_new_v2.network.beans.JsonReceive;
import com.wiipu.beautysalon_new_v2.network.beans.ResponseHook;
import com.wiipu.beautysalon_new_v2.request.UpdateVersionRequest;
import com.wiipu.beautysalon_new_v2.response.WelcomeImageResponse;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.utils.StatusBarUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 启动页
 * 启动之后跳转到登录页
 */
public class LaunchActivity extends Activity {
    private ImageView iv;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);

        StatusBarUtil.setTransparentStatusBar(this);

        iv = (ImageView) findViewById(R.id.launch_acticity_image);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (JudgeFirstUse()) {

            // 如果是第一次使用，则加载系统中的默认图片
            iv.setImageBitmap(new BitmapFactory().decodeResource(
                    getResources(), R.drawable.default_load_pic));
        }
        // 不是第一次使用，则去文件夹中拿取图片
        else {
            try {
                Bitmap bit = loadPictureFromFile();
                if (bit != null) {

                    iv.setImageBitmap(bit);
                }

                else {

                    iv.setImageBitmap(new BitmapFactory().decodeResource(
                            getResources(), R.drawable.default_load_pic));
                }

            } catch (FileNotFoundException e) {
                // 文件夹中并不存在图片，则需要显示默认的图片
                iv.setImageBitmap(new BitmapFactory().decodeResource(
                        getResources(), R.drawable.default_load_pic));
                e.printStackTrace();
            }

        }
        // 将第一次使用的标志置为false
        HaveUsed();
        // 获取最新的图片
        NetworkManager.getInstance().post(MethodConstant.WELCOME_MANAGE,
                new UpdateVersionRequest(), new ResponseHook() {

                    @Override
                    public void deal(Context arg0, JsonReceive receive) {
                        WelcomeImageResponse response = (WelcomeImageResponse) receive
                                .getResponse();

                        Log.e("呵呵额",response.toString());
                        if (response != null) {
                            if (response.getExe_success() == 1) {
                                imageUrl = response.getImg_url();
                                // 比较最新的url和存放的url是否有不同，不同便需要下载，否则不要下载
                                //仅有当URL相同且文件存在时才不需下载
                                if ((imageUrl.equals(getImageUrl())&isPicExist())){
                                    Log.d("Pic already exist,","Needn't to down");
                                }else {
                                    //if (!imageUrl.equals(getImageUrl()))

                                    // url不相同 需要下载
                                    saveImageUrl(imageUrl);

                                    // 如果需要下载，则下载图片保存在文件夹里面
                                    new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {
                                                loadPictureFromNet(imageUrl);
                                            } catch (Exception e) {
                                                // TODO 自动生成的 catch 块
                                                e.printStackTrace();
                                            }

                                        }
                                    }).start();

                                }

                            }
                        }
                        Log.d("LaunchActivity", response.toString());

                    }
                }, new ErrorHook(){
                    @Override
                public void deal(Context arg0, VolleyError arg1) {
                        System.out.println("");
                    }
                }, WelcomeImageResponse.class);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }

    /**
     * 获取图片的url
     */
    private String getImageUrl() {
        SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
        String url = sp.getString("imageUrl", null);
        return url;
    }

    /**
     * 将服务器最新的图片url存放起来
     */
    private void saveImageUrl(String s) {
        SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("imageUrl", s);
        edit.commit();
    }

    /**
     * 判断是否是第一次使用，如果是则返回false，否则返回true
     */
    private boolean JudgeFirstUse() {
        SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
        boolean firstUse = sp.getBoolean("firstUse", true);
        return firstUse;
    }

    /**
     * 第一次使用的时候，需要调用该函数，表示下一次使用的时候已经不是第一次使用了
     */
    private void HaveUsed() {
        SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("firstUse", false);
        edit.commit();
    }

    /**
     * 判断文件是否存在，若不存在则重新下载，已经存在则不需下载
     * 先核对url，若一致且文件不存在时，则再次下载；
     *
     * 且Target 4.4以上编译会出现找不到路径的问题
     *
     * @throws FileNotFoundException
     */
    private boolean isPicExist(){
        File dir = new File(Environment.getExternalStorageDirectory(),
                "mandi");
        File file = new File(dir, "load.jpg");
        if (!file.exists()) return false;
        else return true;
    }

    /**
     * 从存储图片的文件夹中拿出图片
     *
     * @throws FileNotFoundException
     */
    private Bitmap loadPictureFromFile() throws FileNotFoundException {
        File dir = new File(Environment.getExternalStorageDirectory(),
                "mandi");
        File file = new File(dir, "load.jpg");
        FileInputStream fis = new FileInputStream(file);
        Bitmap bit = BitmapFactory.decodeStream(fis);
        return bit;
    }

    /**
     * @category 从确定的url中下载图片，然后把图片放在手机内存的文件夹中，图片的名字叫“load.jpg”
     * @throws FileNotFoundException
     */
    private void loadPictureFromNet(String myUrl) throws Exception {

        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        InputStream is = conn.getInputStream();
        File dir = new File(Environment.getExternalStorageDirectory(),
                "mandi");
        if (!dir.exists())
            dir.mkdir();
        File file = new File(dir, "load.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] buff = new byte[1024];
        int l;
        while ((l = bis.read(buff)) != -1) {

            fos.write(buff, 0, l);
        }
        fos.close();
        bis.close();
        is.close();

    }
}