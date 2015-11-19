package com.shengping.paomanager;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.shengping.paomanager.popup.Popup_UploadPhoto;
import com.shengping.paomanager.util.MyUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_EditProduct extends Activity implements OnClickListener{

	private ImageView img_delete,img;
	private Popup_UploadPhoto UploadPhoto;
	private String cacheUr;
	final public static int request_camera=1;
	final public static int request_photo_zoom=2;
	final public static int request_xiangce=3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("�༭��Ʒ");
		
		img_delete=(ImageView)findViewById(R.id.img_delete);
		img_delete.setOnClickListener(this);
		img=(ImageView)findViewById(R.id.img);
		img.setOnClickListener(this);
		UploadPhoto=new Popup_UploadPhoto(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.img){
			UploadPhoto.builder(this).show();
		}else if(v.getId()==R.id.xiangji){
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
			Date date=new Date();
			cacheUr=date.getTime()+"";
			Uri imageUri = Uri.fromFile(new File(MyUtil.getSDPath(this)+"/imageloader/Cache",cacheUr+".png"));
			cacheUr=MyUtil.getSDPath(this)+"/imageloader/Cache/"+cacheUr+".png";
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
	        startActivityForResult(intent,request_camera);  
		}else if(v.getId()==R.id.xiangce){
			Intent intent=new Intent(this,Activity_SelectPhoto.class);
			intent.putExtra("type", 0);
			startActivityForResult(intent, request_xiangce);
		}else if(v.getId()==R.id.img_delete){
			img.setImageResource(R.drawable.upload_photo);
			img_delete.setVisibility(View.GONE);
		}
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
    	UploadPhoto.dismiss();
        Bundle extras = picdata.getExtras();    
        if (extras != null&&extras.getParcelable("data")!=null) {
//        	LoadingDialog.showWindow(this);
             Bitmap photo = extras.getParcelable("data"); 
             Bitmap  photo_temp=MyUtil.resizeBitmap(photo, MyUtil.dip2px(this, 80));
             img.setImageBitmap(photo_temp);
             img_delete.setVisibility(View.VISIBLE);
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
