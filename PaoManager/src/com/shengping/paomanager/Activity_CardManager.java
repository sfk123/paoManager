package com.shengping.paomanager;

import com.shengping.paomanager.view.ClearEditText;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 商家体现
 * @author Administrator
 *
 */
public class Activity_CardManager extends Activity implements OnClickListener,OnCheckedChangeListener{
	
	private ClearEditText edt_name,edt_account,edt_address;
	private RadioGroup group_type;
	private TextView tv_notice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_manager);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("账户管理");
		
		edt_name=(ClearEditText)findViewById(R.id.edt_name);
		edt_account=(ClearEditText)findViewById(R.id.edt_account);
		edt_address=(ClearEditText)findViewById(R.id.edt_address);
		group_type=(RadioGroup)findViewById(R.id.group_type);
		group_type.setOnCheckedChangeListener(this);
		tv_notice=(TextView)findViewById(R.id.tv_notice);
		tv_notice.setText(getResources().getString(R.string.manager_notice));
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back) {
			finish();
		}
		
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		if(group_type.getCheckedRadioButtonId()==R.id.rbtn_aplay){
			edt_address.setVisibility(View.GONE);
		}else{
			edt_address.setVisibility(View.VISIBLE);
		}
	}
}
