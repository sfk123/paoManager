package com.shengping.paomanager.fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shengping.paomanager.Activity_Analyse;
import com.shengping.paomanager.Activity_Business_Balance;
import com.shengping.paomanager.Activity_CardManager;
import com.shengping.paomanager.Activity_Enchashment;
import com.shengping.paomanager.Activity_Refund;
import com.shengping.paomanager.MyApplication;
import com.shengping.paomanager.R;
import com.shengping.paomanager.model.Business;
import com.shengping.paomanager.util.UrlUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment_Business_Balance  extends Fragment implements OnClickListener{

	private ImageView img_logo;
	private TextView tv_name;//店铺名称
	
	private Business user_business;//商家数据
	private ImageLoader imageLoader;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView=inflater.inflate(R.layout.fragment_business_balance, null);
		contentView.findViewById(R.id.btn_back).setVisibility(View.GONE);
		TextView tv_title=(TextView)contentView.findViewById(R.id.tv_title);
		tv_title.setText("结  算");
		contentView.findViewById(R.id.lable_tixian).setOnClickListener(this);
		contentView.findViewById(R.id.lable_card_manager).setOnClickListener(this);
		contentView.findViewById(R.id.lable_tuikuan).setOnClickListener(this);
		contentView.findViewById(R.id.lable_balance).setOnClickListener(this);
		contentView.findViewById(R.id.lable_analyse).setOnClickListener(this);
		
		img_logo=(ImageView)contentView.findViewById(R.id.img_logo);
		tv_name=(TextView)contentView.findViewById(R.id.tv_name);
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  imageLoader=MyApplication.getInstence().getmImageLoader();
		  user_business=MyApplication.getInstence().getUser_business();
		  if(!user_business.getLogoUrl().equals("null")&&!user_business.getLogoUrl().equals("")){
			  imageLoader.displayImage(UrlUtil.getUrl(UrlUtil.Business_Logo)+user_business.getLogoUrl(), img_logo);
		  }
		  tv_name.setText("店铺名称："+user_business.getName());
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.lable_tixian){
			Intent intent=new Intent(getContext(),Activity_Enchashment.class);
			startActivity(intent);
		}else if(v.getId()==R.id.lable_card_manager){
			Intent intent=new Intent(getContext(),Activity_CardManager.class);
			startActivity(intent);
		}else if(v.getId()==R.id.lable_tuikuan){
			Intent intent=new Intent(getContext(),Activity_Refund.class);
			startActivity(intent);
		}else if(v.getId()==R.id.lable_balance){
			Intent intent=new Intent(getContext(),Activity_Business_Balance.class);
			startActivity(intent);
		}else if(v.getId()==R.id.lable_analyse){
			Intent intent=new Intent(getContext(),Activity_Analyse.class);
			startActivity(intent);
		}
	}
}
