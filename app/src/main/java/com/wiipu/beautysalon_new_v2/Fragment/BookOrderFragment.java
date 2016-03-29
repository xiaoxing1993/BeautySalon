package com.wiipu.beautysalon_new_v2.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * 预约订单fragment
 * Modified by xxx on 2016.3.18
 * Modified by xxx on 2016.3.27 把adapter添加进ListView中
 */
public class BookOrderFragment extends Fragment {

    private Activity activity;
    private ListView listView;
    private View view ;
    private AddOrderAdapter adapter;
    private ArrayList<BookOrderData> arrayList=new ArrayList<BookOrderData>();



    public BookOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_book_order, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(SaveLoginStatusUtil.loadPosition(getActivity()).equals("1")){
            GetBookOrderRequest request=new GetBookOrderRequest();
            request.setPersonal_id(SaveLoginStatusUtil.loadPersonal_id(getActivity()));
            NetworkManager.getInstance().post(MethodConstant.ADD_BOOK_ORDER, request, new ResponseHook() {
                @Override
                public void deal(Context context, JsonReceive receive) {
                    GetBookOrderResponse response = (GetBookOrderResponse) receive.getResponse();
                    if (response != null) {
                        if (response.getExe_success() == 1) {

                            arrayList=response.getBook_order_list();
                            adapter=new AddOrderAdapter(context,arrayList,R.layout.item_order_list);
                            listView=(ListView)view.findViewById(R.id.list_order);
                            listView.setAdapter(adapter);
                        }
                    }else{
                        Toast.makeText(context,"获取数据失败，请重试！",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new ErrorHook() {
                @Override
                public void deal(Context context, VolleyError error) {

                }
            },GetBookOrderResponse.class);
        }
    }
}
