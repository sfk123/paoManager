package com.shengping.paomanager.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.shengping.paomanager.R;
import com.shengping.paomanager.model.Business;
import com.shengping.paomanager.model.Order_Pusher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_Pusher_Order extends BaseAdapter{
	private LayoutInflater inflater;
	private List<Order_Pusher> dataList;
	private Context context;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public Adapter_Pusher_Order(Context context,List<Order_Pusher> dataList){
		inflater=LayoutInflater.from(context);
		this.dataList=dataList;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Viewholder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.item_pusher_order, null);
			holder=new Viewholder();
			holder.lable_bottom=(LinearLayout)convertView.findViewById(R.id.lable_bottom);
			holder.lable_business=(LinearLayout)convertView.findViewById(R.id.lable_business);
			holder.order_code=(TextView)convertView.findViewById(R.id.order_code);
			holder.tv_pay_type=(TextView)convertView.findViewById(R.id.tv_pay_type);
			holder.tv_order_time=(TextView)convertView.findViewById(R.id.tv_order_time);
			holder.tv_username=(TextView)convertView.findViewById(R.id.tv_username);
			holder.tv_userphone=(TextView)convertView.findViewById(R.id.tv_userphone);
			holder.tv_address=(TextView)convertView.findViewById(R.id.tv_address);
			holder.tv_total=(TextView)convertView.findViewById(R.id.tv_total);
			holder.tv_btton_getorder=(TextView)convertView.findViewById(R.id.tv_btton_getorder);
			convertView.setTag(holder);
		}else{
			holder=(Viewholder)convertView.getTag();
		}
		Order_Pusher order=dataList.get(position);
		if(order.getStatus()==3){
			holder.lable_bottom.setVisibility(View.GONE);
		}else if(order.getStatus()==2){
			holder.tv_btton_getorder.setVisibility(View.GONE);
		}
		holder.order_code.setText(order.getOrderNum());
		holder.tv_pay_type.setText(order.getPayType());
		holder.tv_order_time.setText("下单时间："+format.format(order.getDate()));
		holder.tv_username.setText(order.getUserName()+"("+order.getSex()+")");
		holder.tv_userphone.setText(order.getUserPhone());
		int i=1;
		holder.lable_business.removeAllViews();
		for(Business b:order.getBusinesses()){
			TextView tv=new TextView(context);
			tv.setTextColor(context.getResources().getColor(R.color.text_black));
			tv.setText(i+"、("+b.getName()+")"+b.getAddress());
			holder.lable_business.addView(tv);
			i++;
		}
		holder.tv_address.setText("客户地址："+order.getAddress());
		holder.tv_total.setText("￥"+order.getTotal());
		return convertView;
	}
	class Viewholder{
		LinearLayout lable_business,lable_bottom;
		TextView order_code,tv_pay_type,tv_order_time,tv_username,tv_userphone,tv_address,tv_total,tv_btton_getorder;
	}
}
