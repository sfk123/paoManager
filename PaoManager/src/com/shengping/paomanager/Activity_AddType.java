package com.shengping.paomanager;

import com.shengping.paomanager.util.MyUtil;
import com.shengping.paomanager.view.ClearEditText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_AddType extends Activity implements OnClickListener{
	
	private TextView tv_btn;
	private ClearEditText edt_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_type);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("添加分类");
		tv_btn=(TextView)findViewById(R.id.tv_btn);
		tv_btn.setOnClickListener(this);
		edt_name=(ClearEditText)findViewById(R.id.edt_name);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.tv_btn){
			if(edt_name.getText().toString().trim().equals("")){
				MyUtil.alert("请输入要添加的分类名称！", this);
			}else{
				Intent intent=new Intent();
				intent.putExtra("type", edt_name.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	}
}
