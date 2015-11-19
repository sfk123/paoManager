package com.shengping.paomanager.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.shengping.paomanager.R;
import com.shengping.paomanager.model.Business;
import com.shengping.paomanager.model.Order_Business;
import com.shengping.paomanager.model.Order_Pusher;
import com.shengping.paomanager.model.Product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_Business_Order extends BaseAdapter{
	private LayoutInflater inflater;
	private List<Order_Business> dataList;
	private Context context;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public Adapter_Business_Order(Context context,List<Order_Business> dataList){
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
			convertView=inflater.inflate(R.layout.item_business_order, null);
			holder=new Viewholder();
			holder.lable_bottom1=(LinearLayout)convertView.findViewById(R.id.lable_bottom1);
			holder.lable_bottom2=(LinearLayout)convertView.findViewById(R.id.lable_bottom2);
			holder.lable_product=(LinearLayout)convertView.findViewById(R.id.lable_product);
			holder.order_code=(TextView)convertView.findViewById(R.id.order_code);
			holder.tv_pay_type=(TextView)convertView.findViewById(R.id.tv_pay_type);
			holder.tv_order_time=(TextView)convertView.findViewById(R.id.tv_order_time);
			holder.tv_username=(TextView)convertView.findViewById(R.id.tv_username);
			holder.tv_userphone=(TextView)convertView.findViewById(R.id.tv_userphone);
			holder.tv_address=(TextView)convertView.findViewById(R.id.tv_address);
			holder.tv_total=(TextView)convertView.findViewById(R.id.tv_total);
			holder.tv_btton_getorder=(TextView)convertView.findViewById(R.id.tv_btton_getorder);
			holder.img_tag=(ImageView)convertView.findViewById(R.id.img_tag);
			holder.tv_status=(TextView)convertView.findViewById(R.id.tv_status);
			convertView.setTag(holder);
		}else{
			holder=(Viewholder)convertView.getTag();
		}
		Order_Business order=dataList.get(position);
		if(order.getStatus()==3){
			holder.lable_bottom1.setVisibility(View.GONE);
		}else if(order.getStatus()==2){
			holder.tv_status.setText("订单已完成");
			holder.lable_bottom1.setVisibility(View.GONE);
		}else{
			holder.img_tag.setVisibility(View.GONE);
			if(order.getStatus()==4){
				holder.lable_bottom1.setVisibility(View.GONE);
				holder.lable_bottom2.setVisibility(View.VISIBLE);
			}
		}
		holder.order_code.setText(order.getOrderNum());
		holder.tv_pay_type.setText(order.getPayType());
		holder.tv_order_time.setText("下单时间："+format.format(order.getDate()));
		holder.tv_username.setText(order.getUserName()+"("+order.getSex()+")");
		holder.tv_userphone.setText(order.getUserPhone());
		holder.lable_product.removeAllViews();
		for(Product p:order.getProducts()){
			View v=inflater.inflate(R.layout.item_product, null);
			TextView tv_name=(TextView)v.findViewById(R.id.tv_name);
			tv_name.setText(p.getName());
			TextView tv_count=(TextView)v.findViewById(R.id.tv_count);
			tv_count.setText("X"+p.getCount());
			TextView tv_price=(TextView)v.findViewById(R.id.tv_price);
			tv_price.setText("￥"+(p.getCount()*p.getPrice()));
			holder.lable_product.addView(v);
		}
		holder.tv_address.setText("地址："+order.getAddress());
		holder.tv_total.setText("￥"+order.getTotal());
		return convertView;
	}
	class Viewholder{
		ImageView img_tag;
		LinearLayout lable_product,lable_bottom1,lable_bottom2;
		TextView tv_status;
		TextView order_code,tv_pay_type,tv_order_time,tv_username,tv_userphone,tv_address,tv_total,tv_btton_getorder;
	}
}
