package com.shengping.paomanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class Activity_Analyse extends Activity implements OnClickListener{

	private TableLayout lable_talbe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analyse);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("营业统计");
		
		lable_talbe=(TableLayout)findViewById(R.id.lable_talbe);
		JSONArray data_json=new JSONArray();
		try{
		for(int i=0;i<10;i++){
			JSONObject json=new JSONObject();
			json.put("name", "商品"+(i+1));
			json.put("price", 12.5);
			json.put("count", 125);
			data_json.put(json);
		}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		LayoutInflater inflater=LayoutInflater.from(this);
		try{
		for(int i=0;i<data_json.length();i++){
			JSONObject json=data_json.getJSONObject(i);
			View v=inflater.inflate(R.layout.tablerow_analyse, null);
			TextView tv_name=(TextView)v.findViewById(R.id.tv_name);
			tv_name.setText(json.getString("name"));
			TextView tv_price=(TextView)v.findViewById(R.id.tv_price);
			tv_price.setText("￥"+json.getDouble("price"));
			TextView tv_count=(TextView)v.findViewById(R.id.tv_count);
			tv_count.setText(json.getInt("count")+"");
			lable_talbe.addView(v);
		}
		}catch(JSONException e){
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
