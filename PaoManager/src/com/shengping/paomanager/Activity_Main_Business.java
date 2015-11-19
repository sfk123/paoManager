package com.shengping.paomanager;

import com.shengping.paomanager.fragment.Fragment_Business_Balance;
import com.shengping.paomanager.fragment.Fragment_Business_Order;
import com.shengping.paomanager.fragment.Fragment_Business_Product;
import com.shengping.paomanager.fragment.Fragment_Business_Setting;
import com.shengping.paomanager.fragment.Fragment_Pusher_Balance;
import com.shengping.paomanager.fragment.Fragment_Pusher_Info;
import com.shengping.paomanager.fragment.Fragment_Pusher_Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ÉÌ¼ÒÖ÷Ò³
 * @author Administrator
 *
 */
public class Activity_Main_Business extends FragmentActivity implements OnClickListener{
	private ImageView img_setting,img_balance,img_order,img_product;
	private TextView tv_order,tv_balance,tv_product,tv_setting;
	private ViewPager viewPager;
	private boolean iswork=false;
	private Fragment_Business_Order frg_order;
	private Fragment_Business_Product frg_product;
	private Fragment_Business_Balance frg_balance;
	private Fragment_Business_Setting frg_setting;
	private MyPagerAdapter adapter;
	private final static int request_add_type=1;
	private static Activity_Main_Business instence;
	public static Activity_Main_Business getInstence(){
		return instence;
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_business);
		iswork=getIntent().getBooleanExtra("iswork", false);
		
		img_setting=(ImageView)findViewById(R.id.img_setting);
		img_balance=(ImageView)findViewById(R.id.img_balance);
		img_order=(ImageView)findViewById(R.id.img_order);
		img_product=(ImageView)findViewById(R.id.img_product);
		tv_order=(TextView)findViewById(R.id.tv_order);
		tv_balance=(TextView)findViewById(R.id.tv_balance);
		tv_setting=(TextView)findViewById(R.id.tv_setting);
		tv_product=(TextView)findViewById(R.id.tv_product);
		LinearLayout lable_setting=(LinearLayout)findViewById(R.id.lable_setting);
		lable_setting.setOnClickListener(this);
		LinearLayout lable_balance=(LinearLayout)findViewById(R.id.lable_balance);
		lable_balance.setOnClickListener(this);
		LinearLayout lable_product=(LinearLayout)findViewById(R.id.lable_product);
		lable_product.setOnClickListener(this);
		LinearLayout lable_order=(LinearLayout)findViewById(R.id.lable_order);
		lable_order.setOnClickListener(this);
		
		viewPager=(ViewPager)findViewById(R.id.viewPager);
		adapter=new MyPagerAdapter(getSupportFragmentManager());
		viewPager.setOnPageChangeListener(mPageChangeListener);
		viewPager.setAdapter(adapter);
		instence=this;
	}
	 private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

			@Override
	        public void onPageSelected(int arg0)
	        {
	            if(arg0==0){
	            	img_order.setImageResource(R.drawable.business_order_selected);
	            	tv_order.setTextColor(getResources().getColor(R.color.color_main));
	            	
	            	img_product.setImageResource(R.drawable.business_product);
	            	img_balance.setImageResource(R.drawable.business_balance);
	            	img_setting.setImageResource(R.drawable.business_setting);
	            	setColorNormal(tv_balance,tv_setting,tv_product);
	            }else if(arg0==1){
	            	img_product.setImageResource(R.drawable.business_product_selected);
	            	tv_product.setTextColor(getResources().getColor(R.color.color_main));

	            	img_order.setImageResource(R.drawable.business_order);
	            	img_balance.setImageResource(R.drawable.business_balance);
	            	img_setting.setImageResource(R.drawable.business_setting);
	            	setColorNormal(tv_order,tv_balance,tv_setting);
	            }else if(arg0==2){
	            	img_balance.setImageResource(R.drawable.business_balance_selected);
	            	tv_balance.setTextColor(getResources().getColor(R.color.color_main));

	            	img_product.setImageResource(R.drawable.business_product);
	            	img_order.setImageResource(R.drawable.business_order);
	            	img_setting.setImageResource(R.drawable.business_setting);
	            	setColorNormal(tv_order,tv_setting,tv_product);
	            }else if(arg0==3){
	            	img_setting.setImageResource(R.drawable.business_setting_selected);
	            	tv_setting.setTextColor(getResources().getColor(R.color.color_main));

	            	img_product.setImageResource(R.drawable.business_product);
	            	img_order.setImageResource(R.drawable.business_order);
	            	img_balance.setImageResource(R.drawable.business_balance);
	            	
	            	setColorNormal(tv_order,tv_balance,tv_product);
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
	        private void setColorNormal(TextView... views){
	        	for(TextView tv:views){
	        		tv.setTextColor(getResources().getColor(R.color.text_gray));
	        	}
	        }
	    };
	 private class MyPagerAdapter extends FragmentStatePagerAdapter
	    {
	        public MyPagerAdapter(FragmentManager fm)
	        {
	            super(fm);
	            frg_order=new Fragment_Business_Order();
	            frg_balance=new Fragment_Business_Balance();
	            frg_product=new Fragment_Business_Product();
	            frg_setting=new Fragment_Business_Setting();
	        }

	        @Override
	        public Fragment getItem(int position)
	        {
	        	if(position==0){
	        		return frg_order;
	        	}else if(position==1){
	        		return frg_product;
	        	}else if(position==2){
	        		return frg_balance;
	        	}else if(position==3){
	        		return frg_setting;
	        	}
	            return null;
	        }

	        @Override
	        public int getCount()
	        {
	            return 4;
	        }

	    }
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.lable_setting){
			viewPager.setCurrentItem(3);
		}else if(v.getId()==R.id.lable_balance){
			viewPager.setCurrentItem(2);
		}else if(v.getId()==R.id.lable_product){
			viewPager.setCurrentItem(1);
		}else if(v.getId()==R.id.lable_order){
			viewPager.setCurrentItem(0);
		}
	}
	public void startAddType(){
		Intent intent=new Intent(this,Activity_AddType.class);
		startActivityForResult(intent, request_add_type);
	}
	public void startManager(int id){
		Intent intent=new Intent(this,Activity_Product_Manager.class);
		startActivity(intent);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if(requestCode==request_add_type){
				frg_product.addType(data.getStringExtra("type"));
			}else{
				frg_setting.onActivityResult(requestCode, resultCode, data);
			}
		}
		
	}
}
