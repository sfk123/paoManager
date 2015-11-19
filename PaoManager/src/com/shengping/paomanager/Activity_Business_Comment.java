package com.shengping.paomanager;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.paomanager.adapter.Adapter_Business_Evaluate;
import com.shengping.paomanager.view.XListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Business_Comment extends Activity implements OnClickListener{
	private XListView mylist;
	private JSONArray datalist;
	private Adapter_Business_Evaluate adapter;
	private View listHeader;
	private ImageView img_btn;
	private boolean img_selected=true;//只看有内容的评论
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_comment);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("营业统计");
		
		mylist=(XListView)findViewById(R.id.mylist);
		mylist.setPullLoadEnable(true);
		datalist=new JSONArray();
		JSONObject json;
		try{
			for(int i=0;i<10;i++){
				json=new JSONObject();
				json.put("id", i);
				json.put("username", "日月教主");
				json.put("time", new Date().getTime());
				json.put("content", "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
				datalist.put(json);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		LayoutInflater inflater=LayoutInflater.from(this);
		listHeader=inflater.inflate(R.layout.business_evaluate_list_header, null);
		img_btn=(ImageView)listHeader.findViewById(R.id.img_btn);
		img_btn.setOnClickListener(this);
		adapter=new Adapter_Business_Evaluate(datalist, this);
		mylist.addHeaderView(listHeader);
		mylist.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.img_btn){
			if(img_selected){
				img_btn.setImageResource(R.drawable.select_no);
				img_selected=false;
			}else{
				img_btn.setImageResource(R.drawable.location_select);
				img_selected=true;
			}
		}
	}
}
