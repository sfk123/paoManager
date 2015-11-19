package com.shengping.paomanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 商家体现
 * @author Administrator
 *
 */
public class Activity_Enchashment extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enchashment);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("我要提现");
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back) {
			finish();
		}
		
	}
}
