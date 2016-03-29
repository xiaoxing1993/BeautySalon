package com.wiipu.beautysalon_new_v2.adapter;

import android.content.Context;
import android.widget.TextView;

import com.mtxc.universallistview.UniversalAdapter;
import com.mtxc.universallistview.ViewHolder;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.data.CustomerFreeItemsData;

import java.util.ArrayList;

/**
 * Created by Ken~Jc on 2016/3/16.
 * 用户持有的免费服务的适配器Adapter
 */
public class FreeItemsAdapter extends UniversalAdapter<CustomerFreeItemsData>{
    public FreeItemsAdapter(Context context,ArrayList<CustomerFreeItemsData> datas,int itemLayoutId){
        super(context, datas, itemLayoutId);
    }

    @Override
    public void updateItem(ViewHolder holder,CustomerFreeItemsData data){
        TextView id=holder.getView(R.id.id_free_item);
        TextView name=holder.getView(R.id.name_free_item);
        TextView price=holder.getView(R.id.price_free_item);
        TextView deadline=holder.getView(R.id.deadline_free_item);

        id.setText(data.getItem_id());
        name.setText(data.getItem_name());
        price.setText(data.getItem_price());
        deadline.setText(data.getItem_deadline());
    }
}
