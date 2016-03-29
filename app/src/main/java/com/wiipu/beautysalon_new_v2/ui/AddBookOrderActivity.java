package com.wiipu.beautysalon_new_v2.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.kenjc.mytitlebar.MyTitleBar;
import com.wiipu.beautysalon_new_v2.constants.MethodConstant;
import com.wiipu.beautysalon_new_v2.network.NetworkManager;
import com.wiipu.beautysalon_new_v2.network.beans.ErrorHook;
import com.wiipu.beautysalon_new_v2.network.beans.JsonReceive;
import com.wiipu.beautysalon_new_v2.network.beans.ResponseHook;
import com.wiipu.beautysalon_new_v2.request.AddBookOrderRequest;
import com.wiipu.beautysalon_new_v2.response.AddBookOrderResponse;
import com.wiipu.beautysalon_new_v2.utils.ShowToastUtil;
import com.wiipu.beautysalon_new_v2.R;

import java.util.Calendar;

/**
 * 添加预约Activity
 * 仅在理发师页可添加预约订单
 * Modified by xxx on 2016/3/21
 */
public class AddBookOrderActivity extends Activity implements View.OnClickListener{

    private TextView text_date_show;
    private TextView text_time_show;
    private TextView text_remark_show;
    private Button but_exit;
    private EditText et_id;
    private EditText et_name;
    private EditText et_phone;

    private StringBuilder sb = new StringBuilder();

    private AddBookOrderRequest request=new AddBookOrderRequest();

    private String bookTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_book_order);

        initView();
        initEditTextBookTimeListener();
        initBtnListener();//添加按钮监听事件
        // testRequest();//测试方法
    }

    //初始化页面
    private void initView() {

        text_date_show=(TextView)findViewById(R.id.text_date_show_add_book_order);
        text_time_show=(TextView)findViewById(R.id.text_time_show_add_book_order);
        text_remark_show=(TextView)findViewById(R.id.text_remark_show_add_book_order);
        but_exit=(Button)findViewById(R.id.btn_ass_exit);
        et_id=(EditText)findViewById(R.id.edit_customer_id_add_book_order);
        et_name=(EditText)findViewById(R.id.edit_customer_name_add_book_order);
        et_phone=(EditText)findViewById(R.id.edit_customer_phone_add_book_order);

        final MyTitleBar addBookOrderMyTitleBar=(MyTitleBar)findViewById(R.id.mytitlebar_add_book_order);
        addBookOrderMyTitleBar.setOnLeftAndRightClickListener(new MyTitleBar.OnLeftAndRightClickListener() {
            @Override
            public void onLeftButtonClick() {

            }

            @Override
            public void onRightButtonClick() {
                finish();
            }
        });
        addBookOrderMyTitleBar.setLeftButtonVisibility(true);//可见性
        addBookOrderMyTitleBar.setRightButtonVisibility(true);
        addBookOrderMyTitleBar.setLeftButtonClickable(false);//可点击性
        addBookOrderMyTitleBar.setRightButtonClickable(true);
    }

    //设定事件和日期监听事件
    private void initEditTextBookTimeListener() {
        text_date_show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        text_time_show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

        text_remark_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemarkDialog();
            }
        });
    }

    //添加预约日期和时间的选择的dialog，应考虑理发师的空闲时间
    private void showDateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookOrderActivity.this);
        //getActivity()
        View view = View.inflate(AddBookOrderActivity.this,
                R.layout.dialog_data_date_picker, null);
        //getActivity()
        final DatePicker datePicker = (DatePicker) view
                .findViewById(R.id.date_picker);
        builder.setView(view);
        final Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis() + 24 * 60 * 60
                * 1000);
        datePicker.init(calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH), null);
        builder.setTitle("请选择预约日期");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String bookDate = String.format("%d-%02d-%02d",
                        datePicker.getYear(), datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth());
                String currentDate = String.format("%d-%02d-%02d",
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                        calendar
                                .get(Calendar.DAY_OF_MONTH));
                Log.e("bookDate", "bookDate: " + bookDate
                        + "  currentDate:" + currentDate);
                if (bookDate.compareTo(currentDate) > 0) {
                    // 订单不能预约当天之前
                    sb.append(String.format("%d-%02d-%02d",
                            datePicker.getYear(), datePicker.getMonth() + 1,
                            datePicker.getDayOfMonth()));
                    text_date_show.setText(sb);

                    //tv_add_order_ass.setClickable(true);
                    //tv_add_order_bar.setClickable(true);
                    dialog.cancel();
                    sb.delete(0, sb.length());

                } else {
                    ShowToastUtil.showToast(AddBookOrderActivity.this, "不能预约这个时间");
                    //getActivity();
                }
            }

        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookOrderActivity.this);
        //getActivity()
        View view = View.inflate(AddBookOrderActivity.this,
                R.layout.dialog_data_time_picker, null);
        //getActivity()
        final TimePicker timePicker = (TimePicker) view
                .findViewById(R.id.time_picker);
        builder.setView(view);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + 24 * 60 * 60
                * 1000);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setIs24HourView(true);     //设置为24小时制显示
        builder.setTitle("请选择预约时间");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (timePicker.getCurrentHour() > 9 && timePicker.getCurrentHour() < 22) {
                    // 订单不能预约非营业时间
                    sb.append(timePicker.getCurrentHour());
                    text_time_show.setText(sb + ":00");//必须为整点？

                    dialog.cancel();
                    sb.delete(0, sb.length());

                } else {
                    ShowToastUtil.showToast(AddBookOrderActivity.this, "不属于店面营业时间哦");
                    //getActivity()
                }
            }

        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showRemarkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookOrderActivity.this);
        //getActivity()
        View view = View.inflate(AddBookOrderActivity.this,
                R.layout.dialog_data_remark, null);
        //getActivity()
        final EditText remark= (EditText) view
                .findViewById(R.id.edit_remark_dialog);
        builder.setView(view);

        builder.setTitle("请填写备注信息");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string_remark=remark.getText().toString();
                //request.setBook_remark(string_remark);
                Log.e("remark",string_remark);

                sb.append(string_remark);
                text_remark_show.setText(sb);
                //tv_add_order_ass.setClickable(true);
                //tv_add_order_bar.setClickable(true);
                dialog.cancel();
                sb.delete(0, sb.length());
            }

        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void initBtnListener() {

        but_exit.setOnClickListener(this);
    }
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_ass_exit:
                //必须确保request中信息全部存在，否则会出现解析错误，程序崩溃！
                if (TextUtils.isEmpty(et_id.getText())) {
                    Toast.makeText(AddBookOrderActivity.this, "请填写顾客编号！", Toast.LENGTH_SHORT).show();
                } else {
                    request.setPersonal_id(et_id.getText().toString());
                    if (TextUtils.isEmpty(et_name.getText())) {
                        Toast.makeText(AddBookOrderActivity.this, "请填写顾客姓名！", Toast.LENGTH_SHORT).show();
                    } else {
                        request.setBook_name(et_name.getText().toString());
                        if (TextUtils.isEmpty(et_phone.getText())) {
                            Toast.makeText(AddBookOrderActivity.this, "请填写顾客手机！", Toast.LENGTH_SHORT).show();
                        } else {
                            request.setBook_phone_num(et_phone.getText().toString());
                            if (TextUtils.isEmpty(text_date_show.getText())) {
                                Toast.makeText(AddBookOrderActivity.this, "请选择预约日期！", Toast.LENGTH_SHORT).show();

                            } else {
                                bookTime = text_time_show.getText().toString();
                                if (TextUtils.isEmpty(text_time_show.getText())) {
                                    Toast.makeText(AddBookOrderActivity.this, "请选择预约时间！", Toast.LENGTH_SHORT).show();
                                } else {
                                    bookTime = bookTime + text_time_show.getText().toString();
                                    request.setBook_time(bookTime);
                                    if (TextUtils.isEmpty(text_remark_show.getText())) {
                                        Toast.makeText(AddBookOrderActivity.this, "请添加备注", Toast.LENGTH_SHORT).show();
                                    } else {
                                        request.setBook_remark(text_remark_show.getText().toString());
                                        openNetworkManager();//开始网络请求！
                                    }
                                }
                            }
                        }
                    }
                }


        }
    }

    //  private void testRequest(){
    //    request.setBook_remark("heh");
    //   request.setPersonal_id("22");
    //   request.setBook_name("sss");
    //   request.setBook_phone_num("11111111");
    //   request.setBook_time("2222");
    // }

    private void openNetworkManager(){

        NetworkManager.getInstance().post(MethodConstant.ADD_BOOK_ORDER, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                AddBookOrderResponse response = (AddBookOrderResponse) receive.getResponse();
                if (response.getExe_success() == 1 && response.getAdd_book_status().equals("success")) {
                    Toast.makeText(context, "添加成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "添加失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
                Toast.makeText(context, "添加失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        },AddBookOrderResponse.class);
    }



}
