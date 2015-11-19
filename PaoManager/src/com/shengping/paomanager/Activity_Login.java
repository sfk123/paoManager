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
	public static String cacheUr;//拍照时候的图片缓存地址
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
			tv_title.setText("跑腿哥登陆");
			type="pusher";
            frg_register=new Fragment_Pusher_Register();
		}else{
			tv_title.setText("商家登陆");
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
		intent.putExtra("title", "我在");
		startActivityForResult(intent, request_location);
	}
	@Override   
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   
		super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {    
        // 如果是直接从相册获取    
        case request_camera:   
        	if(resultCode==RESULT_OK)
        	startPhotoZoom(Uri.parse("file://"+cacheUr));    
            break; 
         // 取得裁剪后的图片    
        case request_photo_zoom:    
            /**   
             * 非空判断大家一定要验证，如果不验证的话，   
             * 在剪裁之后如果发现不满意，要重新裁剪，丢弃   
             * 当前功能时，会报NullException，小马只   
             * 在这个地方加下，大家可以根据不同情况在合适的   
             * 地方做判断处理类似情况   
             *    
             */   
            if(resultCode==RESULT_OK&&data != null){    
                setPicToView(data);    
            }    
            break;  
         // 如果是调用相机拍照时    
        case request_xiangce: 
        	if(resultCode==RESULT_OK&&data!=null){
        	@SuppressWarnings("unchecked")
			List<String> mSelectedImage =(List<String>)data.getSerializableExtra("images");
            startPhotoZoom(Uri.parse("file://"+mSelectedImage.get(0)));   
        	}
            break;  
        case  request_location:
        	if(resultCode==RESULT_OK){
        		 Bundle b=data.getExtras(); //data为B中回传的Intent
  			   	 String address=b.getString("address");//str即为回传的值
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
     * 保存裁剪之后的图片数据    
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
             * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上   
             * 传到服务器，QQ头像上传采用的方法跟这个类似   
             */   
                
            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();   
            photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);   
            byte[] b = stream.toByteArray();   
            // 将图片流以字符串形式存储下来   
               
            tp = new String(Base64Coder.encodeLines(b));   
		            这个地方大家可以写下给服务器上传图片的实现，直接把tp直接上传就可以了，   
		            服务器处理的方法是服务器那边的事了，吼吼   
		               
		            如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换   
		            为我们可以用的图片类型就OK啦...吼吼   
            Bitmap dBitmap = BitmapFactory.decodeFile(tp);   
            Drawable drawable = new BitmapDrawable(dBitmap);   
            */   
            
        }    
    }    
	 /**   
     * 裁剪图片方法实现   
     * @param uri   
     */   
    public void startPhotoZoom(Uri uri) {    
        /*   
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页   
         * yourself_sdk_path/docs/reference/android/content/Intent.html   
         * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,   
         * 是直接调本地库的，小马不懂C C++  这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么   
         * 制做的了...吼吼   
         */   
        Intent intent = new Intent("com.android.camera.action.CROP");    
        intent.setDataAndType(uri, "image/*");    
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪    
        intent.putExtra("crop", "true");    
        // aspectX aspectY 是宽高的比例    
        intent.putExtra("aspectX", 1);    
        intent.putExtra("aspectY", 1);    
        // outputX outputY 是裁剪图片宽高    
        intent.putExtra("outputX", 150);    
        intent.putExtra("outputY", 150);    
        intent.putExtra("return-data", true);    
        startActivityForResult(intent, request_photo_zoom);    
    }
}
