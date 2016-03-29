package com.wiipu.beautysalon_new_v2.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.adapter.AllItemsAdapter;
import com.wiipu.beautysalon_new_v2.adapter.BarberFreeAdapter;
import com.wiipu.beautysalon_new_v2.adapter.FreeItemsAdapter;
import com.wiipu.beautysalon_new_v2.adapter.MyViewHolder;
import com.wiipu.beautysalon_new_v2.constants.MethodConstant;
import com.wiipu.beautysalon_new_v2.data.BarberFreeTimeData;
import com.wiipu.beautysalon_new_v2.data.CustomerFreeItemsData;
import com.wiipu.beautysalon_new_v2.data.ServiceItemsData;
import com.wiipu.beautysalon_new_v2.network.NetworkManager;
import com.wiipu.beautysalon_new_v2.network.beans.ErrorHook;
import com.wiipu.beautysalon_new_v2.network.beans.JsonReceive;
import com.wiipu.beautysalon_new_v2.network.beans.ResponseHook;
import com.wiipu.beautysalon_new_v2.request.QueryAllItemsByShopRequest;
import com.wiipu.beautysalon_new_v2.request.QueryBarbersRequest;
import com.wiipu.beautysalon_new_v2.request.QueryCustomerInfoRequest;
import com.wiipu.beautysalon_new_v2.response.QueryAllItemsByShopResponse;
import com.wiipu.beautysalon_new_v2.response.QueryBarbersResponse;
import com.wiipu.beautysalon_new_v2.response.QueryCustomerInfoResponse;
import com.wiipu.beautysalon_new_v2.ui.AddOrderActivity;
import com.wiipu.beautysalon_new_v2.ui.AssistantActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.sql.StatementEvent;

/**
 * Created by Ken~Jc on 2016/3/6.
 * dialog工具类，用于显示所有的自定义dialog
 */

public class DialogUtils {

    //    下班的dialog
    public static void showKnockOffDialog(final Context context) {
        View view = View.inflate(context, R.layout.dialog_off_work, null);
        final AlertDialog dialog_off = new AlertDialog.Builder(context)
                .setView(view).create();
        TextView tv_dialog_off_work_time = (TextView) view
                .findViewById(R.id.tv_dialog_off_work_time);
        Button bt_dialog_off_work_enter = (Button) view
                .findViewById(R.id.bt_dialog_off_work_enter);
        Button bt_dialog_off_work_cancel = (Button) view
                .findViewById(R.id.bt_dialog_off_work_cancel);

        //通过SimpleDateFormat获取24小时制时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        tv_dialog_off_work_time.setText(currentTime);
        bt_dialog_off_work_enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_off.cancel();
//                PollingUtils.stopPollingService(context,
//                        MyService.class);
//                if (SalonConstants.MY_POISION.equals("manager") || SalonConstants.MY_POISION.equals("prostage")) {
//                    MainActivity.instance.finish();
//                } else if (SalonConstants.MY_POISION.equals("barber")) {
//                    BarberActivity.instance.finish();
//                } else if (SalonConstants.MY_POISION.equals("assistant")) {
//                    AssistantActivity.instance.finish();
//                }
                //程序退出时移除未成功的网络队列
                NetworkManager.getInstance().remove();
                System.exit(0);
            }
        });

        bt_dialog_off_work_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_off.cancel();
            }
        });
        dialog_off.show();
    }

    //    显示用户信息的dialog
    public static void showCustomerInfoDialog(final Context context) {

        final View view = View.inflate(context, R.layout.dialog_customer_info, null);

        final AlertDialog dialog_cus = new AlertDialog.Builder(context)
                .setView(view).create();

        //        IconFont初始化设置
        Typeface iconfont=Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");

        //所有控件
        final ImageView btn_close = (ImageView) view.findViewById(R.id.btn_close_customer_info);
        final RelativeLayout btn_free_item = (RelativeLayout) view.findViewById(R.id.rlayout_free_items_customer_info);
        final LinearLayout display_all_free_item = (LinearLayout) view.findViewById(R.id.llayout_free_items_customer_info);
        final LinearLayout otherInfo = (LinearLayout) view.findViewById(R.id.ll_other_customer_info);
        final Button btn_add_order = (Button) view.findViewById(R.id.btn_add_order_customer_info);
        final TextView next=(TextView)view.findViewById(R.id.icon_next);

        next.setTypeface(iconfont);

        //存放免费服务的数组
        final ArrayList<CustomerFreeItemsData> list = new ArrayList<CustomerFreeItemsData>();

        //是否可以查询的标志
        final boolean[] queryFlag = {false};
        //所有个人信息
        final TextView phoneNum = (TextView) view.findViewById(R.id.text_phone_num_customer_info);
        final TextView customerId = (TextView) view.findViewById(R.id.text_id_customer_info);
        final TextView name = (TextView) view.findViewById(R.id.text_name_customer_info);
        final TextView sex = (TextView) view.findViewById(R.id.text_sex_customer_info);
        final TextView totalMoney = (TextView) view.findViewById(R.id.text_total_money_customer_info);
        final TextView leftMoney = (TextView) view.findViewById(R.id.text_left_money_customer_info);
        final TextView leftCoins = (TextView) view.findViewById(R.id.text_left_coins_customer_info);
        final ListView service = (ListView) view.findViewById(R.id.list_free_item_customer_info);

        //关闭按钮的监听
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cus.cancel();
                queryFlag[0] = false;
            }
        });

//        是否显示所有免费服务信息的按钮
        btn_free_item.setOnClickListener(new View.OnClickListener() {
            //计数器实现点击切换显示与否
            private int count = 0;

            @Override
            public void onClick(View v) {
                count++;
                if (count % 2 == 1)
                    display_all_free_item.setVisibility(View.VISIBLE);
                else
                    display_all_free_item.setVisibility(View.INVISIBLE);
                if (count == 2) count = 0;
            }
        });

//        在查询客户信息时直接添加订单的按钮
        btn_add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_add = new Intent(context, AddOrderActivity.class);
                context.startActivity(intent_add);
                //dialog_cus.cancel();
            }
        });

        //取出查询页面的id或者手机号，应增加处理，每次查询只能填一种
        final SharedPreferences sp = context.getSharedPreferences("runningTemp", Context.MODE_PRIVATE);
        final String num = sp.getString("phoneNumFromQuery", null);
        final String id = sp.getString("customerIdFromQuery", null);

        //请求数据
        QueryCustomerInfoRequest request = new QueryCustomerInfoRequest();
        //从sp取出的id或手机号置入网络请求的request中
        if ((id.equals("")) & (num.equals(""))) {
            Log.e("填写错误", "不含数据");
            Toast.makeText(context, "您尚未填写任何信息，无法查询！", Toast.LENGTH_SHORT).show();
        } else if (num.equals("")) {
            request.setCustomer_id(id);
            request.setCustomer_phone_num("");
            queryFlag[0] = true;
        } else if (id.equals("")) {
            request.setCustomer_id("");
            request.setCustomer_phone_num(num);
            queryFlag[0] = true;
        } else {
            Log.e("同时填入两个数据", "不能查询");
            Toast.makeText(context, "同时填入两个数据,不能查询", Toast.LENGTH_SHORT).show();
        }

        if (queryFlag[0]) {
            NetworkManager.getInstance().post(MethodConstant.QUERY_CUSTOMER_INFO, request, new ResponseHook() {
                @Override
                public void deal(Context context, JsonReceive receive) {
                    QueryCustomerInfoResponse response = (QueryCustomerInfoResponse) receive.getResponse();

                    if (response != null) {
                        //更新获取到的用户信息
                        phoneNum.setText(response.getCustomer_phone_num());
                        customerId.setText(response.getCustomer_id());
                        name.setText(response.getCustomer_name());
                        sex.setText(response.getCustomer_sex());
                        totalMoney.setText(response.getCustomer_total_money());
                        leftMoney.setText(response.getCustomer_left_money());
                        leftCoins.setText(response.getCustomer_left_coins());
                        if (response.getList() != null) {
                            //数组是否为空
                            list.addAll(response.getList());
                            //设置适配器到用户持有的免费项目，必须在请求数据且确认数据非空之后才能设置适配器
                            FreeItemsAdapter adapter = new FreeItemsAdapter(context, list, R.layout.item_customer_free_item);
                            service.setAdapter(adapter);
                        }
                        Log.d("个人信息数据传入", "成功");
                        //显示dialog，必须在网络请求之后，如果未查询到用户信息则不应该跳出dialog，暂时先显示所有
                        dialog_cus.show();
                    } else {
                        Log.e("个人信息获取", "失败");
                    }
                }
            }, new ErrorHook() {
                @Override
                public void deal(Context context, VolleyError error) {
                    //错误处理
                    Log.e("个人信息获取", "失败");
                }
            }, QueryCustomerInfoResponse.class);
        } else {
            Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
        }
        dialog_cus.show();
    }

    //    显示所有店铺服务的dialog
    public static void showAllServiceItemsDialog(final Context context) {

        final View view = View.inflate(context, R.layout.dialog_service_items, null);
        final AlertDialog dialog_off = new AlertDialog.Builder(context)
                .setView(view).create();
        final Button btn_selected = (Button) view
                .findViewById(R.id.btn_select_items_dialog);
        final ListView listView = (ListView) view.findViewById(R.id.lv_service_items);
        final int[] itemsNum = new int[1];
        itemsNum[0] = 0;
        final TextView tvTitle = (TextView) view.findViewById(R.id.tv_title_service_dialog);
        final String[] alreadySelected = new String[1];
        final ArrayList<ServiceItemsData> list = new ArrayList<ServiceItemsData>();
        final AllItemsAdapter mAdapter;

//        网络请求，查询店铺所有服务
        QueryAllItemsByShopRequest request = new QueryAllItemsByShopRequest();
//        店铺id暂时用031代替
        request.setShop_id("031");
        NetworkManager.getInstance().post(MethodConstant.QUERY_ALL_ITEMS_BY_SHOP, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                QueryAllItemsByShopResponse response = (QueryAllItemsByShopResponse) receive.getResponse();
                Log.d("查询所有服务", "");
                if (response != null && response.getList() != null) {
                    list.addAll(response.getList());
                    Log.d("列表传入：", "成功");
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

//                错误处理
            }
        });

//        所有网络请求之后，实例化自定义适配器并绑定到 listView
        mAdapter = new AllItemsAdapter(list, context);
        listView.setAdapter(mAdapter);
//        刷新listView的适配器
        //mAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                MyViewHolder holder = (MyViewHolder) view.getTag();
//                改变CheckBox的状态
                holder.cbItem.toggle();
//                将CheckBox的状态记录下来
                AllItemsAdapter.getIsSelected().put(position, holder.cbItem.isChecked());
//                记录已经选择的服务的id
                alreadySelected[0] = String.valueOf(AllItemsAdapter.getIsSelected());

//                调整选定条目
                if (holder.cbItem.isChecked() == true) {
                    itemsNum[0]++;
                } else {
                    itemsNum[0]--;
                }
                tvTitle.setText("当前选择了" + itemsNum[0] + "项服务");
//                刷新listView的适配器
                mAdapter.notifyDataSetChanged();
            }
        });
        btn_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                保存所选数据并关闭dialog
                dialog_off.cancel();
            }
        });
        dialog_off.show();
    }

    //    显示当前空闲理发师的Dialog
    public static void showAllFreeBarberDialog(final Context context) {

        final View view = View.inflate(context, R.layout.dialog_barber_free, null);
        final AlertDialog dialog_off = new AlertDialog.Builder(context)
                .setView(view).create();
        final Button btn_selected = (Button) view
                .findViewById(R.id.btn_select_barber_dialog);
        final ListView listView = (ListView) view.findViewById(R.id.lv_barber_items);
        final ArrayList<BarberFreeTimeData> list = new ArrayList<BarberFreeTimeData>();
        final String[] selectedBarberId=new String[1];
        final BarberFreeAdapter mAdapter;

//        网络请求，查询店铺所有服务
        QueryBarbersRequest request = new QueryBarbersRequest();
//        店铺id暂时用031代替
        request.setShop_id("031");
        NetworkManager.getInstance().post(MethodConstant.QUERY_BARBERS, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                QueryBarbersResponse response = (QueryBarbersResponse) receive.getResponse();
                Log.d("查询所有空闲理发师", "");
                if (response != null && response.getList() != null) {
                    list.addAll(response.getList());
                    Log.d("列表传入：", "成功");
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

//                错误处理
            }
        });

//        所有网络请求之后，实例化自定义适配器并绑定到 listView
        mAdapter = new BarberFreeAdapter( context,list,R.layout.dialog_barber_free);
        listView.setAdapter(mAdapter);
//        刷新listView的适配器
        //mAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                获取选择的理发师Id
                selectedBarberId[0]=list.get(position).getBarber_id();
//                刷新listView的适配器
                mAdapter.notifyDataSetChanged();
            }
        });
        btn_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                将理发师Id保存待用
                final SharedPreferences sharedPreferences=context.getSharedPreferences("orderInfo",Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor=sharedPreferences.edit();

                editor.putString("barber_id",selectedBarberId[0]);

//                保存所选数据并关闭dialog
                dialog_off.cancel();
            }
        });
        dialog_off.show();
    }

    //    订单确认的dialog
    public static void showCommitDialog(final Context context) {
        View view = View.inflate(context, R.layout.dialog_order_commit, null);
        final AlertDialog dialogCommit = new AlertDialog.Builder(context)
                .setView(view).create();
        TextView orderId = (TextView) view
                .findViewById(R.id.tv_commit_id);
        Button btnCommit = (Button) view
                .findViewById(R.id.btn_commit);
        Button btnCancel = (Button) view
                .findViewById(R.id.btn_cancel);
        String finalOrderId="";

        orderId.setText(finalOrderId);

        btnCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogCommit.cancel();
                ((Activity)context).finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogCommit.cancel();
            }
        });
        dialogCommit.show();
    }

}
