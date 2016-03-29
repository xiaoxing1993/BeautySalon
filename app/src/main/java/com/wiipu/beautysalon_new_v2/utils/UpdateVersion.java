package com.wiipu.beautysalon_new_v2.utils;

/**
 * Created by Ken~Jc on 2016/3/11.
 * 检测版本并更新，直接调用start函数
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AlertDialog;

import com.wiipu.beautysalon_new_v2.response.updateVersionResponse;
import com.wiipu.beautysalon_new_v2.constants.MethodConstant;
import com.wiipu.beautysalon_new_v2.request.UpdateVersionRequest;
import com.wiipu.beautysalon_new_v2.network.NetworkManager;
import com.wiipu.beautysalon_new_v2.network.beans.JsonReceive;
import com.wiipu.beautysalon_new_v2.network.beans.ResponseHook;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class UpdateVersion {
    //	存放的版本号 myversion表示手机已经安装的版本号  SerVersion表示服务器存放的版本号
    private String myVersion, SerVersion;
    //	是否需要强制更新  1 表示需要强制更新  0表示不需要强制更新
    private int mustUpdate;
    //	apk的下载url
    private String apk_url;
    //	新版本的说明内容
    private String note;
    InputStream is;
    private Context context;
    //	对话框
    ProgressDialog pd;
    /**
     *检测版本并更新的启动函数
     *
     */
    public void start(final Context context) {
        this.context = context;
        myVersion = getVersion();

        try {
//			与服务器通话，拿到版本更新的相关信息
            NetworkManager.getInstance().post(MethodConstant.UPDATE_VERSION, new UpdateVersionRequest(), new ResponseHook() {

                @Override
                public void deal(Context context, JsonReceive receive) {

                    updateVersionResponse response=(updateVersionResponse)receive.getResponse();
                    if(response!=null){
                        if(response.getExe_success()==1){


                            SerVersion=response.getVersion();
                            SerVersion=SerVersion.replaceAll("android_s_park_", "");
                            mustUpdate=response.getMust_update();
                            apk_url=response.getApk_url();
                            note=response.getNote();
//							开始调用更新函数
                            update();
                        }
                    }


                }
            }, null,updateVersionResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于版本更新的函数
     * 先检测是否需要更新 以及是否需要强制更新
     */
    private void update(){
        if (myVersion != null && SerVersion != null) {
            if (!(myVersion.equals(SerVersion))) {
//					需要强行更新
                if(mustUpdate==1){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("版本升级").setMessage("出现新的版本，请务必更新，否则无法使用！\n ");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            pd = new ProgressDialog(context);
                            pd.setCancelable(false);
//							对话框的图标
//							pd.setIcon(R.drawable.i1);
                            pd.setTitle("正在下载");
                            pd.setMessage("我正在努力的下载着。。");
                            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            pd.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Looper.prepare();


                                        LoadAPK();
                                        Looper.loop();
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                        }
                    });
                    dialog.create();
                    dialog.show();}
//					不需要强行更新
                else{

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("版本升级").setMessage("检测到新版本，请及时升级！\n "+note);
                    dialog.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            pd = new ProgressDialog(context);
                            pd.setCancelable(false);
//							对话框的图标
//							pd.setIcon(R.drawable.i1);
                            pd.setTitle("正在下载");
                            pd.setMessage("我正在努力的下载着。。");
                            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            pd.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Looper.prepare();


                                        LoadAPK();
                                        Looper.loop();
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                        }
                    });
                    dialog.setNeutralButton("下次再说", null);
                    dialog.create();
                    dialog.show();
                }
            }
        }
    }



    /**
     * 下载最新的apk
     *
     * @throws //MalformedURLException
     *
     */
    private void LoadAPK() throws Exception {
        URL url = new URL(apk_url);
//		URL url=new URL("http://112.124.3.197:8013/download/apk_version/2015/12/农资12.9.2.apk");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        DecimalFormat df = new DecimalFormat("##.00");
        System.out.println("获得的apk字节数 "+conn.getContentLength()/1024+"Kb");
        pd.setProgressNumberFormat(df.format((conn.getContentLength())
                / (1024.0 * 1024.0))
                + "M");
        pd.setMax((conn.getContentLength()) / 1024);
        InputStream is = conn.getInputStream();
        File dir = new File(Environment.getExternalStorageDirectory(), "mandi");
        if (!dir.exists())
            dir.mkdir();
        File file = new File(dir, "农资商城.apk");
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] buff = new byte[1024];
        int len;
        int total = 0;
        while ((len = bis.read(buff)) != -1) {
            fos.write(buff, 0, len);
            total += len;
            pd.setProgress(total / 1024);
        }
        pd.dismiss();
        fos.close();
        bis.close();
        is.close();

        installApk(file);
    }

    /**
     * 获取当前版本号
     *
     * @throws //NameNotFoundException
     *
     */
    private String getVersion() {
        PackageManager mpackageManager = context.getPackageManager();
        PackageInfo info;
        try {
            info = mpackageManager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return null;
    }

    // 安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
        context.startActivity(intent);
    }

}