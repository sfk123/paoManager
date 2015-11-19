package com.shengping.paomanager.popup;
import java.io.File;
import java.util.Date;
import com.shengping.paomanager.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView.FindListener;
import android.widget.PopupWindow;

public class Popup_UploadPhoto {
	    private Context context;
	    private PopupWindow popupWindow;
	    public Popup_UploadPhoto(Activity activity){
	    	this.context = activity;

	    }
	    @SuppressWarnings("deprecation")
		@SuppressLint("InflateParams")
		public Popup_UploadPhoto builder(OnClickListener listener){

	        // PopView
	        View view = LayoutInflater.from(context).inflate(
	                R.layout.popup_userinfo_head, null);
	// android.view.View#getWindowToken()
	        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,false);
	        popupWindow.setFocusable(true);
	        popupWindow.setOutsideTouchable(true);
	        view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dismiss();
				}
			});
	        view.findViewById(R.id.xiangji).setOnClickListener(listener);
	        view.findViewById(R.id.xiangce).setOnClickListener(listener);
	        popupWindow.setBackgroundDrawable(new BitmapDrawable());
	        popupWindow.setOnDismissListener(new PopDismissListener());
	        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
	        popupWindow.setAnimationStyle(R.style.PopAnimationDownUp);
	        return this;
	    }
	    /**
	     * 显示
	     */
	    public void show(){
	        if (null != popupWindow){
	            setBackgroundAlpha(0.5f);
	            popupWindow.update();
	        }
	    }

	    /**
	     * 撤销
	     */
	    public void dismiss(){
	        if (null != popupWindow){
	            popupWindow.dismiss();
	        }
	    }

	    /**
	     * 设置背景透明度
	     * @param alpha 背景的Alpha
	     */
	    private void setBackgroundAlpha(float alpha){
	        WindowManager.LayoutParams lp =  ((Activity)context).getWindow().getAttributes();
	        lp.alpha = alpha;
	        ((Activity)context).getWindow().setAttributes(lp);
	    }
	    private class PopDismissListener implements PopupWindow.OnDismissListener{

	        @Override
	        public void onDismiss() {
	            //更改背景透明度
	            setBackgroundAlpha(1.0f);
	        }
	    }
	    public interface SelectListener{
	    	public void select_ok(String s);
	    }

}
