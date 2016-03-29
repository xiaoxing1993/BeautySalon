package com.wiipu.beautysalon_new_v2.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kenjc.mytitlebar.MyTitleBar;
import com.wiipu.beautysalon_new_v2.Fragment.MyDataFragment;
import com.wiipu.beautysalon_new_v2.Fragment.QueryCustomerFragment;
import com.wiipu.beautysalon_new_v2.Fragment.ServiceLogFragment;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.utils.UpdateVersion;

/**
 * 助理页面的Activity
 */
public class AssistantActivity extends FragmentActivity {

    private LinearLayout btn_ass_query;// 查询客户信息
    private LinearLayout btn_ass_service_log;// 服务记录
    private LinearLayout btn_ass_data;// 数据分析

    private TextView tv_ass_query;
    private TextView tv_ass_service_log;
    private TextView tv_ass_data;

    private TextView tv_query;
    private TextView tv_log;
    private TextView tv_data;

    private static FragmentManager fragmentManager;
    private Fragment contentFragment;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    //标题栏
    private MyTitleBar assistantMyTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_assistant);

        initView();//默认显示第一页

        new UpdateVersion().start(this);//检查更新
    }

    /*
	 * 初始化界面
	 */
    private void initView() {
//        为TextView指定文字，IconFont中的方法
        Typeface iconfond = Typeface.createFromAsset(getAssets(),"iconfont/iconfont.ttf");

//        三个Fragment
        btn_ass_query = (LinearLayout) findViewById(R.id.btn_ass_query);
        btn_ass_service_log = (LinearLayout) findViewById(R.id.btn_ass_service_log);
        btn_ass_data = (LinearLayout) findViewById(R.id.btn_ass_data);

        tv_ass_query=(TextView)findViewById(R.id.tv_ass_query);
        tv_ass_service_log=(TextView)findViewById(R.id.tv_ass_log);
        tv_ass_data=(TextView)findViewById(R.id.tv_ass_data);

        tv_query=(TextView)findViewById(R.id.tv_ass_query_down);
        tv_log=(TextView)findViewById(R.id.tv_ass_log_down);
        tv_data=(TextView)findViewById(R.id.tv_ass_data_down);

//        设定字体
        tv_ass_query.setTypeface(iconfond);
        tv_ass_service_log.setTypeface(iconfond);
        tv_ass_data.setTypeface(iconfond);
        tv_ass_query.setTextSize(30);
        tv_ass_service_log.setTextSize(30);
        tv_ass_data.setTextSize(30);

        assistantMyTitleBar = (MyTitleBar) findViewById(R.id.mytitlebar_assistant);

        btn_ass_query.setOnClickListener(listener);
        btn_ass_data.setOnClickListener(listener);
        btn_ass_service_log.setOnClickListener(listener);

        mSharedPreferences = this.getSharedPreferences("IsShow", Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();

        fragmentManager = getSupportFragmentManager();

        btn_ass_query.performClick();

        //标题栏的可操作性
        assistantMyTitleBar.setOnLeftAndRightClickListener(new MyTitleBar.OnLeftAndRightClickListener() {
            @Override
            public void onLeftButtonClick() {

            }

            @Override
            public void onRightButtonClick() {
                //添加订单
                Intent intent=new Intent(AssistantActivity.this,AddOrderActivity.class);
                startActivity(intent);
            }
        });

        assistantMyTitleBar.setLeftButtonClickable(false);
        assistantMyTitleBar.setRightButtonClickable(true);

        assistantMyTitleBar.setLeftButtonVisibility(true);//可见性
        assistantMyTitleBar.setRightButtonVisibility(true);

    }


    //几个fragment之间的切换
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (v.getId()) {
                //查询客户信息
                case R.id.btn_ass_query:
                    contentFragment = new QueryCustomerFragment();
                    transaction.replace(R.id.assistant_fragmentPager, contentFragment);
                    switchUI(btn_ass_query);
                    break;
                //服务记录
                case R.id.btn_ass_service_log:
                    contentFragment = new ServiceLogFragment();
                    transaction.replace(R.id.assistant_fragmentPager, contentFragment);
                    switchUI(btn_ass_service_log);
                    break;
                //个人信息
                case R.id.btn_ass_data:
                    contentFragment = new MyDataFragment();
                    transaction.replace(R.id.assistant_fragmentPager, contentFragment);
                    switchUI(btn_ass_data);
                    break;

                default:
                    break;
            }
            transaction.commit();
        }


        /**
         * 导航栏变化
         * 目标选项卡的下标
         *
         * @param imageButton
         */

        //切换UI时的动画——标题栏
        private void switchUI(LinearLayout imageButton) {
            if (btn_ass_query.equals(imageButton)) {
                //订单列表
                String string = AssistantActivity.this.getResources().getString(R.string.assistant_query);
                assistantMyTitleBar.setTitleText(string);
                tv_ass_query.setTextColor(getResources().getColor(R.color.btn_true));
                tv_ass_service_log.setTextColor(getResources().getColor(R.color.btn_false));
                tv_ass_data.setTextColor(getResources().getColor(R.color.btn_false));

                tv_query.setTextColor(getResources().getColor(R.color.btn_true));
                tv_log.setTextColor(getResources().getColor(R.color.btn_false));
                tv_data.setTextColor(getResources().getColor(R.color.btn_false));
            } else if (btn_ass_service_log.equals(imageButton)) {
                //服务记录
                String string = AssistantActivity.this.getResources().getString(R.string.service_log);
                assistantMyTitleBar.setTitleText(string);
                tv_ass_query.setTextColor(getResources().getColor(R.color.btn_false));
                tv_ass_service_log.setTextColor(getResources().getColor(R.color.btn_true));
                tv_ass_data.setTextColor(getResources().getColor(R.color.btn_false));

                tv_query.setTextColor(getResources().getColor(R.color.btn_false));
                tv_log.setTextColor(getResources().getColor(R.color.btn_true));
                tv_data.setTextColor(getResources().getColor(R.color.btn_false));
            } else if (btn_ass_data.equals(imageButton)) {
                //我的数据界面
                String string = AssistantActivity.this.getResources().getString(R.string.my_data);
                assistantMyTitleBar.setTitleText(string);
                tv_ass_query.setTextColor(getResources().getColor(R.color.btn_false));
                tv_ass_service_log.setTextColor(getResources().getColor(R.color.btn_false));
                tv_ass_data.setTextColor(getResources().getColor(R.color.btn_true));

                tv_query.setTextColor(getResources().getColor(R.color.btn_false));
                tv_log.setTextColor(getResources().getColor(R.color.btn_false));
                tv_data.setTextColor(getResources().getColor(R.color.btn_true));
            }
        }
    };
}
