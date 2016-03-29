package com.wiipu.beautysalon_new_v2.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kenjc.mytitlebar.MyTitleBar;
import com.wiipu.beautysalon_new_v2.Fragment.BookOrderFragment;
import com.wiipu.beautysalon_new_v2.Fragment.MyDataFragment;
import com.wiipu.beautysalon_new_v2.Fragment.ServiceLogFragment;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.utils.UpdateVersion;

/**
 * 理发师页面的Activity
 */
public class BarberActivity extends FragmentActivity {
    private LinearLayout btn_barber_book_order;// 查询客户信息
    private LinearLayout btn_barber_service_log;// 服务记录
    private LinearLayout btn_barber_data;// 数据分析

    private TextView tv_bar_book_order;
    private TextView tv_bar_log;
    private TextView tv_bar_data;

    private TextView tv_book_order_down;
    private TextView tv_log_down;
    private TextView tv_data_down;

    private static FragmentManager fragmentManager;
    private Fragment contentFragment;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    //标题栏
    private MyTitleBar barberMyTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_barber);
        initView();//默认显示第一页

        new UpdateVersion().start(this);//检查更新
    }

    /*
	 * 初始化界面
	 */
    private void initView() {

        btn_barber_book_order = (LinearLayout) findViewById(R.id.btn_barber_book_order);
        btn_barber_service_log = (LinearLayout) findViewById(R.id.btn_barber_service_log);
        btn_barber_data = (LinearLayout) findViewById(R.id.btn_barber_data);

        tv_bar_book_order=(TextView)findViewById(R.id.tv_bar_book_order);
        tv_bar_log=(TextView)findViewById(R.id.tv_bar_log);
        tv_bar_data=(TextView)findViewById(R.id.tv_bar_data);

        tv_book_order_down=(TextView)findViewById(R.id.tv_bar_book_order_down);
        tv_log_down=(TextView)findViewById(R.id.tv_bar_log_down);
        tv_data_down=(TextView)findViewById(R.id.tv_bar_data_down);

        barberMyTitleBar = (MyTitleBar) findViewById(R.id.mytitlebar_barber);

//        IconFont初始化设置
        Typeface iconfont=Typeface.createFromAsset(getAssets(),"iconfont/iconfont.ttf");
        tv_bar_book_order.setTypeface(iconfont);
        tv_bar_data.setTypeface(iconfont);
        tv_bar_log.setTypeface(iconfont);
        tv_bar_book_order.setTextSize(30);
        tv_bar_log.setTextSize(30);
        tv_bar_data.setTextSize(30);

        btn_barber_book_order.setOnClickListener(listener); ;
        btn_barber_data.setOnClickListener(listener);
        btn_barber_service_log.setOnClickListener(listener);

        mSharedPreferences = this.getSharedPreferences("IsShow", Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();

        fragmentManager = getSupportFragmentManager();

        btn_barber_book_order.performClick();

        //标题栏的可操作性
        barberMyTitleBar.setOnLeftAndRightClickListener(new MyTitleBar.OnLeftAndRightClickListener() {
            @Override
            public void onLeftButtonClick() {
            }

            @Override
            public void onRightButtonClick() {
                //添加预约
                Intent intent=new Intent(BarberActivity.this,AddBookOrderActivity.class);
                startActivity(intent);
            }
        });

        barberMyTitleBar.setLeftButtonClickable(false);
        barberMyTitleBar.setRightButtonClickable(true);

        barberMyTitleBar.setLeftButtonVisibility(true);//可见性
        barberMyTitleBar.setRightButtonVisibility(true);

    }

    //几个fragment之间的切换
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (v.getId()) {
                //预约订单
                case R.id.btn_barber_book_order:
                    contentFragment = new BookOrderFragment();
                    transaction.replace(R.id.barber_fragmentPager, contentFragment);
                    switchUI(btn_barber_book_order);
                    break;
                //个人信息
                case R.id.btn_barber_data:
                    contentFragment = new MyDataFragment();
                    transaction.replace(R.id.barber_fragmentPager, contentFragment);
                    switchUI(btn_barber_data);
                    break;
                //服务记录
                case R.id.btn_barber_service_log:
                    contentFragment = new ServiceLogFragment();
                    transaction.replace(R.id.barber_fragmentPager, contentFragment);
                    switchUI(btn_barber_service_log);
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
            //switchAnimation(imageButton);
            if (btn_barber_book_order.equals(imageButton)) {
                //订单列表
                String string = BarberActivity.this.getResources().getString(R.string.reservation_order);
                barberMyTitleBar.setTitleText(string);
                tv_bar_book_order.setTextColor(getResources().getColor(R.color.btn_true));
                tv_bar_log.setTextColor(getResources().getColor(R.color.btn_false));
                tv_bar_data.setTextColor(getResources().getColor(R.color.btn_false));

                tv_book_order_down.setTextColor(getResources().getColor(R.color.btn_true));
                tv_log_down.setTextColor(getResources().getColor(R.color.btn_false));
                tv_data_down.setTextColor(getResources().getColor(R.color.btn_false));
            } else if (btn_barber_service_log.equals(imageButton)) {
                //服务记录
                String string = BarberActivity.this.getResources().getString(R.string.service_log);
                barberMyTitleBar.setTitleText(string);
                tv_bar_book_order.setTextColor(getResources().getColor(R.color.btn_false));
                tv_bar_log.setTextColor(getResources().getColor(R.color.btn_true));
                tv_bar_data.setTextColor(getResources().getColor(R.color.btn_false));

                tv_book_order_down.setTextColor(getResources().getColor(R.color.btn_false));
                tv_log_down.setTextColor(getResources().getColor(R.color.btn_true));
                tv_data_down.setTextColor(getResources().getColor(R.color.btn_false));
            } else if (btn_barber_data.equals(imageButton)) {
                //我的数据界面
                String string = BarberActivity.this.getResources().getString(R.string.my_data);
                barberMyTitleBar.setTitleText(string);
                tv_bar_book_order.setTextColor(getResources().getColor(R.color.btn_false));
                tv_bar_log.setTextColor(getResources().getColor(R.color.btn_false));
                tv_bar_data.setTextColor(getResources().getColor(R.color.btn_true));

                tv_book_order_down.setTextColor(getResources().getColor(R.color.btn_false));
                tv_log_down.setTextColor(getResources().getColor(R.color.btn_false));
                tv_data_down.setTextColor(getResources().getColor(R.color.btn_true));
            }
        }
    };
}
