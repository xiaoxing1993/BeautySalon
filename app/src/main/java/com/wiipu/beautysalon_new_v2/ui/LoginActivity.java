package com.wiipu.beautysalon_new_v2.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.wiipu.beautysalon_new_v2.utils.PatternUtil;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.utils.SendCheckNumberUtil;

/**
 * 登录界面
 * 判断角色为理发师则跳到理发师页面，助理同理
 */
public class LoginActivity extends Activity {

    /**
     * 登录成功
     */
    public final static int SUCCESS_LOGIN = 1;
    /**
     * 登录失败
     */
    public final static int FAIL_LOGIN = 0;
    /**
     * 倒计时标志位
     */
    private final static int TIME_DOWN = 100;
    /**
     * 信息未完善
     */
    public final static int INFO_NOT_COMPLETE = 0;
    /**
     * 倒计时开始
     */
    private final static int TIME_DOWN_START = 101;
    private EditText et_phone;
    private EditText et_verifier;
    private Button btn_get_verifier;
    private Button btn_login;

    private String userPhoneNum="";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_DOWN_START: {
                    btn_get_verifier
                            .setBackgroundResource(R.drawable.btn_get_vcode_shape_gray);
                    timer.schedule(task, 0, 1000);
                    btn_get_verifier.setClickable(false);
                    break;
                }
                case TIME_DOWN:
                    if (totalTime > 0) {
                        btn_get_verifier.setClickable(false);
                        btn_get_verifier.setText(--totalTime + "秒后重发");
                    } else {
                        btn_get_verifier
                                .setBackgroundResource(R.drawable.btn_login_shape);
                        btn_get_verifier.setText("点击重新获取");
                        btn_get_verifier.setClickable(true);
                        totalTime = 60;
                        task.cancel();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 定时器
     */
    private Timer timer;
    /**
     * 执行定时任务
     */
    private TimerTask task;

    /**
     * 记录是否正在发送验证码的标志位
     */
    private Boolean isSendingCheckCode = false;

    public static int  totalTime = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

       // StatusBarUtil.setTransparentStatusBar(this);

        init();
        initView();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 登录，手机号和验证码是否全部填好
                //if (isLogin())
                login();

                //判断职位进入不同的管理页面
                judgePosition(getPosition());

            }
        });

    }

    private void init() {
        timer = new Timer();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        et_phone = (EditText) findViewById(R.id.login_et_phone);
        et_verifier = (EditText) findViewById(R.id.login_et_verifier);
        btn_get_verifier = (Button) findViewById(R.id.login_btn_getCheckCode);

        et_phone.setText(getUserPhoneNum());

        btn_get_verifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取短信验证码
                if (isGetVerifier()) {
                    getVerifier();
                    totalTime = 60;
                    et_verifier.requestFocus();
                }
            }
        });
        btn_login = (Button) findViewById(R.id.login_btn_login);

    }

    /**
     * 是否可以获取验证码
     *
     * @return
     */
    private boolean isGetVerifier() {
        if ("".equals(et_phone.getText().toString().trim())) {
            // 未输入手机号
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            et_phone.requestFocus();
            return false;
        } else if (!PatternUtil.CheckPhoneNum(et_phone.getText().toString().trim())) {
            Toast.makeText(this, "手机号输入错误", Toast.LENGTH_SHORT).show();
            et_phone.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 获取短信验证码
     */
    private void getVerifier() {
        if (isSendingCheckCode)
            return;
         //发送验证码请求
        SendCheckNumberUtil.sendGetCheckNumberRequest(et_phone.getText().toString(),SendCheckNumberUtil.TYPE_LOGIN);

        task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TIME_DOWN);
            }
        };
        handler.sendEmptyMessage(TIME_DOWN_START);
    }

    /**
     * 是否可以登录
     *
     * @return
     */
    private boolean isLogin() {
        if (!PatternUtil.CheckPhoneNum(et_phone.getText().toString().trim())) {
            // 未输入手机号
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            et_phone.requestFocus();
            return false;
        } else if ("".equals(et_verifier.getText().toString().trim())) {
            //未输入验证码
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            et_verifier.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 回调之后获取用户职位
     * @return position
     */
    private String getPosition(){

        return et_phone.getText().toString().trim();
    }

    /**
     * 保存用户输入的手机号码
     */
    private void saveTextPhone(String phoneNum){
        SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("UserPhoneNum", phoneNum);
        edit.commit();
    }

    /**
     * 取出用户所输入的手机号码
     */
    private String getUserPhoneNum(){
        SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
        String num = sp.getString("UserPhoneNum", null);
        return num;
    }

    private void judgePosition(String position){

        if("18740441732".equals(position)){
            Intent intentAssistant=new Intent(LoginActivity.this,AssistantActivity.class);
            startActivity(intentAssistant);
            finish();
        }
        else if("".equals(position)){
            Intent intentBarber = new Intent(LoginActivity.this, BarberActivity.class);
            startActivity(intentBarber);
            finish();
        }
        else {
            Toast.makeText(LoginActivity.this,"身份验证出现错误，请检查服务器！",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 登录
     */
    private void login(){

        String userPhoneNum=et_phone.getText().toString().trim();
        saveTextPhone(userPhoneNum);

    }
//    private void login() {
//        LoginRequest request = new LoginRequest();
//        request.setPhone(et_phone.getText().toString().trim());
//        request.setPwd("12345");
//        request.setVerify_code(et_verifier.getText().toString());
//        NetworkManager.getInstance().post(MethodConstant.LOGIN, request,
//                new ResponseHook() {
//                    @Override
//                    public void deal(Context context, JsonReceive receive) {
//                        LoginResponse response = (LoginResponse) receive
//                                .getResponse();
//                        int login = response.getLogin();
//                        long uid = response.getUid();
//                        System.out.println("23333:"+receive.getStatus());
//                        System.out.println("23333:"+receive.getError());
//                        // 登录成功
//                        if (login == LoginActivity.SUCCESS_LOGIN) {
//                            // 信息未完善
//                            if (response.getIs_verify() == INFO_NOT_COMPLETE) {
//                                // 保存登录状态
//                                SaveLoginStatusUtil.save(context, uid, false);
//                                Intent intent = new Intent(LoginActivity.this,
//                                        CompleteInfoActivity.class);
//                                intent.putExtra("uid", response.getUid());
//                                startActivity(intent);
//                                finish();
//                                return;
//                            }
//                            // 保存登录状态
//                            SaveLoginStatusUtil.save(context, uid, true);
//                            ShoppingConstants.uid = uid;
//                            ShoppingConstants.FLAG_LOGIN = true;
//                            finish();
//                            return;
//                        }
//                        // 登录失败
//                        if (login == LoginActivity.FAIL_LOGIN) {
//                            if (null != receive.getError()
//                                    && !"".equals(receive.getError())) {
//                                Toast.makeText(LoginActivity.this,
//                                        getError(receive.getStatus()),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                }, new ErrorHook() {
//
//                    @Override
//                    public void deal(Context arg0, VolleyError arg1) {
//                        // TODO 自动生成的方法存根
//
//                        System.out.println("23333:"+arg1.getMessage());
//                        System.out.println("23333:"+arg1.getStackTrace());
//                        System.out.println("23333:"+arg1.getNetworkTimeMs());
//                    }
//                }, LoginResponse.class);
//    }

    public static String getError(int status) {
        if (status == 201)
            return "验证码输入错误";
        else if (status == 202)
            return "账号不存在";
        else if (status == 502)
            return "手机号输入错误";
        return "";
    }

}
