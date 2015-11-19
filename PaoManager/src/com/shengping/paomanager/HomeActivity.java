package com.shengping.paomanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeActivity extends Activity implements OnClickListener{
	private ImageView img_logo;
	private LinearLayout lable_pusher,lable_business;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		img_logo=(ImageView)findViewById(R.id.img_logo);
		lable_pusher=(LinearLayout)findViewById(R.id.lable_pusher);
		lable_business=(LinearLayout)findViewById(R.id.lable_business);
		lable_business.setOnClickListener(this);
		lable_pusher.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent=new Intent(this,Activity_Login.class);
		if(v.getId()==R.id.lable_pusher){
			intent.putExtra("type", "pusher");
		}else if(v.getId()==R.id.lable_business){
			intent.putExtra("type", "business");
		}
		startActivity(intent);
	}

	
}
