package com.wiipu.beautysalon_new_v2.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.adapter.AddOrderAdapter;
import com.wiipu.beautysalon_new_v2.constants.MethodConstant;
import com.wiipu.beautysalon_new_v2.data.BookOrderData;
import com.wiipu.beautysalon_new_v2.network.NetworkManager;
import com.wiipu.beautysalon_new_v2.network.beans.ErrorHook;
import com.wiipu.beautysalon_new_v2.network.beans.JsonReceive;
import com.wiipu.beautysalon_new_v2.network.beans.ResponseHook;
import com.wiipu.beautysalon_new_v2.request.GetBookOrderRequest;
import com.wiipu.beautysalon_new_v2.response.GetBookOrderResponse;
import com.wiipu.beautysalon_new_v2.utils.SaveLoginStatusUtil;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * 服务记录的fragment
 * Modified by xxx on 2016.3.18
 * Modified by xxx on 2016.3.27 共用AddOrderAdapter进行显示内容
 */
public class ServiceLogFragment extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout linearLayout;
    private ListView listView;
    private AddOrderAdapter adapter;
    private ArrayList<BookOrderData>  arrayList=new ArrayList<BookOrderData>();
    private String queryDate;


    public ServiceLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_service_log, container, false);
        initView();
        return view;
    }

    private void initView(){
        linearLayout=(LinearLayout)view.findViewById(R.id.ll_server_log_time);
        linearLayout.setOnClickListener(this);
        listView=(ListView)view.findViewById(R.id.lv_service_log);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ll_server_log_time:
                showDateDialog();
        }
    }

    private void showDateDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view=View.inflate(getActivity(),R.layout.dialog_data_date_picker,null);
        final DatePicker datePicker=(DatePicker)view.findViewById(R.id.date_picker);
        builder.setView(view);
        final Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis() + 24 * 60 * 60
                * 1000);
        datePicker.init(calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH), null);
        builder.setTitle("请选择查询日期");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                queryDate = String.format("%d-%02d-%02d",
                        datePicker.getYear(), datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth());
                Log.d("testDate", queryDate);
                GetBookOrderRequest request = new GetBookOrderRequest();
                request.setDate(queryDate);
                String personal_id = SaveLoginStatusUtil.loadPersonal_id(getContext());
                request.setPersonal_id(personal_id);
                openNetWorkManager(request);//进行网络请求获取订单


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.show();


    }

    private void openNetWorkManager(GetBookOrderRequest request){

        NetworkManager.getInstance().post(MethodConstant.ADD_BOOK_ORDER, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {

                GetBookOrderResponse response = (GetBookOrderResponse) receive.getResponse();
                if (response != null && response.getExe_success() == 1) {
                    arrayList = response.getBook_order_list();
                    listView=(ListView)view.findViewById(R.id.lv_service_log);
                    adapter=new AddOrderAdapter(context,arrayList,R.layout.item_order_list);
                    listView.setAdapter(adapter);
                    //需要新建Adapter并对内容进行排序显示
                } else {
                    Toast.makeText(context, "获取数据失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

                Toast.makeText(context, "获取数据失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        },GetBookOrderResponse.class);
    }
}
