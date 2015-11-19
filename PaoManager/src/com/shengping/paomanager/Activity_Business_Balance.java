package com.shengping.paomanager;

import com.shengping.paomanager.fragment.Fragment_Business_Balance_Month;
import com.shengping.paomanager.fragment.Fragment_Business_Balance_Today;
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
import android.widget.TextView;

public class Activity_Business_Balance extends FragmentActivity implements OnClickListener{

	private ViewPager viewPager;
	private Fragment_Business_Balance_Today today;
	private Fragment_Business_Balance_Month month;
	private TextView tv_today,tv_month;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_balance);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("∂©µ•∂‘’À");
		tv_today=(TextView)findViewById(R.id.tv_today);
		tv_month=(TextView)findViewById(R.id.tv_month);
		tv_today.setOnClickListener(this);
		tv_month.setOnClickListener(this);
		
		viewPager=(ViewPager)findViewById(R.id.viewPager);
		viewPager.setOnPageChangeListener(mPageChangeListener);
		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.tv_today){
			viewPager.setCurrentItem(0);
		}else if(v.getId()==R.id.tv_month){
			viewPager.setCurrentItem(1);
		}
	}
	 private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

			@Override
	        public void onPageSelected(int arg0)
	        {
	            if(arg0==0){
	            	tv_today.setBackgroundColor(getResources().getColor(R.color.color_main));
	            	tv_today.setTextColor(getResources().getColor(R.color.white));
	            	tv_month.setBackgroundColor(getResources().getColor(R.color.line_color));
	            	tv_month.setTextColor(getResources().getColor(R.color.text_black));
	            }else if(arg0==1){
	            	tv_month.setBackgroundColor(getResources().getColor(R.color.color_main));
	            	tv_month.setTextColor(getResources().getColor(R.color.white));
	            	tv_today.setBackgroundColor(getResources().getColor(R.color.line_color));
	            	tv_today.setTextColor(getResources().getColor(R.color.text_black));
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
            today=new Fragment_Business_Balance_Today();
            month=new Fragment_Business_Balance_Month();
        }

        @Override
        public Fragment getItem(int position)
        {
        	if(position==0){
        		return today;
        	}else if(position==1){
        		return month;
        	}
            return null;
        }

        @Override
        public int getCount()
        {
            return 2;
        }

    }
}
