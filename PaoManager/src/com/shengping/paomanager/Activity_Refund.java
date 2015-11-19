package com.shengping.paomanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shengping.paomanager.adapter.Adapter_Business_Order;
import com.shengping.paomanager.model.Order_Business;
import com.shengping.paomanager.model.Product;
import com.shengping.paomanager.view.XListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * �̼��˿�ҳ��
 * @author Administrator
 *
 */
public class Activity_Refund extends Activity implements OnClickListener{

	private XListView mylist;
	private List<Order_Business> dataList;
	private Adapter_Business_Order adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refund);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("�����˿�");
		
		mylist=(XListView)findViewById(R.id.mylist);
		dataList=new ArrayList<>();
		for(int i=0;i<5;i++){
			Order_Business order=new Order_Business();
			order.setAddress("�����ĳ���-���нֵ�-�»���301��-������Ϣ��ҵ԰-��Ϣ����-807");
			order.setDate(new Date());
			order.setOrderNum("4569879156");
			order.setPayType("����֧��");
			order.setSex("Ůʿ");
			order.setStatus(4);
			order.setTotal(25.5);
			order.setUserName("�ž���");
			order.setUserPhone("18726239709");
			List<Product> products=new ArrayList<>();
			for(int j=0;j<3;j++){
				Product p=new Product();
				p.setCount(1);
				p.setPrice(1.5);
				p.setName("��Ҷ��");
				products.add(p);
			}
			order.setProducts(products);
			dataList.add(order);
		}
		 adapter=new Adapter_Business_Order(this, dataList);
		  mylist.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
