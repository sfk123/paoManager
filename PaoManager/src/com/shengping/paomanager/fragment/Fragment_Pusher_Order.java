package com.shengping.paomanager.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shengping.paomanager.R;
import com.shengping.paomanager.adapter.Adapter_Pusher_Order;
import com.shengping.paomanager.model.Business;
import com.shengping.paomanager.model.Order_Pusher;
import com.shengping.paomanager.view.XListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_Pusher_Order extends Fragment {
	private XListView mylist;
	private TextView tv_worker,tv_title;
	private List<Order_Pusher> orderList;
	private Adapter_Pusher_Order adapter;
	private boolean iswork=false;
	public Fragment_Pusher_Order(boolean iswork){
		this.iswork=iswork;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView=inflater.inflate(R.layout.fragment_pusher_order, null);
		tv_title=(TextView)contentView.findViewById(R.id.tv_title);
		tv_title.setText("订单中心");
		contentView.findViewById(R.id.btn_back).setVisibility(View.GONE);
		mylist=(XListView)contentView.findViewById(R.id.mylist);
		mylist.setPullRefreshEnable(true);
		tv_worker=(TextView)contentView.findViewById(R.id.tv_worker);
		if(iswork)
			tv_worker.setVisibility(View.GONE);
		orderList=new ArrayList<>();
		for(int i=0;i<5;i++){
			Order_Pusher order=new Order_Pusher();
			order.setAddress("金华市婺城区-城中街道-新华街301号-浙中信息产业园-信息大厦-807");
			order.setDate(new Date());
			order.setOrderNum("4569879156");
			order.setPayType("在线支付");
			order.setSex("女士");
			order.setStatus(1);
			order.setTotal(25.5);
			order.setUserName("张静静");
			order.setUserPhone("18726239709");
			List<Business> bs=new ArrayList<>();
			for(int j=0;j<3;j++){
				Business b=new Business();
				b.setAddress("八一路111号");
				b.setName("麦当劳");
				bs.add(b);
			}
			order.setBusinesses(bs);
			orderList.add(order);
		}
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  adapter=new Adapter_Pusher_Order(getContext(), orderList);
		  mylist.setAdapter(adapter);
	}
}
