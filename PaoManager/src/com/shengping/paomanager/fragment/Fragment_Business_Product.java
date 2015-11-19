package com.shengping.paomanager.fragment;

import java.util.ArrayList;
import java.util.List;

import com.shengping.paomanager.Activity_AddType;
import com.shengping.paomanager.Activity_Main_Business;
import com.shengping.paomanager.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragment_Business_Product  extends Fragment implements OnClickListener{

	private LinearLayout lable_type;
	private List<String> types;
	private LayoutInflater inflater;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView=inflater.inflate(R.layout.fragment_business_product, null);
		contentView.findViewById(R.id.btn_back).setVisibility(View.GONE);
		TextView tv_title=(TextView)contentView.findViewById(R.id.tv_title);
		tv_title.setText("商品管理");
		contentView.findViewById(R.id.lable_add_type).setOnClickListener(this);
		contentView.findViewById(R.id.lable_manager).setOnClickListener(this);
		lable_type=(LinearLayout)contentView.findViewById(R.id.lable_type);
		types=new ArrayList<>();
		for(int i=0;i<5;i++){
			types.add("类别"+i);
		}
		this.inflater=inflater;
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		 for(String s:types){
			 View v=inflater.inflate(R.layout.item_product_type, null);
			 TextView tv_name=(TextView)v.findViewById(R.id.tv_name);
			 tv_name.setText(s);
			 v.setOnClickListener(new typeClick());
			 lable_type.addView(v);
		 }
	}
	private class typeClick implements OnClickListener{

		public typeClick(){
			
		}
		@Override
		public void onClick(View arg0) {
			Activity_Main_Business.getInstence().startManager(1);
		}
		
	} 
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.lable_add_type){
			Activity_Main_Business.getInstence().startAddType();
		}else if(v.getId()==R.id.lable_manager){
			Activity_Main_Business.getInstence().startManager(-1);
		}
	}
	public void addType(String type){
		 View v=inflater.inflate(R.layout.item_product_type, null);
		 TextView tv_name=(TextView)v.findViewById(R.id.tv_name);
		 tv_name.setText(type);
		 lable_type.addView(v);
	}
}
