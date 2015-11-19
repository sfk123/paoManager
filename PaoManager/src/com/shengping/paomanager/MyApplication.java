package com.shengping.paomanager;

import java.io.File;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.shengping.paomanager.model.Business;


import android.graphics.Bitmap;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{
	public static String IMAGE_CACHE_PATH = "imageloader/Cache"; // 图片缓存路径
	private DisplayImageOptions options;
	private Business user_business;//商家信息保存
	private static Context mContext;
	private static MyApplication application;
	public static MyApplication getInstence(){
		return application;
	}
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate()
    {
        super.onCreate();
		SDKInitializer.initialize(this);  
//        UpdateConfig.setDebug(true);//此处发布应用时请注释掉
//		UmengUpdateAgent.setUpdateOnlyWifi(false);
		initImageLoader();
    	options = new DisplayImageOptions.Builder()
//			.showStubImage(R.drawable.top_banner_android)
//			.showImageForEmptyUri(R.drawable.top_banner_android)
//			.showImageOnFail(R.drawable.top_banner_android)
			.cacheInMemory(true).cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.imageScaleType(ImageScaleType.EXACTLY).build();
    	CrashHandler handler=CrashHandler.getInstance();
    	handler.init(getApplicationContext());
    	application=this;
    	mContext = this;
    }
	
	public static Context getAppContext() {
        return mContext;
    }
	@SuppressWarnings("deprecation")
	private void initImageLoader() {
		File cacheDir = com.nostra13.universalimageloader.utils.StorageUtils
				.getOwnCacheDirectory(this,IMAGE_CACHE_PATH);

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new LruMemoryCache(12 * 1024 * 1024))
				.memoryCacheSize(12 * 1024 * 1024)
				.discCacheSize(32 * 1024 * 1024).discCacheFileCount(100)
				.discCache(new BaseDiskCache(cacheDir))
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		ImageLoader.getInstance().init(config);
	}
	public ImageLoader getmImageLoader() {
		return ImageLoader.getInstance();
	}
	public DisplayImageOptions getOptions() {
		return options;
	}
	public Business getUser_business() {
		return user_business;
	}
	public void setUser_business(Business user_business) {
		this.user_business = user_business;
	}
	
}
