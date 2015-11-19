package com.shengping.paomanager.fragment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shengping.paomanager.Activity_Business_Comment;
import com.shengping.paomanager.Activity_Login;
import com.shengping.paomanager.Activity_SelectLocation;
import com.shengping.paomanager.Activity_SelectPhoto;
import com.shengping.paomanager.Activity_WebView;
import com.shengping.paomanager.MyApplication;
import com.shengping.paomanager.R;
import com.shengping.paomanager.model.Business;
import com.shengping.paomanager.popup.Popup_UploadPhoto;
import com.shengping.paomanager.popup.TimePicker;
import com.shengping.paomanager.popup.TimePicker.SelectListener;
import com.shengping.paomanager.util.MyHttp;
import com.shengping.paomanager.util.MyHttp.MyHttpCallBack;
import com.shengping.paomanager.util.MyUtil;
import com.shengping.paomanager.util.ShopTypeUtil;
import com.shengping.paomanager.util.UrlUtil;
import com.shengping.paomanager.view.ClearEditText;
import com.shengping.paomanager.view.LoadingDialog;
import com.shengping.paomanager.view.SwitchView;
import com.shengping.paomanager.view.SwitchView.OnStateChangedListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Business_Setting  extends Fragment implements OnClickListener,SelectListener,OnStateChangedListener,MyHttpCallBack{
	private ImageView img_logo;
	private TextView tv_time,tv_address,tv_set_status,tv_status;
	private ClearEditText phone1,phone2,edt_pwd,edt_pwd1,edt_pwd2;
	private SwitchView mswitch;
	private TextView tv_name,//��������
					tv_type,//��������
					tv_district,//��������
					tv_discount,//�̼��ۿ�
					tv_star,//�̼��Ǽ�
					tv_phone,//���̵绰
					tv_login_username,//��½����
					tv_comment;//��������
	
	private Popup_UploadPhoto UploadPhoto;
	private TimePicker timepicker;
	private JSONObject timepicker_result;
	private String cacheUr;
	private Dialog dialog_phone,dialog_password,dialog_status; 
	private Business user_business;
	private  MyHttp http;
	private ImageLoader imageLoader;
	private final static int handler_toast=1;
	private int responseType=1;//��ȡ���� ���ش���
	private final static int responseType_comment=1;//��ȡ����
	private final static int responseType_time=2;//����ʱ��
	private final static int responseType_address=3;//����λ��
	private final static int responseType_phone=4;//���õ��̵绰
	private final static int responseType_pwd=5;//���õ�½����
	private final static int responseType_status=6;//���õ�½����
	private Handler handler=new Handler(){
		@SuppressLint("HandlerLeak")
		@Override
		 public void handleMessage(Message msg) {   
             switch (msg.what) {   
                  case handler_toast:  
          				if(LoadingDialog.isShowing()){
          					LoadingDialog.dismiss();
          				}
                	  Toast.makeText(getContext(),msg.obj.toString(), Toast.LENGTH_LONG).show();
                       break;   
             }   
             super.handleMessage(msg);   
        }   
	};
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView=inflater.inflate(R.layout.fragment_business_setting, null);
		contentView.findViewById(R.id.btn_back).setVisibility(View.GONE);
		TextView tv_title=(TextView)contentView.findViewById(R.id.tv_title);
		tv_title.setText("����");
		tv_time=(TextView)contentView.findViewById(R.id.tv_time);
		tv_address=(TextView)contentView.findViewById(R.id.tv_address);
		tv_status=(TextView)contentView.findViewById(R.id.tv_status);
		tv_name=(TextView)contentView.findViewById(R.id.tv_name);
		tv_type=(TextView)contentView.findViewById(R.id.tv_type);
		tv_district=(TextView)contentView.findViewById(R.id.tv_district);
		tv_discount=(TextView)contentView.findViewById(R.id.tv_discount);
		tv_star=(TextView)contentView.findViewById(R.id.tv_star);
		tv_phone=(TextView)contentView.findViewById(R.id.tv_phone);
		tv_login_username=(TextView)contentView.findViewById(R.id.tv_login_username);
		tv_comment=(TextView)contentView.findViewById(R.id.tv_comment);
		
		img_logo=(ImageView)contentView.findViewById(R.id.img_logo);
		setClick(img_logo,contentView.findViewById(R.id.layout_time),contentView.findViewById(R.id.layout_address),contentView.findViewById(R.id.layout_phone),contentView.findViewById(R.id.layout_password)
				,contentView.findViewById(R.id.layout_status),contentView.findViewById(R.id.layout_about),contentView.findViewById(R.id.layout_comment));
		return contentView;
	}
	private void setClick(View...views){
		for(View v:views){
			v.setOnClickListener(this);
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  imageLoader=MyApplication.getInstence().getmImageLoader();
		  UploadPhoto=new Popup_UploadPhoto(getActivity());
		  timepicker=new TimePicker(getActivity());
		  timepicker.setSelectListener(this);
		  user_business=MyApplication.getInstence().getUser_business();
		  tv_name.setText("�������ƣ�"+user_business.getName());
		  tv_type.setText(ShopTypeUtil.getInstence().getTypeByValue(user_business.getType()));
		  tv_district.setText(user_business.getArea());
		  tv_discount.setText(user_business.getDiscount());
		  tv_star.setText(user_business.getStar()+"�Ǽ�");
		  tv_address.setText(user_business.getAddress());
		  tv_login_username.setText(user_business.getLoginName());
		  System.out.println("�̼�ͼƬ��"+user_business.getLogoUrl());
		  if(!user_business.getLogoUrl().equals("null")&&!user_business.getLogoUrl().equals("")){
			  imageLoader.displayImage(UrlUtil.getUrl(UrlUtil.Business_Logo)+user_business.getLogoUrl(), img_logo);
		  }
		  if(user_business.getStatus()==1){
			  tv_status.setText("Ӫҵ��");
		  }else{
			  tv_status.setText("��Ϣ��");
		  }
		  JSONObject timejson=user_business.getTime();
		  JSONArray phonejson=user_business.getPhone();
		  try{
		  tv_time.setText(timejson.getString("hour_start_am")+":"+timejson.getString("minute_start_am")+" - "+timejson.getString("hour_end_am")+":"+timejson.getString("minute_end_am")
    			+" | "+timejson.getString("hour_start_pm")+":"+timejson.getString("minute_start_pm")+" - "+timejson.getString("hour_end_pm")+":"+timejson.getString("minute_end_pm"));
		  	String phone=phonejson.getString(0);
		  	if(phonejson.length()==2){
		  		phone=phone+" "+phonejson.getString(1);
		  	}
		  	tv_phone.setText(phone);
		  }catch(JSONException e){
			  e.printStackTrace();
		  }
		  http=new MyHttp(getContext());
		  Map<String, String> params=new HashMap<>();
		  params.put("shopid", user_business.getId()+"");
		  params.put("token", user_business.getToken());
		  responseType=responseType_comment;
		  http.Http_post(UrlUtil.getUrl("comment", UrlUtil.Service_Shop), params, this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.img_logo){
			UploadPhoto.builder(this).show();
		}else if(v.getId()==R.id.xiangji){
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
			Date date=new Date();
			cacheUr=date.getTime()+"";
			Uri imageUri = Uri.fromFile(new File(MyUtil.getSDPath(getActivity())+"/imageloader/Cache",cacheUr+".png"));
			cacheUr=MyUtil.getSDPath(getActivity())+"/imageloader/Cache/"+cacheUr+".png";
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			getActivity().startActivityForResult(intent,Activity_Login. request_camera);  
		}else if(v.getId()==R.id.xiangce){
			UploadPhoto.dismiss();
			Intent intent=new Intent(getContext(),Activity_SelectPhoto.class);
			intent.putExtra("type", 0);
			getActivity().startActivityForResult(intent, Activity_Login.request_xiangce);
		}else if(v.getId()==R.id.layout_time){
			timepicker.builder().show();
		}else if(v.getId()==R.id.layout_address){
			Intent intent=new Intent(getContext(),Activity_SelectLocation.class);
			intent.putExtra("current_location", tv_address.getText().toString().substring(0, tv_address.getText().toString().indexOf(" ")));
			intent.putExtra("type", "location");
			intent.putExtra("latitude", user_business.getLocation().latitude);
			intent.putExtra("longitude", user_business.getLocation().longitude);
			intent.putExtra("title", "��ַ...");
			getActivity().startActivityForResult(intent, Activity_Login.request_location);
		}else if(v.getId()==R.id.layout_phone){
			LayoutInflater inflaterDl = LayoutInflater.from(getContext());

	         View  view = inflaterDl.inflate(R.layout.dialog_editphone, null );
	         dialog_phone = new AlertDialog.Builder(getContext()).create();
	         dialog_phone.show();
	         dialog_phone.getWindow().setContentView(view);
	         phone1=(ClearEditText)view.findViewById(R.id.phone1);
	         phone2=(ClearEditText)view.findViewById(R.id.phone2);
	         JSONArray jsonPhone=user_business.getPhone();
	         if(jsonPhone.length()>0){
	        	 try {
					phone1.setText(jsonPhone.getString(0));
					if(jsonPhone.length()>1){
						phone2.setText(jsonPhone.getString(1));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	         setClick(view.findViewById(R.id.dialog_calcle_phone),view.findViewById(R.id.dialog_save_phone));
		}else if(v.getId()==R.id.dialog_calcle_phone){
			dialog_phone.dismiss();
		}else if(v.getId()==R.id.dialog_save_phone){
			if(phone1.getText().toString().equals("")&&phone2.getText().toString().equals("")){
				MyUtil.alert("����������һ�����̵绰��", getContext());
			}else{
				JSONArray phonejson=new JSONArray();
				phonejson.put(phone1.getText().toString());
				phonejson.put(phone2.getText().toString());
				Map<String, String> params=new HashMap<>();
				params.put("shopid", user_business.getId()+"");
				params.put("token", user_business.getToken());
				params.put("phone",phonejson.toString());
				LoadingDialog.showWindow(getContext());
				responseType=responseType_phone;
				http.Http_post(UrlUtil.getUrl("setPhone", UrlUtil.Service_Shop), params, this);
			}
		}else if(v.getId()==R.id.layout_password){
			LayoutInflater inflaterDl = LayoutInflater.from(getContext());

	         View  view = inflaterDl.inflate(R.layout.dialog_set_password, null );
	         dialog_password = new AlertDialog.Builder(getContext()).create();
	         dialog_password.show();
	         dialog_password.getWindow().setContentView(view);
	         edt_pwd=(ClearEditText)view.findViewById(R.id.edt_pwd);
	         edt_pwd1=(ClearEditText)view.findViewById(R.id.edt_pwd1);
	         edt_pwd2=(ClearEditText)view.findViewById(R.id.edt_pwd2);
	         setClick(view.findViewById(R.id.dialog_calcle_password),view.findViewById(R.id.dialog_save_password));
		}else if(v.getId()==R.id.dialog_calcle_password){
			dialog_password.dismiss();
		}else if(v.getId()==R.id.dialog_save_password){
			if(!edt_pwd1.getText().toString().equals(edt_pwd2.getText().toString())){
				MyUtil.alert("�������벻һ��", getContext());
			}else if(edt_pwd.getText().toString().equals("")){
				MyUtil.alert("������ԭ����", getContext());
			}else{
				Map<String, String> params=new HashMap<>();
				params.put("shopid", user_business.getId()+"");
				params.put("token", user_business.getToken());
				params.put("old",MyUtil.stringToMD5(edt_pwd.getText().toString()));
				params.put("newpwd", edt_pwd2.getText().toString());
				LoadingDialog.showWindow(getContext());
				responseType=responseType_pwd;
				http.Http_post(UrlUtil.getUrl("ResetPwd", UrlUtil.Service_Shop), params, this);
			}
		}else if(v.getId()==R.id.layout_status){
			LayoutInflater inflaterDl = LayoutInflater.from(getContext());

	         View  view = inflaterDl.inflate(R.layout.dialog_set_status, null );
	         dialog_status = new AlertDialog.Builder(getContext()).create();
	         dialog_status.show();
	         dialog_status.getWindow().setContentView(view);
	         mswitch=(SwitchView)view.findViewById(R.id.my_switch);
	         mswitch.setOnStateChangedListener(this);
	         tv_set_status=(TextView)view.findViewById(R.id.tv_set_status);
	         if(tv_status.getText().toString().equals("Ӫҵ��"))
	         mswitch.setState(true);
	         else{
	        	 mswitch.setState(false);
	        	 tv_set_status.setText("��Ϣ��");
	         }
	         setClick(view.findViewById(R.id.dialog_save_status));
		}else if(v.getId()==R.id.dialog_save_status){
			int status_current=0;
			if(tv_set_status.getText().toString().equals("Ӫҵ��")){
				status_current=1;
			}
			if(status_current!=user_business.getStatus()){
				Map<String, String> params=new HashMap<>();
				params.put("shopid", user_business.getId()+"");
				params.put("token", user_business.getToken());
				params.put("status", status_current+"");
				LoadingDialog.showWindow(getContext());
				responseType=responseType_status;
				http.Http_post(UrlUtil.getUrl("setStatus", UrlUtil.Service_Shop), params, this);
			}else{
				dialog_status.dismiss();
			}
		}else if(v.getId()==R.id.layout_about){//�����������
			Intent intent=new Intent(getContext(),Activity_WebView.class);
			startActivity(intent);
		}else if(v.getId()==R.id.layout_comment){
			Intent intent=new Intent(getContext(),Activity_Business_Comment.class);
			startActivity(intent);
		}
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK){
			System.out.println("requestCode:"+requestCode);
			if(requestCode==Activity_Login. request_camera){
				startPhotoZoom(Uri.parse("file://"+cacheUr));   
			}else if(requestCode==Activity_Login.request_photo_zoom){
				if(data != null){    
	                setPicToView(data);    
	            }   
			}else if(requestCode==Activity_Login.request_xiangce){
				if(data!=null){
		        	@SuppressWarnings("unchecked")
					List<String> mSelectedImage =(List<String>)data.getSerializableExtra("images");
		            startPhotoZoom(Uri.parse("file://"+mSelectedImage.get(0)));   
		        	}
			}else if(requestCode==Activity_Login.request_location){
				try{
					Map<String, String> params=new HashMap<>();
					params.put("shopid", user_business.getId()+"");
					params.put("token", user_business.getToken());
					params.put("address",data.getStringExtra("address"));
					JSONObject location=new JSONObject();
					location.put("longitude", data.getDoubleExtra("latlong",0));
					location.put("latitude", data.getDoubleExtra("lat",0));
					params.put("latlong",location.toString());
					LoadingDialog.showWindow(getContext());
					responseType=responseType_address;
					http.Http_post(UrlUtil.getUrl("setaddress", UrlUtil.Service_Shop), params, this);
					
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}
	}
	/**    
     * ����ü�֮���ͼƬ����    
     * @param picdata    
     */    
	private void setPicToView(Intent picdata) {  
    	UploadPhoto.dismiss();
        Bundle extras = picdata.getExtras();    
        if (extras != null&&extras.getParcelable("data")!=null) {
        	LoadingDialog.showWindow(getContext());
            final Bitmap photo = extras.getParcelable("data"); 
             Bitmap  photo_temp=MyUtil.resizeBitmap(photo, MyUtil.dip2px(getContext(), 80));
             img_logo.setImageBitmap(photo_temp);
            Thread thread=new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
					String path=MyUtil.getSDPath(getActivity())+"/"+MyApplication.IMAGE_CACHE_PATH+"/";
					File dirFile = new File(path);
				     if(!dirFile.exists()){    
				         dirFile.mkdir();    
				     }    
				     File myCaptureFile = new File(path + "headtemp.jpg");    
				     BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));    
				     photo.compress(Bitmap.CompressFormat.JPEG, 80, bos);    
				     bos.flush();    
				     bos.close();  
				     Map<String, String> params=new HashMap<String, String>();
				     params.put("shopid", user_business.getId()+"");
				     params.put("token", user_business.getToken());
				     http.post(UrlUtil.getUrl("upload_logo", UrlUtil.Service_Shop), params, myCaptureFile,new uploadCallback());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
            thread.start();
            /**   
             * ����ע�͵ķ����ǽ��ü�֮���ͼƬ��Base64Coder���ַ���ʽ��   
             * ������������QQͷ���ϴ����õķ������������   
             */   
                
            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();   
            photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);   
            byte[] b = stream.toByteArray();   
            // ��ͼƬ�����ַ�����ʽ�洢����   
               
            tp = new String(Base64Coder.encodeLines(b));   
		            ����ط���ҿ���д�¸��������ϴ�ͼƬ��ʵ�֣�ֱ�Ӱ�tpֱ���ϴ��Ϳ����ˣ�   
		            ����������ķ����Ƿ������Ǳߵ����ˣ����   
		               
		            ������ص��ķ����������ݻ�����Base64Coder����ʽ�Ļ������������·�ʽת��   
		            Ϊ���ǿ����õ�ͼƬ���;�OK��...���   
            Bitmap dBitmap = BitmapFactory.decodeFile(tp);   
            Drawable drawable = new BitmapDrawable(dBitmap);   
            */   
            
        }    
    }  
    class uploadCallback implements MyHttpCallBack{

		@Override
		public void onResponse(JSONObject response) {
			System.out.println(response);
			try {
				if(response.getBoolean("status")){
					user_business.setLogoUrl(response.getString("data"));
				}
				Message msg=new Message();
				msg.what=handler_toast;
				msg.obj=response.getString("message");
				handler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			error.printStackTrace();
			Message msg=new Message();
			msg.what=handler_toast;
			msg.obj="���ִ����������������";
			handler.sendMessage(msg);
		}
    	
    }
	 /**   
     * �ü�ͼƬ����ʵ��   
     * @param uri   
     */   
    public void startPhotoZoom(Uri uri) {    
        /*   
         * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ   
         * yourself_sdk_path/docs/reference/android/content/Intent.html   
         * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰС��û��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����,   
         * ��ֱ�ӵ����ؿ�ģ�С����C C++  ���������ϸ�˽�ȥ�ˣ������Ӿ������ӣ������о���������ô   
         * ��������...���   
         */   
        Intent intent = new Intent("com.android.camera.action.CROP");    
        intent.setDataAndType(uri, "image/*");    
        //�������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�    
        intent.putExtra("crop", "true");    
        // aspectX aspectY �ǿ�ߵı���    
        intent.putExtra("aspectX", 1);    
        intent.putExtra("aspectY", 1);    
        // outputX outputY �ǲü�ͼƬ���    
        intent.putExtra("outputX", 150);    
        intent.putExtra("outputY", 150);    
        intent.putExtra("return-data", true);    
        getActivity().startActivityForResult(intent, Activity_Login.request_photo_zoom);    
    }
	@Override
	public void select_ok(JSONObject result) {
		timepicker_result =result;
		Map<String, String> params=new HashMap<>();
		params.put("shopid", user_business.getId()+"");
		params.put("token", user_business.getToken());
		params.put("time", timepicker_result.toString());
		LoadingDialog.showWindow(getContext());
		responseType=responseType_time;
		http.Http_post(UrlUtil.getUrl("settime", UrlUtil.Service_Shop), params, this);
		
	}
	@Override
	public void toggleToOn() {
		tv_set_status.setText("��Ϣ��");
	}
	@Override
	public void toggleToOff() {
		tv_set_status.setText("Ӫҵ��");
	}
	@Override
	public void onResponse(JSONObject response) {
		System.out.println(response);
		if(responseType==responseType_comment){
			try {
				if(response.getBoolean("status")){
					
						JSONArray comments=response.getJSONArray("data");
						tv_comment.setText(comments.length()+"��");
					
				}else{
					Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if(LoadingDialog.isShowing()){
				LoadingDialog.dismiss();
			}
			try{
				if(responseType==responseType_time){
						if(response.getBoolean("status")){
							tv_time.setText(timepicker_result.getString("hour_start_am")+":"+timepicker_result.getString("minute_start_am")+" - "+timepicker_result.getString("hour_end_am")+":"+timepicker_result.getString("minute_end_am")
					    			+" | "+timepicker_result.getString("hour_start_pm")+":"+timepicker_result.getString("minute_start_pm")+" - "+timepicker_result.getString("hour_end_pm")+":"+timepicker_result.getString("minute_end_pm"));
							user_business.setTime(timepicker_result);
						}
					
				}else if(responseType==responseType_address){
					if(response.getBoolean("status")){
						tv_address.setText(response.getString("data"));
						user_business.setAddress(response.getString("data"));
					}
				}else if(responseType==responseType_phone){
					if(response.getBoolean("status")){
						tv_phone.setText(phone1.getText().toString()+" "+phone2.getText().toString());
						dialog_phone.dismiss();
						JSONArray json_phone=new JSONArray();
						json_phone.put(phone1.getText().toString());
						json_phone.put(phone2.getText().toString());
						user_business.setPhone(json_phone);
					}
				}else if(responseType==responseType_pwd){
					if(response.getBoolean("status"))
					dialog_password.dismiss();
				}else if(responseType==responseType_status){
					if(response.getBoolean("status")){
						dialog_status.dismiss();
						tv_status.setText(tv_set_status.getText().toString());
						if(tv_status.getText().toString().equals("Ӫҵ��")){
							user_business.setStatus(1);
						}else{
							user_business.setStatus(0);
						}
					}
				}
				MyApplication.getInstence().setUser_business(user_business);
				Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void onErrorResponse(VolleyError error) {
		error.printStackTrace();
		try {
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(getContext(), "�������Ӵ���", Toast.LENGTH_LONG).show();
	}
}
