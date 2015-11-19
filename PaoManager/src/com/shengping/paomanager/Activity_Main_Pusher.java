package com.shengping.paomanager;

import com.shengping.paomanager.fragment.Fragment_Pusher_Balance;
import com.shengping.paomanager.fragment.Fragment_Pusher_Info;
import com.shengping.paomanager.fragment.Fragment_Pusher_Order;

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
 * ≈‹Õ»∏Á÷˜“≥
 * @author Administrator
 *
 */
public class Activity_Main_Pusher extends FragmentActivity implements OnClickListener{
	private ImageView img_info,img_balance,img_order;
	private TextView tv_order,tv_balance,tv_info;
	private ViewPager viewPager;
	private boolean iswork=false;
	private Fragment_Pusher_Order frg_order;
	private Fragment_Pusher_Balance frg_balance;
	private Fragment_Pusher_Info frg_info;
	private MyPagerAdapter adapter;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_pusher);
		iswork=getIntent().getBooleanExtra("iswork", false);
		
		img_info=(ImageView)findViewById(R.id.img_info);
		img_balance=(ImageView)findViewById(R.id.img_balance);
		img_order=(ImageView)findViewById(R.id.img_order);
		tv_order=(TextView)findViewById(R.id.tv_order);
		tv_balance=(TextView)findViewById(R.id.tv_balance);
		tv_info=(TextView)findViewById(R.id.tv_info);
		LinearLayout lable_info=(LinearLayout)findViewById(R.id.lable_info);
		lable_info.setOnClickListener(this);
		LinearLayout lable_balance=(LinearLayout)findViewById(R.id.lable_balance);
		lable_balance.setOnClickListener(this);
		LinearLayout lable_order=(LinearLayout)findViewById(R.id.lable_order);
		lable_order.setOnClickListener(this);
		
		viewPager=(ViewPager)findViewById(R.id.viewPager);
		adapter=new MyPagerAdapter(getSupportFragmentManager());
		viewPager.setOnPageChangeListener(mPageChangeListener);
		viewPager.setAdapter(adapter);
		
	}
	 private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

			@Override
	        public void onPageSelected(int arg0)
	        {
	            if(arg0==0){
	            	img_order.setImageResource(R.drawable.pusher_order_selected);
	            	tv_order.setTextColor(getResources().getColor(R.color.color_main));
	            	
	            	img_info.setImageResource(R.drawable.pusher_info);
	            	img_balance.setImageResource(R.drawable.pusher_balance);
	            	setColorNormal(tv_balance,tv_info);
	            }else if(arg0==1){
	            	img_balance.setImageResource(R.drawable.pusher_balance_selected);
	            	tv_balance.setTextColor(getResources().getColor(R.color.color_main));

	            	img_order.setImageResource(R.drawable.pusher_order);
	            	img_info.setImageResource(R.drawable.pusher_info);
	            	setColorNormal(tv_order,tv_info);
	            }else if(arg0==2){
	            	img_info.setImageResource(R.drawable.pusher_info_selected);
	            	tv_info.setTextColor(getResources().getColor(R.color.color_main));

	            	img_order.setImageResource(R.drawable.pusher_order);
	            	img_balance.setImageResource(R.drawable.pusher_balance);
	            	setColorNormal(tv_order,tv_balance);
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
	            frg_order=new Fragment_Pusher_Order(iswork);
	            frg_balance=new Fragment_Pusher_Balance();
	            frg_info=new Fragment_Pusher_Info();
	        }

	        @Override
	        public Fragment getItem(int position)
	        {
	        	if(position==0){
	        		return frg_order;
	        	}else if(position==1){
	        		return frg_balance;
	        	}else if(position==2){
	        		return frg_info;
	        	}
	            return null;
	        }

	        @Override
	        public int getCount()
	        {
	            return 3;
	        }

	    }
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.lable_info){
			viewPager.setCurrentItem(2);
		}else if(v.getId()==R.id.lable_balance){
			viewPager.setCurrentItem(1);
		}else if(v.getId()==R.id.lable_order){
			viewPager.setCurrentItem(0);
		}
	}
}
