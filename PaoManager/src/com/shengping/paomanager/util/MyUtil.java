package com.shengping.paomanager.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MyUtil {
	private static String hexString="0123456789ABCDEF";
	private static Date d;

	public static String getSDPath(Activity a){
		File sdDir=null;
		String[] paths=null;
		boolean sdCardExist=Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if(sdCardExist){
			paths=new String[2];
			paths[0]=Environment.getExternalStorageDirectory().getAbsolutePath();
		}else{
			StorageManager sm = (StorageManager) a.getSystemService(Context.STORAGE_SERVICE);
			try {
				paths = (String[]) sm.getClass().getMethod("getVolumePaths", null).invoke(sm, null);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return paths[0];
	}
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static String encode(String str)
    {
		if(str==null||str.equals("")){
			return null;
		}
    //根据默认编码获取字节数组
    byte[] bytes=str.getBytes();
    StringBuilder sb=new StringBuilder(bytes.length*2);
    //将字节数组中每个字节拆解�?�?6进制整数
    for(int i=0;i<bytes.length;i++)
    {
    sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
    sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
    }
    return sb.toString();
    }
	public static Bitmap decodeThumbBitmapForFile(String path, int viewWidth){
		BitmapFactory.Options options = new BitmapFactory.Options();
		//设置为true,表示解析Bitmap对象，该对象不占内存
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		//设置缩放比例
		options.inSampleSize = computeScale(options, viewWidth);
		
		//设置为false,解析Bitmap对象加入到内存中
		options.inJustDecodeBounds = false;
		
		return BitmapFactory.decodeFile(path, options);
	}
	public static Bitmap drawableToBitamp(Drawable drawable)
	    {
	        int w = drawable.getIntrinsicWidth();
	        int h = drawable.getIntrinsicHeight();
	        System.out.println("Drawable转Bitmap");
	        Bitmap.Config config = 
	                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
	                        : Bitmap.Config.RGB_565;
	        Bitmap bitmap = Bitmap.createBitmap(w,h,config);
	        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
	        Canvas canvas = new Canvas(bitmap);   
	        drawable.setBounds(0, 0, w, h);   
	        drawable.draw(canvas);
	        return bitmap;
	    }
	private static int computeScale(BitmapFactory.Options options, int viewWidth){
		int inSampleSize = 1;
		if(viewWidth == 0 || viewWidth == 0){
			return inSampleSize;
		}
		int bitmapWidth = options.outWidth;
		int bitmapHeight = options.outHeight;
		
		//假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比�?
		if(bitmapWidth > viewWidth || bitmapHeight > viewWidth){
			int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
			int heightScale = Math.round((float) bitmapHeight / (float) viewWidth);
			
			//为了保证图片不缩放变形，我们取宽高比例最小的那个
			inSampleSize = widthScale < heightScale ? widthScale : heightScale;
		}
		return inSampleSize;
	}
	/*
	 * 改变指定Bitmap的宽和高
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap,int w,int h){
		final float bitmapWidth=bitmap.getWidth();
		final float bitmapHeight=bitmap.getHeight();
		Matrix matrix=new Matrix();
		float scaleWidth=((float)w)/bitmapWidth;
		float scaleHeight=((float)h)/bitmapHeight;
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bm=Bitmap.createBitmap(bitmap,0,0,(int)bitmapWidth,(int)bitmapHeight,matrix,true);
		return bm;
	}
	public static Bitmap resizeBitmap(Bitmap bitmap,int w){
		float bitmapWidth=bitmap.getWidth();
		float bitmapHeight=bitmap.getHeight();
		float scale=(float)w/bitmapWidth;
		return resizeBitmap( bitmap, w,(int)(scale*bitmapHeight));
	}
	public static void toast(Context context,String msg){
		Toast.makeText(context, msg,
			     Toast.LENGTH_SHORT).show();
	}
	public static int[] getWindowSize(Activity ac){
		DisplayMetrics dm = new DisplayMetrics();
		int[] size=new int[2];
		ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
		size[0]=dm.widthPixels;
		size[1]=dm.heightPixels;
		return size;
	}
     public static int getBarHeight(Context context){
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 38;//默认�?8，貌似大部分是这样的

            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = context.getResources().getDimensionPixelSize(x);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return sbar;
        }
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大�?
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}
	public static Bitmap convertToBitmap(String path, int w) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大�?
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w ) {
			// 缩放
			scaleWidth = ((float) width) / w;
		}
		//scaleHeight=height/scaleWidth;
		opts.inJustDecodeBounds = false;
		//float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scaleWidth;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, (int)(height/scaleWidth), false);
	}
	/**  
     * 复制单个文件  
     * @param oldPath String 原文件路�?如：c:/fqf.txt  
     * @param newPath String 复制后路�?如：f:/fqf.txt  
     * @return boolean  
     */   
   public static void copyFile(String oldPath, String newPath) {   
       try {   
//           int bytesum = 0;   
           int byteread = 0;   
           File oldfile = new File(oldPath);   
           if (oldfile.exists()) { //文件存在�?  
               InputStream inStream = new FileInputStream(oldPath); //读入原文�?  
               FileOutputStream fs = new FileOutputStream(newPath);   
               byte[] buffer = new byte[1444];   
//               int length;   
               while ( (byteread = inStream.read(buffer)) != -1) {   
//                   bytesum += byteread; //字节�?文件大小     
                   fs.write(buffer, 0, byteread);   
               }   
               fs.flush();
               fs.close();
               inStream.close();   
           }   
       }   
       catch (Exception e) {   
           System.out.println("复制单个文件操作出错");   
           e.printStackTrace();   
  
       }   
  
   }
   public static void alert(String s,Context c) {
		new AlertDialog.Builder(c)
				.setTitle("提示")
				.setMessage(s)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						//setResult(RESULT_OK);// 确定按钮事件
						// finish();
					}
				}).show();
	}
   public static void alert(String s,Context c,DialogInterface.OnClickListener listener) {
		new AlertDialog.Builder(c)
				.setTitle("提示")
				.setMessage(s)
				.setPositiveButton("确定",listener).show();
	}
//   public static void alert_My(Activity context,String s,final View.OnClickListener listener){
//	  final AlertDialog  dlg = new AlertDialog.Builder(context).create();
//	   LayoutInflater factory = LayoutInflater.from(context);
//	   View view = factory.inflate(R.layout.myalert, null);
//	   TextView tv=(TextView)view.findViewById(R.id.tv_message);
//	   tv.setText(s);
//	   Button btn_dialog=(Button)view.findViewById(R.id.btn_dialog);
//	   btn_dialog.setOnClickListener(new View.OnClickListener() {
//		
//		@Override
//		public void onClick(View arg0) {
//			if(listener!=null)
//			listener.onClick(arg0);
//			dlg.dismiss();
//		}
//	   });
//	   
//	   dlg.show();
//	   dlg.getWindow().setGravity(Gravity.CENTER);
//	   dlg.getWindow().setLayout(getWindowSize(context)[0]-dip2px(context, 80), android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
//	   dlg.getWindow().setContentView(view);
////	   FrameLayout.LayoutParams llp=(FrameLayout.LayoutParams) view.getLayoutParams();
////	   llp.width=getWindowSize(context)[0]-dip2px(context, 80);
////	   llp.leftMargin=(getWindowSize(context)[0]-llp.width)/2;
////	   view.setLayoutParams(llp);
//   }
   /**
    * 获取版本�?
    * @return 当前应用的版本号
    */
   public static String getVersion(Context context) {
       try {
           PackageManager manager = context.getPackageManager();
           PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
           String version = info.versionName;
           return version;
       } catch (Exception e) {
           e.printStackTrace();
           return "0";
       }
   }
// 获取ApiKey
   public static String getMetaValue(Context context, String metaKey) {
       Bundle metaData = null;
       String apiKey = null;
       if (context == null || metaKey == null) {
           return null;
       }
       try {
           ApplicationInfo ai = context.getPackageManager()
                   .getApplicationInfo(context.getPackageName(),
                           PackageManager.GET_META_DATA);
           if (null != ai) {
               metaData = ai.metaData;
           }
           if (null != metaData) {
               apiKey = metaData.getString(metaKey);
           }
       } catch (NameNotFoundException e) {

       }
       return apiKey;
   }
   public static int getFontHeight(float fontSize)  
   {  
	      Paint paint = new Paint();  
	       paint.setTextSize(fontSize);  
	       FontMetrics fm = paint.getFontMetrics();  
	       return (int) Math.ceil(fm.descent - fm.top);  
   }  
// 计算出该TextView中文字的长度(像素)
   public static float getTextViewLength(TextView textView,String text){
	   TextPaint paint = textView.getPaint();
	   // 得到使用该paint写上text的时�?像素为多�?
	   float textLength = paint.measureText(text);
	   return textLength;
}
   /**
    * 获取控件的高度，如果获取的高度为0，则重新计算尺寸后再返回高度
    * @param view
    * @return
    */
   public static int getViewMeasuredHeight(View view){
//       int height = view.getMeasuredHeight();
//       if(0 < height){
//           return height;
//       }
       calcViewMeasure(view);
       return view.getMeasuredHeight();
   }
   /**
    * 获取控件的宽度，如果获取的宽度为0，则重新计算尺寸后再返回宽度
    * @param view
    * @return
    */
   public static int getViewMeasuredWidth(View view){
//       int width = view.getMeasuredWidth();
//       if(0 < width){
//           return width;
//       }
       calcViewMeasure(view);
       return view.getMeasuredWidth();
   }

   /**
    * 测量控件的尺�?
    * @param view
    */
   public static void calcViewMeasure(View view){
//       int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//       int height = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//       view.measure(width,height);

       int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
       int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
       view.measure(width, expandSpec);
   }
   public static boolean isMobileNO(String mobiles){  
	   
	 Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
	   
	 Matcher m = p.matcher(mobiles);  
	   
	   
	return m.matches();
	   
	}  
  
   public static String getWeekOfDate(Date dt) {
       String[] weekDays = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
       Calendar cal = Calendar.getInstance();
       cal.setTime(dt);

       int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
       if (w < 0)
           w = 0;

       return weekDays[w];
   }
   public static Bitmap getHttpBitmap(final String url){
	   Thread thread=new Thread(new Runnable() {
		
		@Override
		public void run() {
			 URL myFileURL;
		       Bitmap bitmap=null;
		       try{
		           myFileURL = new URL(url);
		           //获得连接
		           HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
		           //设置超时时间�?000毫秒，conn.setConnectionTiem(0);表示没有时间限制
		           conn.setConnectTimeout(6000);
		           //连接设置获得数据�?
		           conn.setDoInput(true);
		           //不使用缓�?
		           conn.setUseCaches(false);
		           //这句可有可无，没有影�?
		           //conn.connect();
		           //得到数据�?
		           InputStream is = conn.getInputStream();
		           //解析得到图片
		           bitmap = BitmapFactory.decodeStream(is);
		           //关闭数据�?
		           is.close();
		       }catch(Exception e){
		           e.printStackTrace();
		       }
		        System.out.println("myutil loaded");
		}
	});
	   thread.start();
       return null;
        
   }
   /**
	 * 设置高度/宽度
	 * 
	 * @param object
	 * @param height
	 * @param width
	 */
	public static void setWidthHeight(int width, int height, View... views) {
		for (View object : views) {
			ViewGroup.LayoutParams lp=object.getLayoutParams() ;
			if (object != null && lp!= null) {
				lp.height =height;
				lp.width = width;
				object.setLayoutParams(lp);
			}
		}
	}
	 /**
		 * 设置宽度
		 * 
		 * @param object
		 * @param height
		 * @param width
		 */
		public static void setWidth(int width, View... views) {
			for (View object : views) {
				ViewGroup.LayoutParams lp=object.getLayoutParams() ;
				if (object != null && lp!= null) {
					lp.width = width;
					object.setLayoutParams(lp);
				}
			}
		}
		 /**
		 * 设置高度
		 * 
		 * @param object
		 * @param height
		 * @param width
		 */
		public static void setHeight(int height, View... views) {
			for (View object : views) {
				ViewGroup.LayoutParams lp=object.getLayoutParams() ;
				if (object != null && lp!= null) {
					lp.height = height;
					object.setLayoutParams(lp);
				}
			}
		}
		/** 
		 * 将字符串转成MD5值 
		 *  
		 * @param string  要转成MD5的字符串
		 * @return 
		 */  
		public static String stringToMD5(String string) {  
		    byte[] hash;  
		  
		    try {  
		        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));  
		    } catch (NoSuchAlgorithmException e) {  
		        e.printStackTrace();  
		        return null;  
		    } catch (UnsupportedEncodingException e) {  
		        e.printStackTrace();  
		        return null;  
		    }  
		  
		    StringBuilder hex = new StringBuilder(hash.length * 2);  
		    for (byte b : hash) {  
		        if ((b & 0xFF) < 0x10)  
		            hex.append("0");  
		        hex.append(Integer.toHexString(b & 0xFF));  
		    }  
		  
		    return hex.toString();  
		}  
		  /**
	     * 验证邮箱
	     * @param email
	     * @return
	     */
	    public static boolean checkEmail(String email){
	        boolean flag = false;
	        try{
	                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	                Pattern regex = Pattern.compile(check);
	                Matcher matcher = regex.matcher(email);
	                flag = matcher.matches();
	            }catch(Exception e){
	                flag = false;
	            }
	        return flag;
	    }
	     
	    /**
	     * 验证手机号码
	     * @param mobiles
	     * @return
	     */
	    public static boolean checkMobileNumber(String mobileNumber){
	        boolean flag = false;
	        try{
	                Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
	                Matcher matcher = regex.matcher(mobileNumber);
	                flag = matcher.matches();
	            }catch(Exception e){
	                flag = false;
	            }
	        return flag;
	    }
}
