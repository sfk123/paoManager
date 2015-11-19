package com.shengping.paomanager.popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.paomanager.R;
import com.shengping.paomanager.util.ShopTypeUtil;
import com.shengping.paomanager.view.WheelView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

public class Select_ShopType {
	 private Context context;
	    /**
	     * 取消按钮
	     */
	    private Button btnCancel;

	    /**
	     * 确定按钮
	     */
	    private Button btnConfirm;

	    /**
	     *  屏幕的宽度，用于动态设定滑动块的宽度
	     * */
	    private WheelView wvTransportation;
	    private List<String> Transportations;//交通工具列表
	    private PopupWindow popupWindow;
	    private SelectListener selectListtener;
	    private Map<String, Integer> types;
	    public void setSelectListener(SelectListener selectListtener){
	    	this.selectListtener=selectListtener;
	    }
	    public Select_ShopType(Activity activity){
	    	this.context = activity;
	    	types=ShopTypeUtil.getInstence().getData();
	    }
	    @SuppressWarnings("deprecation")
		@SuppressLint("InflateParams")
		public Select_ShopType builder(){

	        // PopView
	        View view = LayoutInflater.from(context).inflate(
	                R.layout.popup_select_transportation, null);

	        loadCity(view);
	// android.view.View#getWindowToken()
	        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,false);
	        popupWindow.setFocusable(true);
	        popupWindow.setOutsideTouchable(true);
	        popupWindow.setBackgroundDrawable(new BitmapDrawable());
	        popupWindow.setOnDismissListener(new PopDismissListener());
	        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
	        popupWindow.setAnimationStyle(R.style.PopAnimationDownUp);
	        return this;
	    }
	    public  void loadCity(View view){
	    	btnCancel = (Button) view.findViewById(R.id.btn_cancel);
	        btnCancel.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                dismiss();
	            }
	        });
	        
	        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
	        btnConfirm.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	if(selectListtener!=null){
	            		JSONObject result=new JSONObject();
	            		try{
	            		result.put("text", wvTransportation.getSeletedItem());
	            		result.put("value", types.get(wvTransportation.getSeletedItem()));
	            		selectListtener.select_ok(result);
	            		}catch(JSONException e){
	            			e.printStackTrace();
	            		}
	            	}
	            	dismiss();
	            }
	        });
	        wvTransportation=(WheelView)view.findViewById(R.id.wheel_view1);
	        Transportations=new ArrayList<>();
	        for (Map.Entry<String, Integer> entry : types.entrySet()) {  
	        	Transportations.add(entry.getKey()); 
	          
	        }  
	        wvTransportation.setItems(Transportations);
	    }
	    /**
	     * 显示
	     */
	    public void show(){
	        if (null != popupWindow){
//	        	popupWindow.setBackgroundDrawable();
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
	    	public void select_ok(JSONObject result);
	    }
}
