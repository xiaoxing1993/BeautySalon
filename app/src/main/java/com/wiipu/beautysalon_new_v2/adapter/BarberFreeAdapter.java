package com.wiipu.beautysalon_new_v2.adapter;

import android.content.Context;
import android.widget.TextView;

import com.mtxc.universallistview.UniversalAdapter;
import com.mtxc.universallistview.ViewHolder;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.data.BarberFreeTimeData;

import java.util.ArrayList;

/**
 * Created by Ken~Jc on 2016/3/20.
 * 当前空闲的理发师的适配器Adapter
 */
public class BarberFreeAdapter extends UniversalAdapter<BarberFreeTimeData>{

    public BarberFreeAdapter(Context context,ArrayList<BarberFreeTimeData> datas,int itemLayoutId){
        super(context,datas,itemLayoutId);
    }
    @Override
    public void updateItem(ViewHolder viewHolder, BarberFreeTimeData barberFreeTimeData) {
        TextView id=viewHolder.getView(R.id.tv_barber_free_id);
        TextView name=viewHolder.getView(R.id.tv_barber_free_name);

        id.setText(barberFreeTimeData.getBarber_id());
        name.setText(barberFreeTimeData.getBarber_name());
    }
}
