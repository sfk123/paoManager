package com.shengping.paomanager;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.model.LatLng;
import com.shengping.paomanager.fragment.Fragment_Business_Register;
import com.shengping.paomanager.fragment.Fragment_Login;
import com.shengping.paomanager.fragment.Fragment_Pusher_Register;
import com.shengping.paomanager.util.MyUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Login extends FragmentActivity implements OnClickListener{
	 private ViewPager mViewPager;
	 private PagerAdapter mPagerAdapter;
	 
	 private TextView tv_login_username,tv_login_phone,tv_title,tv_forget;
	 private String type;
 	private Fragment_Login frg_login;
 	private Fragment_Pusher_Register frg_register;
 	private Fragment_Business_Register frg_business_register;
	 private int viewpagerHeight=0;
	 final public static int request_camera=1;
	 final public static int request_photo_zoom=2;
	 final public static int request_xiangce=3;
	 final public static int request_location=4;
	public static String cacheUr;//����ʱ���ͼƬ�����ַ
	 private static Activity_Login instence;
	 public static Activity_Login getInstence(){
		 return instence;
	 }
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		tv_title=(TextView)findViewById(R.id.tv_title);
		if(getIntent().getStringExtra("type").equals("pusher")){
			tv_title.setText("���ȸ��½");
			type="pusher";
            frg_register=new Fragment_Pusher_Register();
		}else{
			tv_title.setText("�̼ҵ�½");
			type="business";
			frg_business_register=new Fragment_Business_Register();
		}
		tv_forget=(TextView)findViewById(R.id.tv_forget);
		tv_forget.setOnClickListener(this);
		viewpagerHeight=MyUtil.getWindowSize(this)[1]-(int)getResources().getDimension(R.dimen.title_height)-MyUtil.dip2px(this, 51);
		
		tv_login_username=(TextView)findViewById(R.id.tv_login_username);
		tv_login_username.setOnClickListener(this);
		tv_login_phone=(TextView)findViewById(R.id.tv_login_phone);
		tv_login_phone.setOnClickListener(this);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mViewPager.getLayoutParams().height=viewpagerHeight;
		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
		instence=this;
	}
	 private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

			@Override
	        public void onPageSelected(int arg0)
	        {
	            if(arg0==0){
	            	tv_login_username.setBackgroundColor(getResources().getColor(R.color.login_select));
	            	tv_login_username.setTextColor(getResources().getColor(R.color.text_black));
	            	tv_login_phone.setBackgroundColor(getResources().getColor(R.color.white));
	            	tv_login_phone.setTextColor(getResources().getColor(R.color.color_main));
	            }else if(arg0==1){
	            	
	            	
	            	tv_login_username.setBackgroundColor(getResources().getColor(R.color.white));
	            	tv_login_username.setTextColor(getResources().getColor(R.color.color_main));
	            	tv_login_phone.setBackgroundColor(getResources().getColor(R.color.login_select));
	            	tv_login_phone.setTextColor(getResources().getColor(R.color.text_black));
	            }
	        }

	        @Override
	        public void onPageScrolled(int arg0, float arg1, int arg2)
	        {

	        }

	        @Override
	        public void onPageScrollStateChanged(int arg0)
	        {

	        }
	    };
    private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
            frg_login=new Fragment_Login(type);
        }

        @Override
        public Fragment getItem(int position)
        {
        	if(position==0){
        		return frg_login;
        	}else if(position==1){
        		if(type.equals("pusher"))
        		return frg_register;
        		else
        			return frg_business_register;
        	}
            return null;
        }

        @Override
        public int getCount()
        {
            return 2;
        }

    }
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.tv_login_username){
			mViewPager.setCurrentItem(1);
		}else if(v.getId()==R.id.tv_login_phone){
			mViewPager.setCurrentItem(0);
		}else if(v.getId()==R.id.tv_forget){
			Intent intent=new Intent(this,Activity_ForgetPssword.class);
			intent.putExtra("type", type);
			startActivity(intent);
		}
	}
	public void startCarama(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
		Date date=new Date();
		cacheUr=date.getTime()+"";
		Uri imageUri = Uri.fromFile(new File(MyUtil.getSDPath(this)+"/imageloader/Cache",cacheUr+".png"));
		cacheUr=MyUtil.getSDPath(this)+"/imageloader/Cache/"+cacheUr+".png";
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent,Activity_Login. request_camera);  
	}
	public void startXiangce(){
		Intent intent=new Intent(this,Activity_SelectPhoto.class);
		intent.putExtra("type", 0);
		startActivityForResult(intent, request_xiangce);
	}
	public void startLocation(String s){
		Intent intent=new Intent(this,Activity_SelectLocation.class);
		intent.putExtra("current_location", s);
		intent.putExtra("type", "start");
		intent.putExtra("title", "����");
		startActivityForResult(intent, request_location);
	}
	@Override   
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   
		super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {    
        // �����ֱ�Ӵ�����ȡ    
        case request_camera:   
        	if(resultCode==RESULT_OK)
        	startPhotoZoom(Uri.parse("file://"+cacheUr));    
            break; 
         // ȡ�òü����ͼƬ    
        case request_photo_zoom:    
            /**   
             * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ���   
             * �ڼ���֮��������ֲ����⣬Ҫ���²ü�������   
             * ��ǰ����ʱ���ᱨNullException��С��ֻ   
             * ������ط����£���ҿ��Ը��ݲ�ͬ����ں��ʵ�   
             * �ط����жϴ����������   
             *    
             */   
            if(resultCode==RESULT_OK&&data != null){    
                setPicToView(data);    
            }    
            break;  
         // ����ǵ����������ʱ    
        case request_xiangce: 
        	if(resultCode==RESULT_OK&&data!=null){
        	@SuppressWarnings("unchecked")
			List<String> mSelectedImage =(List<String>)data.getSerializableExtra("images");
            startPhotoZoom(Uri.parse("file://"+mSelectedImage.get(0)));   
        	}
            break;  
        case  request_location:
        	if(resultCode==RESULT_OK){
        		 Bundle b=data.getExtras(); //dataΪB�лش���Intent
  			   	 String address=b.getString("address");//str��Ϊ�ش���ֵ
  			   	 double lat=b.getDouble("lat");
			     double latlong=b.getDouble("latlong");
				frg_business_register.setLocation(address, new LatLng(lat, latlong));
				
        	}
        	break;
        default:    
            break;    
        }
	}
	/**    
     * ����ü�֮���ͼƬ����    
     * @param picdata    
     */    
    @SuppressWarnings("deprecation")
	private void setPicToView(Intent picdata) {  
    	frg_register.hidePopup();
        Bundle extras = picdata.getExtras();    
        if (extras != null&&extras.getParcelable("data")!=null) {
//        	LoadingDialog.showWindow(this);
             Bitmap photo = extras.getParcelable("data"); 
             Bitmap  photo_temp=MyUtil.resizeBitmap(photo, MyUtil.dip2px(this, 80));
             frg_register.setHeadImage(photo_temp);
//            Drawable drawable = new BitmapDrawable(photo); 
//            Thread thread=new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					try{
//					String path=MyUtil.getSDPath(Activity_UserInfo.this)+"/"+MyApplication.IMAGE_CACHE_PATH+"/";
//					File dirFile = new File(path);
//				     if(!dirFile.exists()){    
//				         dirFile.mkdir();    
//				     }    
//				     File myCaptureFile = new File(path + "headtemp.jpg");    
//				     BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));    
//				     photo_temp.compress(Bitmap.CompressFormat.JPEG, 80, bos);    
//				     bos.flush();    
//				     bos.close();  
//				     Http.post(Common.U("User", "changeAvatar",MyApplication.getInstence()), new HashMap<String, String>(), myCaptureFile,Activity_UserInfo.this);
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//				}
//			});
//            thread.start();
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
        startActivityForResult(intent, request_photo_zoom);    
    }
}
