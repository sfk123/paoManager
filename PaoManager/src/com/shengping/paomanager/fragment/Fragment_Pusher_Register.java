package com.shengping.paomanager.fragment;
import java.io.File;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.paomanager.Activity_Login;
import com.shengping.paomanager.R;
import com.shengping.paomanager.popup.Popup_UploadPhoto;
import com.shengping.paomanager.popup.Select_City;
import com.shengping.paomanager.popup.Select_City.cityselectListener;
import com.shengping.paomanager.popup.Select_Transportation;
import com.shengping.paomanager.popup.Select_Transportation.SelectListener;
import com.shengping.paomanager.util.MyUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment_Pusher_Register  extends Fragment implements OnClickListener,cityselectListener,SelectListener{

	private ImageView upload_1,upload_example_1,upload_2,upload_example_2,upload_3,upload_example_3;
	private ImageView img_upload_head;
	private View contentView;
	private TextView tv_address,tv_traffic;
	
	private int[] windowSize;
	private Popup_UploadPhoto UploadPhoto;
	private Select_City selectCity;
	private Select_Transportation selectTran;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_pusher_register, null);
		upload_1=(ImageView)contentView.findViewById(R.id.upload_1);
		upload_example_1=(ImageView)contentView.findViewById(R.id.upload_example_1);
		upload_2=(ImageView)contentView.findViewById(R.id.upload_2);
		upload_example_2=(ImageView)contentView.findViewById(R.id.upload_example_2);
		upload_3=(ImageView)contentView.findViewById(R.id.upload_3);
		upload_example_3=(ImageView)contentView.findViewById(R.id.upload_example_3);
		tv_address=(TextView)contentView.findViewById(R.id.tv_address);
		tv_traffic=(TextView)contentView.findViewById(R.id.tv_traffic);
		
		
		img_upload_head=(ImageView)contentView.findViewById(R.id.img_upload_head);
		
		setClickListener(img_upload_head,contentView.findViewById(R.id.lable_address),contentView.findViewById(R.id.lable_traffice));
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  windowSize=MyUtil.getWindowSize(getActivity());
		  int imgWidth=(windowSize[0]-MyUtil.dip2px(getContext(), 60))/2;
		  setWidth(imgWidth,upload_1,upload_example_1,upload_2,upload_example_2,upload_3,upload_example_3);
		  UploadPhoto=new Popup_UploadPhoto(getActivity());
		  selectCity=new Select_City(getContext());
		  selectCity.setListener(this);
		  selectTran=new Select_Transportation(getActivity());
		  selectTran.setSelectListener(this);
	}
	private void setWidth(int widht,View...views){
		for(View v:views){
			v.getLayoutParams().width=widht;
		}
	}
	
	private void setClickListener(View...views){
		for(View v:views){
			v.setOnClickListener(this);
		}
	}
	public void setHeadImage(Bitmap bm){
		img_upload_head.setImageBitmap(bm);
	}
	public void hidePopup(){
		if(UploadPhoto!=null){
			UploadPhoto.dismiss();
		}
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.img_upload_head){
			UploadPhoto.builder(this).show();
		}else if(v.getId()==R.id.xiangji){
			Activity_Login.getInstence().startCarama();
		}else if(v.getId()==R.id.xiangce){
			UploadPhoto.dismiss();
			Activity_Login.getInstence().startXiangce();
		}else if(v.getId()==R.id.lable_address){
			selectCity.builder().show();
		}else if(v.getId()==R.id.lable_traffice){
			selectTran.builder().show();
		}
	}
	@Override
	public void selectok(JSONObject json) {
		try {
			tv_address.setText(json.getString("mCurrentProviceName")+" "+json.getString("mCurrentCityName")+" "+json.getString("mCurrentDistrictName"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void select_ok(String s) {
		tv_traffic.setText(s);
	}
	
}
