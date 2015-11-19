package com.shengping.paomanager.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.paomanager.Activity_Orders_Day;
import com.shengping.paomanager.R;
import com.shengping.paomanager.adapter.Adapter_Balance_Business;
import com.shengping.paomanager.view.FlexiListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class Fragment_Business_Balance_Month  extends Fragment implements OnItemClickListener{

	private FlexiListView mylist;
	private TextView tv_total_count;
	private Adapter_Balance_Business adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView=inflater.inflate(R.layout.fragment_business_balance_month, null);
		
		tv_total_count=(TextView)contentView.findViewById(R.id.tv_total_count);
		mylist=(FlexiListView)contentView.findViewById(R.id.mylist);
		JSONArray data_json=new JSONArray();
		try{
		for(int i=1;i<15;i++){
			JSONObject json=new JSONObject();
			json.put("day", i);
			json.put("count", 20);
			data_json.put(json);
		}
		}catch(JSONException e){
			e.printStackTrace();
		}
		adapter=new Adapter_Balance_Business(getContext(), data_json);
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  mylist.setAdapter(adapter); 
		  mylist.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		JSONObject json=adapter.getItem(arg2);
		Intent intent=new Intent(getContext(),Activity_Orders_Day.class);
		try {
			intent.putExtra("day", json.getInt("day"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startActivity(intent);
	}
}
