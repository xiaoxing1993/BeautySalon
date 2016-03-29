package com.wiipu.beautysalon_new_v2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mtxc.universallistview.UniversalAdapter;
import com.mtxc.universallistview.ViewHolder;
import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.data.BookOrderData;

import java.util.List;

/**
 * Created by xxx on 2016/3/15.
 */
public class AddOrderAdapter extends UniversalAdapter<BookOrderData>{

    private Context mContext;

    @Override
    public void updateItem(ViewHolder viewHolder, final BookOrderData bookOrder) {

        viewHolder.setTextViewText(R.id.tv_order_id,bookOrder.getBook_order_id());
        viewHolder.setTextViewText(R.id.tv_order_name,bookOrder.getCustomer_name());
        viewHolder.setTextViewText(R.id.tv_order_time,bookOrder.getBook_order_start());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view =View.inflate(mContext,R.layout.dialog_order_info,null);
                TextView endTime=(TextView)view.findViewById(R.id.tv_dialog_order_endtime);
                TextView name=(TextView)view.findViewById(R.id.tv_dialog_order_name);
                TextView sex=(TextView)view.findViewById(R.id.tv_dialog_order_sex);
                TextView remark=(TextView)view.findViewById(R.id.tv_diaolog_order_remark);
                TextView phone=(TextView)view.findViewById(R.id.tv_dialog_order_tel);
                Button but=(Button)view.findViewById(R.id.btn_dialog_order_info);
                endTime.setText(bookOrder.getBook_order_end());
                name.setText(bookOrder.getCustomer_name());
                sex.setText(bookOrder.getCustomer_sex());
                remark.setText(bookOrder.getBook_order_remark());
                phone.setText(bookOrder.getCustomer_phone_num());
                final AlertDialog dialog=new AlertDialog.Builder(mContext).setView(view).create();
                dialog.show();
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



            }
        });

    }

    public AddOrderAdapter(Context context, List<BookOrderData> datas, int itemLayoutId) {
        super(context, datas, itemLayoutId);
        this.mContext = context;


    }
}
