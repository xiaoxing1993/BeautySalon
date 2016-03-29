package com.wiipu.beautysalon_new_v2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wiipu.beautysalon_new_v2.R;
import com.wiipu.beautysalon_new_v2.data.ServiceItemsData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ken~Jc on 2016/3/19.
 * 适配器，用于实现所有服务的复选，添加订单时可以选择多个服务
 * 其中，getIsSelect()返回的是当前listview中所选择的item的数据表
 * 类型为HashMap
 */
public class AllItemsAdapter extends BaseAdapter {

//    填充数据的list
    private ArrayList<ServiceItemsData> list;
//    用来储存CheckBox的选中状况
    private static HashMap<Integer,Boolean> isSelected;
//    上下文
    private Context context;
//    用来导入布局
    private LayoutInflater inflater=null;

//    构造器
    public AllItemsAdapter(ArrayList<ServiceItemsData> list,Context context){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
        isSelected=new HashMap<Integer,Boolean>();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static HashMap<Integer,Boolean> getIsSelected(){
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer,Boolean> isSelected){
        AllItemsAdapter.isSelected=isSelected;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder=null;
//        初始化数据，在构造器示例化时不能获取list的长度，
//        故不能对hashmap进行初始化，产生null导致报错，但无法获取的原因未知
        for (int i=0;i<list.size();i++){
            if (getIsSelected().get(position)==null){
                getIsSelected().put(i,false);
            }
        }

        if (convertView==null){
//            获得MyViewHolder对象
            holder=new MyViewHolder();
//            导入布局并赋值给convertView
            convertView=inflater.inflate(R.layout.item_all_services_item,null);
            holder.tvId=(TextView)convertView.findViewById(R.id.tv_service_item_id);
            holder.tvName=(TextView)convertView.findViewById(R.id.tv_service_item_name);
            holder.tvPrice=(TextView)convertView.findViewById(R.id.tv_service_item_price);
            holder.cbItem=(CheckBox)convertView.findViewById(R.id.cb_service_item_cb);

//            为view设置标签
            convertView.setTag(holder);
        }else {
//            取出holder
            holder=(MyViewHolder)convertView.getTag();
        }
//        设置list中的TextView的显示
        holder.tvId.setText(list.get(position).getItem_id());
        holder.tvName.setText(list.get(position).getItem_name());
        holder.tvPrice.setText(list.get(position).getItem_price());
//        根据isSelected来设置checkbox的选中状况
        holder.cbItem.setChecked(getIsSelected().get(position));
        return convertView;
    }
}
