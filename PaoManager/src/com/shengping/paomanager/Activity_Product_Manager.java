package com.shengping.paomanager;
import java.util.ArrayList;
import java.util.List;

import com.shengping.paomanager.R;
import com.shengping.paomanager.adapter.Adapter_Product_Manager;
import com.shengping.paomanager.model.Product;
import com.shengping.paomanager.view.XListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class Activity_Product_Manager extends Activity implements OnClickListener,OnItemClickListener{
	private TextView tv_btn_add;
	private XListView mylist;
	private Adapter_Product_Manager adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_manager);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("商品管理");
		
		tv_btn_add=(TextView)findViewById(R.id.tv_btn_add);
		tv_btn_add.setOnClickListener(this);
		mylist=(XListView)findViewById(R.id.mylist);
		mylist.setPullRefreshEnable(true);
		mylist.setPullLoadEnable(false);
		List<Product> listdata=new ArrayList<>();
		for(int i=0;i<5;i++){
			Product p=new Product();
			p.setName("茶叶蛋");
			p.setPrice(1.5);
			listdata.add(p);
		}
		adapter=new Adapter_Product_Manager(this, listdata);
		mylist.setAdapter(adapter);
		mylist.setOnItemClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.tv_btn_add){
			Intent intent=new Intent(this,Activity_AddProduct.class);
			startActivity(intent);
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent=new Intent(this,Activity_EditProduct.class);
		startActivity(intent);
	}
}
