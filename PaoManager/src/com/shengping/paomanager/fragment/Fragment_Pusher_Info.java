package com.shengping.paomanager.fragment;

import com.shengping.paomanager.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_Pusher_Info extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView=inflater.inflate(R.layout.fragment_pusher_info, null);
		TextView tv_title=(TextView)contentView.findViewById(R.id.tv_title);
		tv_title.setText("пео╒");
		contentView.findViewById(R.id.btn_back).setVisibility(View.GONE);
		return contentView;
	}
}
