package com.shengping.paomanager.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.paomanager.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter_Balance_Business extends BaseAdapter{
	private LayoutInflater inflater;
	private JSONArray data_json;
	public Adapter_Balance_Business(Context context,JSONArray data_json){
		this.data_json=data_json;
		inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data_json.length();
	}

	@Override
	public JSONObject getItem(int arg0) {
		// TODO Auto-generated method stub
		try {
			return data_json.getJSONObject(arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		viewHoldedr holder;
		if(convertView==null){
			holder=new viewHoldedr();
			convertView=inflater.inflate(R.layout.item_balance_month, null);
			holder.tv_day_num=(TextView)convertView.findViewById(R.id.tv_day_num);
			holder.tv_count=(TextView)convertView.findViewById(R.id.tv_count);
			convertView.setTag(holder);
		}else{
			holder=(viewHoldedr)convertView.getTag();
		}
		try {
			JSONObject json=data_json.getJSONObject(position);
			holder.tv_day_num.setText(json.getInt("day")+"ºÅ£º");
			holder.tv_count.setText(json.getInt("count")+"µ¥");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	class viewHoldedr{
		TextView tv_day_num,tv_count;
	}
}
