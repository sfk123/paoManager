package com.shengping.paomanager.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shengping.paomanager.R;
import com.shengping.paomanager.adapter.Adapter_Business_Order;
import com.shengping.paomanager.model.Order_Business;
import com.shengping.paomanager.model.Product;
import com.shengping.paomanager.view.XListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_Business_Order  extends Fragment{

	private XListView mylist;
	private List<Order_Business> dataList;
	private Adapter_Business_Order adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView=inflater.inflate(R.layout.fragment_business_order, null);
		contentView.findViewById(R.id.btn_back).setVisibility(View.GONE);
		TextView tv_title=(TextView)contentView.findViewById(R.id.tv_title);
		tv_title.setText("订单中心");
		mylist=(XListView)contentView.findViewById(R.id.mylist);
		mylist.setPullRefreshEnable(true);
		dataList=new ArrayList<>();
		for(int i=0;i<5;i++){
			Order_Business order=new Order_Business();
			order.setAddress("金华市婺城区-城中街道-新华街301号-浙中信息产业园-信息大厦-807");
			order.setDate(new Date());
			order.setOrderNum("4569879156");
			order.setPayType("在线支付");
			order.setSex("女士");
			order.setStatus(1);
			order.setTotal(25.5);
			order.setUserName("张静静");
			order.setUserPhone("18726239709");
			List<Product> products=new ArrayList<>();
			for(int j=0;j<3;j++){
				Product p=new Product();
				p.setCount(1);
				p.setPrice(1.5);
				p.setName("茶叶蛋");
				products.add(p);
			}
			order.setProducts(products);
			dataList.add(order);
		}
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  adapter=new Adapter_Business_Order(getContext(), dataList);
		  mylist.setAdapter(adapter);
	}
}
