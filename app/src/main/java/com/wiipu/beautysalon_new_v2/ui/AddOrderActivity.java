package com.wiipu.beautysalon_new_v2.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenjc.mytitlebar.MyTitleBar;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.data.ServiceItemsData;
import com.wiipu.beautysalon_new_v2.utils.DialogUtils;
import com.wiipu.beautysalon_new_v2.utils.EditChangeListenerUtil;
import com.wiipu.beautysalon_new_v2.utils.GetDatesUtil;
import com.wiipu.groupviewlayout.view.GroupViewLayout;

import java.util.ArrayList;

/**
 * 添加订单、下单Activity
 * 仅在助理页可添加订单
 */
public class AddOrderActivity extends Activity {

    private Button btnCommit;
    private TextView orderId;
    private ArrayList<ServiceItemsData> list;
    private ListView listView;
    private MyTitleBar myTitleBar;
    private RelativeLayout rlAllItems;
    private RelativeLayout rlBarberFree;
    private GroupViewLayout groupViewTopUp;
    private RelativeLayout rlOrderFree;
    private RelativeLayout rlTopUpPage;
    private EditText etLeftMoney;
    private EditText etLeftCoins;
    private TextView btnMoneyPlus;
    private TextView btnMoneyMin;
    private TextView btnCoinsPlus;
    private TextView btnCoinsMin;
    private RadioGroup radioGroup;

    private int leftMoney=0;
    private int leftCoins=0;
    private String MyId="0031";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_order);

        initView();
    }

//    刷新充值框的值
    private void refEtView(){
        //leftMoney=Integer.valueOf(String.valueOf(etLeftMoney.getText()));
        //leftCoins=Integer.valueOf(String.valueOf(etLeftCoins.getText()));
        etLeftMoney.setText(String.valueOf(leftMoney));
        etLeftCoins.setText(String.valueOf(leftCoins));
    }

//    初始化，绑定id和监听
    private void initView() {
        btnCommit=(Button)findViewById(R.id.btn_commit);
        list=new ArrayList<ServiceItemsData>();
        myTitleBar=(MyTitleBar)findViewById(R.id.mytitlebar_add_order);
        rlAllItems=(RelativeLayout)findViewById(R.id.rl_all_items);
        rlBarberFree=(RelativeLayout)findViewById(R.id.rl_free_barber_add_order);
        groupViewTopUp=(GroupViewLayout)findViewById(R.id.group_top_up);
        rlOrderFree=(RelativeLayout)findViewById(R.id.rl_add_order_free);
        rlTopUpPage=(RelativeLayout)findViewById(R.id.rl_top_up_page);
        etLeftMoney=(EditText)findViewById(R.id.et_left_money);
        etLeftCoins=(EditText)findViewById(R.id.et_left_coins);
        btnMoneyMin=(TextView)findViewById(R.id.btn_money_min);
        btnMoneyPlus=(TextView)findViewById(R.id.btn_money_plus);
        btnCoinsMin=(TextView)findViewById(R.id.btn_coins_min);
        btnCoinsPlus=(TextView)findViewById(R.id.btn_coins_plus);
        radioGroup=(RadioGroup)findViewById(R.id.radio_group_top_money);
        orderId=(TextView)findViewById(R.id.tv_order_id_add_order);

        //        IconFont初始化设置
        Typeface iconfont=Typeface.createFromAsset(getAssets(),"iconfont/iconfont.ttf");
        btnMoneyMin.setTypeface(iconfont);
        btnMoneyPlus.setTypeface(iconfont);
        btnCoinsPlus.setTypeface(iconfont);
        btnCoinsMin.setTypeface(iconfont);

        orderId.setText(GetDatesUtil.getOrderId() + MyId);

//        设定监听事件
        btnMoneyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refEtView();
                leftMoney += 500;
                refEtView();
            }
        });
        btnMoneyMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refEtView();
                if (leftMoney > 0) {
                    if (leftMoney <= 500) {
                        leftMoney = 0;
                    } else {
                        leftMoney -= 500;
                    }
                } else {
                    Toast.makeText(AddOrderActivity.this, "不能更少啦！", Toast.LENGTH_SHORT).show();
                }
                refEtView();
            }
        });
        btnCoinsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refEtView();
                leftCoins += 500;
                refEtView();
            }
        });
        btnCoinsMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refEtView();
                if (leftCoins > 0) {
                    if (leftCoins <= 500) {
                        leftCoins = 0;
                    } else {
                        leftCoins -= 500;
                    }
                } else {
                    Toast.makeText(AddOrderActivity.this, "不能更少啦！", Toast.LENGTH_SHORT).show();
                }
                refEtView();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                refEtView();
                switch (radioButtonId) {
                    case R.id.top_up_1000:
                        leftMoney = 1000;
                        leftCoins = 200;
                        break;
                    case R.id.top_up_2000:
                        leftMoney = 2000;
                        leftCoins = 500;
                        break;
                    case R.id.top_up_5000:
                        leftMoney = 5000;
                        leftCoins = 1500;
                        break;
                    default:
                        break;
                }
                refEtView();
            }
        });

//        设定编辑框更改的监听，重写了监听函数
        etLeftMoney.addTextChangedListener(new EditChangeListenerUtil(){
            private CharSequence temp;
            @Override
            public void afterTextChanged(Editable s){
                temp=s;
                leftMoney= Integer.parseInt(temp.toString());
            }
        });
        etLeftCoins.addTextChangedListener(new EditChangeListenerUtil() {
            private CharSequence temp;

            @Override
            public void afterTextChanged(Editable s) {
                temp = s;
                leftCoins = Integer.parseInt(temp.toString());
            }
        });


//        设置抽屉视图
        groupViewTopUp.settingGroupView(rlTopUpPage);

//        所有服务的对话框
        rlAllItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showAllServiceItemsDialog(AddOrderActivity.this);
            }
        });

//        所有空闲理发师的对话框
        rlBarberFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showAllFreeBarberDialog(AddOrderActivity.this);
            }
        });

//        自定义状态栏的监听事件
        myTitleBar.setOnLeftAndRightClickListener(new MyTitleBar.OnLeftAndRightClickListener() {
            @Override
            public void onLeftButtonClick() {
                //myTitleBar.setRightButtonBackground(R.mipmap.ic_launcher);//更改左右按钮背景图
            }

            @Override
            public void onRightButtonClick() {
                //myTitleBar.setLeftButtonBackground(R.mipmap.backarrow); //更改左右按钮背景图
                finish();
            }
        });
        myTitleBar.setLeftButtonVisibility(true);//可见性
        myTitleBar.setRightButtonVisibility(true);
        myTitleBar.setLeftButtonClickable(false);//可点击性
        myTitleBar.setRightButtonClickable(true);

//        设定最后提交订单的按钮监听
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showCommitDialog(AddOrderActivity.this);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("当前状态恢复","");
    }
}
