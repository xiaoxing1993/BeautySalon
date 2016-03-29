package com.wiipu.beautysalon_new_v2.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.application.MyApplication;
import com.wiipu.beautysalon_new_v2.constants.MethodConstant;
import com.wiipu.beautysalon_new_v2.data.CustomerFreeItemsData;
import com.wiipu.beautysalon_new_v2.network.NetworkManager;
import com.wiipu.beautysalon_new_v2.network.beans.ErrorHook;
import com.wiipu.beautysalon_new_v2.network.beans.JsonReceive;
import com.wiipu.beautysalon_new_v2.network.beans.ResponseHook;
import com.wiipu.beautysalon_new_v2.request.QueryCustomerInfoRequest;
import com.wiipu.beautysalon_new_v2.response.QueryCustomerInfoResponse;
import com.wiipu.beautysalon_new_v2.utils.DialogUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * 查询客户的fragment
 */
public class QueryCustomerFragment extends Fragment {

    private TextView btnQueryCustomer;
    private View m_fragment_query_customer;
    private EditText editText_phone;
    private EditText editText_customer_id;

    private String id;
    private String phone;
    private String sex;
    private String name;
    private String totalMoney;
    private String leftMoney;
    private String leftCoins;
    private ArrayList<CustomerFreeItemsData> list;

    public QueryCustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        m_fragment_query_customer=inflater.inflate(R.layout.fragment_query_customer, container, false);
        initView();
        return m_fragment_query_customer;

    }

    private void initView() {

        btnQueryCustomer=(TextView)m_fragment_query_customer.findViewById(R.id.image_query_customer_fragment);
        //        IconFont初始化设置
        Typeface iconfont=Typeface.createFromAsset(getActivity().getAssets(), "iconfont/iconfont.ttf");
        btnQueryCustomer.setTypeface(iconfont);
        btnQueryCustomer.setTextColor(getResources().getColor(R.color.btn_true));
        btnQueryCustomer.setTextSize(25);
        btnQueryCustomer.setOnClickListener(query_customer);

        editText_customer_id=(EditText)m_fragment_query_customer.findViewById(R.id.edit_customer_id_query_info);
        editText_phone=(EditText)m_fragment_query_customer.findViewById(R.id.edit_phone_num_query_info);

    }

    private View.OnClickListener query_customer=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            id=String.valueOf(editText_customer_id.getText());
            phone=String.valueOf(editText_phone.getText());

            savePhoneNumFromQuery(id,phone);

            //查询客户信息，若信息存在则跳转到用户详细信息中，否则弹出提示，该用户没有信息（不是会员）
            queryInfo();

            DialogUtils.showCustomerInfoDialog(getContext());
        }

        private void queryInfo() {
            QueryCustomerInfoRequest request=new QueryCustomerInfoRequest();
            request.setCustomer_id(id);
            request.setCustomer_phone_num(phone);
            NetworkManager.getInstance().post(MethodConstant.QUERY_CUSTOMER_INFO, request, new ResponseHook() {
                @Override
                public void deal(Context context, JsonReceive receive) {
                    QueryCustomerInfoResponse response = (QueryCustomerInfoResponse) receive.getResponse();


                }
            }, new ErrorHook() {
                @Override
                public void deal(Context context, VolleyError error) {
                    Log.d("查询用户","执行");
                }
            });
        }
    };



    //保存需要查询的手机号码或者会员编号，以便在查询跳出的窗口中显示和查询相关信息
    private void savePhoneNumFromQuery(String phoneNumFromQuery,String customerId) {

        SharedPreferences sp=getActivity().getSharedPreferences("runningTemp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        editor.putString("phoneNumFromQuery", phoneNumFromQuery);
        editor.putString("customerIdFromQuery",customerId);
        editor.commit();
    }

}
