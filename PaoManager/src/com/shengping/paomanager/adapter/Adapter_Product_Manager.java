package com.shengping.paomanager.adapter;

import java.util.List;

import com.shengping.paomanager.R;
import com.shengping.paomanager.model.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Product_Manager extends BaseAdapter{
	private LayoutInflater inflater;
	private List<Product> listdata;
	public Adapter_Product_Manager(Context context,List<Product> listdata){
		inflater=LayoutInflater.from(context);
		this.listdata=listdata;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listdata.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_product_manager, null);
			holder.img=(ImageView)convertView.findViewById(R.id.img_product);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		Product product=listdata.get(position);
		holder.img.setImageResource(R.drawable.temp);
		holder.tv_name.setText(product.getName());
		holder.tv_price.setText("гд"+product.getPrice());
		return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView tv_name,tv_price;
	}
}
