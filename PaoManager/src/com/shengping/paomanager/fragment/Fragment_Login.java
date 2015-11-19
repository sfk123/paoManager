package com.shengping.paomanager.fragment;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.baidu.mapapi.model.LatLng;
import com.shengping.paomanager.Activity_Main_Business;
import com.shengping.paomanager.Activity_Main_Pusher;
import com.shengping.paomanager.MyApplication;
import com.shengping.paomanager.R;
import com.shengping.paomanager.model.Business;
import com.shengping.paomanager.popup.CityUtil;
import com.shengping.paomanager.util.MyHttp;
import com.shengping.paomanager.util.MyUtil;
import com.shengping.paomanager.util.MyHttp.MyHttpCallBack;
import com.shengping.paomanager.util.UrlUtil;
import com.shengping.paomanager.view.ClearEditText;
import com.shengping.paomanager.view.LoadingDialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_Login  extends Fragment implements TextWatcher,OnClickListener,MyHttpCallBack{
	private ClearEditText tv_name,tv_password;
	private Button btn_login;
	
	private String type;
	public Fragment_Login(String type){
		this.type=type;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView=inflater.inflate(R.layout.fragment_login, null);
		tv_name=(ClearEditText)contentView.findViewById(R.id.edit_username);
		tv_name.addTextChangedListener(this);
		tv_password=(ClearEditText)contentView.findViewById(R.id.edit_password);
		tv_password.addTextChangedListener(this);
		btn_login=(Button)contentView.findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		return contentView;
	}
	@Override
	public void afterTextChanged(Editable arg0) {
		if(!tv_name.getText().toString().equals("")&&!tv_password.getText().toString().equals("")){
			btn_login.setBackgroundColor(getResources().getColor(R.color.color_main));
		}else{
			btn_login.setBackgroundColor(getResources().getColor(R.color.btn_unuse));
		}
	}
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_login){
			if(tv_name.getText().toString().trim().equals("")){
				MyUtil.alert("请输入用户名", getContext());
			}else if(tv_password.getText().toString().trim().equals("")){
				MyUtil.alert("请输入密码", getContext());
			}else{
			if(type.equals("pusher")){
			 AlertDialog.Builder builder = new Builder(getContext());

			  builder.setMessage("要进入上班状态吗?");

			  builder.setTitle("提示");

			  builder.setPositiveButton("是",new android.content.DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
					Intent intent=new Intent(getContext(),Activity_Main_Pusher.class);
					intent.putExtra("iswork", true);
					startActivity(intent);
					getActivity().finish();
				}
			});

			  builder.setNegativeButton("否", new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
					Intent intent=new Intent(getContext(),Activity_Main_Pusher.class);
					intent.putExtra("iswork", false);
					startActivity(intent);
					getActivity().finish();
				}

			});

			 builder.create().show();
			}else{//商家登陆
				LoadingDialog.showWindow(getContext());
				MyHttp http=new MyHttp(getContext());
				Map<String, String> params=new HashMap<>();
				params.put("name", tv_name.getText().toString().trim());
				params.put("pwd", MyUtil.stringToMD5(tv_password.getText().toString().trim()));
				String url=UrlUtil.getUrl("login", UrlUtil.Service_Shop);
				http.Http_post(url, params, this);
				
			}
			}
		}
	}
	@Override
	public void onResponse(JSONObject response) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		System.out.println(response);
		try {
			if(response.getBoolean("status")){
				Business user_business=new Business();
				JSONObject data=response.getJSONObject("data");
				user_business.setAddress(data.getString("md_Address"));
				JSONObject json=CityUtil.getInstence(getContext()).getByCode(data.getString("md_Area"));
				user_business.setArea(json.getString("city")+" "+json.getString("district"));
				user_business.setDiscount(data.getString("md_Maps"));
				user_business.setLoginName(data.getString("md_LoName"));
				user_business.setLoginPwd(data.getString("md_LoPawss"));
				user_business.setName(data.getString("md_Title"));
				user_business.setPhone(new JSONArray(data.getString("hotline")));
				user_business.setStar(data.getInt("md_CTXJ"));
				user_business.setStatus(data.getInt("md_ISTJ"));
				user_business.setTime(new JSONObject(data.getString("md_YYSJ")));
				user_business.setType(data.getInt("md_Type"));
				user_business.setId(data.getInt("md_ID"));
				user_business.setToken(data.getString("token"));
				user_business.setLogoUrl(data.getString("md_Images"));
				if(data.getString("md_BankTell").equals("null")){
					user_business.setTurnover(0);
				}else
				user_business.setTurnover(data.getDouble("md_BankTell"));
				if(data.getString("md_KeTiXian").equals("null")){
					user_business.setKeTiXian(0);
				}else
				user_business.setKeTiXian(data.getDouble("md_KeTiXian"));
				user_business.setBank(data.getString("md_Bank"));
				user_business.setBankName(data.getString("md_BankName"));
				user_business.setBankNumber(data.getString("md_BankNumber"));
				LatLng location=new LatLng(new JSONObject(data.getString("md_GPRS")).getDouble("latitude"), new JSONObject(data.getString("md_GPRS")).getDouble("longitude"));
				user_business.setLocation(location);
				MyApplication.getInstence().setUser_business(user_business);
				Intent intent=new Intent(getContext(),Activity_Main_Business.class);
				startActivity(intent);
				getActivity().finish();
			}else{
				MyUtil.alert(response.getString("message"), getContext());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onErrorResponse(VolleyError error) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		error.printStackTrace();
		try {
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyUtil.alert("请检查网络后重试", getContext());
	}
}
