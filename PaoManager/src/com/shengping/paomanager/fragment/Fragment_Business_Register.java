package com.shengping.paomanager.fragment;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.baidu.mapapi.model.LatLng;
import com.shengping.paomanager.Activity_Login;
import com.shengping.paomanager.Activity_WebView;
import com.shengping.paomanager.R;
import com.shengping.paomanager.model.CityModel;
import com.shengping.paomanager.model.DistrictModel;
import com.shengping.paomanager.model.ProvinceModel;
import com.shengping.paomanager.popup.Select_ShopType;
import com.shengping.paomanager.popup.TimePicker;
import com.shengping.paomanager.popup.XmlParserHandler;
import com.shengping.paomanager.popup.TimePicker.SelectListener;
import com.shengping.paomanager.util.MyHttp;
import com.shengping.paomanager.util.MyHttp.MyHttpCallBack;
import com.shengping.paomanager.util.MyUtil;
import com.shengping.paomanager.util.UrlUtil;
import com.shengping.paomanager.view.LoadingDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class Fragment_Business_Register  extends Fragment implements OnClickListener,SelectListener,MyHttpCallBack{

	private EditText location_start_input;
	private TextView tv_time,tv_type,tv_btn,tv_agreement,
	location_start_select;
	private LatLng location;
	private TimePicker timepicker;
	private JSONObject timepicker_result,selectShopType_result;
	private Select_ShopType selectShopType;
	private EditText edt_name,//真实姓名
					 edt_email,//邮箱地址
					 edt_phone,//联系电话
					 edt_hotline,//服务热线
					 edt_shop_name,//店面名称
					 edt_discount;//给予折扣
	private String cityCode;//城市编码
	protected String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
	
	/**
	 * key - 区 values - 邮编
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>(); 
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView=inflater.inflate(R.layout.fragment_business_register, null);
		location_start_select=(TextView)contentView.findViewById(R.id.location_start_select);
		location_start_select.setOnClickListener(this);
		location_start_input=(EditText)contentView.findViewById(R.id.location_start_input);
		edt_name=(EditText)contentView.findViewById(R.id.edt_name);
		edt_email=(EditText)contentView.findViewById(R.id.edt_email);
		edt_phone=(EditText)contentView.findViewById(R.id.edt_phone);
		edt_hotline=(EditText)contentView.findViewById(R.id.edt_hotline);
		edt_shop_name=(EditText)contentView.findViewById(R.id.edt_shop_name);
		edt_discount=(EditText)contentView.findViewById(R.id.edt_discount);
		contentView.findViewById(R.id.lable_select_time).setOnClickListener(this);
		contentView.findViewById(R.id.lable_select_shop_type).setOnClickListener(this);
		tv_time=(TextView)contentView.findViewById(R.id.tv_time);
		tv_type=(TextView)contentView.findViewById(R.id.tv_type);
		tv_btn=(TextView)contentView.findViewById(R.id.tv_btn);
		tv_agreement=(TextView)contentView.findViewById(R.id.tv_agreement);
		tv_btn.setOnClickListener(this);
		tv_agreement.setOnClickListener(this);
		return contentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  timepicker=new TimePicker(getActivity()); 
		  timepicker.setSelectListener(this);
		  selectShopType=new Select_ShopType(getActivity());
		  selectShopType.setSelectListener(shopTypeListener);
		  initProvinceDatas();
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.location_start_select){
			Activity_Login.getInstence().startLocation(location_start_select.getText().toString());
		}else if(v.getId()==R.id.lable_select_time){
			timepicker.builder().show();
		}else if(v.getId()==R.id.lable_select_shop_type){
			selectShopType.builder().show();
		}else if(v.getId()==R.id.tv_btn){//申请入驻
			if(location==null){
				MyUtil.alert("请选择所在地", getContext());
			}else if(location_start_input.getText().toString().trim().equals("")){
				MyUtil.alert("请输入所在地具体地址", getContext());
			}else if(edt_name.getText().toString().trim().equals("")){
				MyUtil.alert("请输入真实姓名", getContext());
			}else if(edt_email.getText().toString().trim().equals("")){
				MyUtil.alert("请输入邮箱地址", getContext());
			}else if(!MyUtil.checkEmail(edt_email.getText().toString().trim())){
				MyUtil.alert("邮箱格式输入有误", getContext());
			}else if(edt_phone.getText().toString().trim().equals("")){
				MyUtil.alert("请输入联系电话", getContext());
			}else if(!MyUtil.checkMobileNumber(edt_phone.getText().toString().trim())){
				MyUtil.alert("联系电话输入有误", getContext());
			}else if(edt_hotline.getText().toString().trim().equals("")){
				MyUtil.alert("请输入店面服务热线", getContext());
			}else if(edt_shop_name.getText().toString().trim().equals("")){
				MyUtil.alert("请输入店面名称", getContext());
			}else if(timepicker_result==null){
				MyUtil.alert("请选择营业时间", getContext());
			}else if(edt_discount.getText().toString().trim().equals("")){
				MyUtil.alert("请输入给予折扣", getContext());
			}else if(selectShopType_result==null){
				MyUtil.alert("请选择商铺类别", getContext());
			}else{
				try{
				Map<String, String> params=new HashMap<>();
				params.put("title", edt_shop_name.getText().toString().trim());
				params.put("address", location_start_select.getText().toString()+" "+location_start_input.getText().toString().trim());
				JSONArray phone_json=new JSONArray();
				phone_json.put( edt_phone.getText().toString().trim());
				params.put("phone",phone_json.toString());
				params.put("discount", edt_discount.getText().toString().trim());
				params.put("type", selectShopType_result.getInt("value")+"");
				params.put("YYSJ", timepicker_result.toString());
				params.put("MD_Area",cityCode);
				params.put("LoEMail",edt_email.getText().toString().trim());
				JSONObject gprs=new JSONObject();
				gprs.put("latitude", location.latitude);
				gprs.put("longitude", location.longitude);
				params.put("MD_GPRS",gprs.toString());
				params.put("realname",edt_name.getText().toString().trim());
				params.put("hotline",edt_hotline.getText().toString().trim());
				String url=UrlUtil.getUrl("register", UrlUtil.Service_Shop);
				LoadingDialog.showWindow(getContext());
				MyHttp http=new MyHttp(getContext());
				http.Http_post(url, params, this);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}else if(v.getId()==R.id.tv_agreement){//打开用户协议页面
			Intent intent=new Intent(getContext(),Activity_WebView.class);
			intent.putExtra("title", "用户协议");
			intent.putExtra("url", UrlUtil.getAgreementUrl());
			startActivity(intent);
		}
	}
	public void setLocation(String address, LatLng location){
		this.location=location;
		location_start_select.setText(address);
		MyHttp http=new MyHttp(getContext());
		String url="http://api.map.baidu.com/geocoder/v2/?ak=e2Sqrx8lvmfDLlpayZjEmFdZ&output=json&pois=0&location="+location.latitude+","+location.longitude;
		System.out.println(url);
		LoadingDialog.showWindow(getContext());
		http.Http_get(url, new MyHttpCallBack() {
			
			@Override
			public void onResponse(JSONObject response) {
				if(LoadingDialog.isShowing()){
					LoadingDialog.dismiss();
				}
				try {
//					String province=response.getJSONObject("result").getJSONObject("addressComponent").getString("province");
//					String city=response.getJSONObject("result").getJSONObject("addressComponent").getString("city");
					String district=response.getJSONObject("result").getJSONObject("addressComponent").getString("district");
					cityCode=mZipcodeDatasMap.get(district);
					System.out.println("城市编码："+cityCode);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onErrorResponse(VolleyError error) {
				if(LoadingDialog.isShowing()){
					LoadingDialog.dismiss();
				}
				error.printStackTrace();
				MyUtil.alert("请检查网络后重试", getContext());
			}
		});
	}
	/**
	 * 解析省市区的XML数据
	 */
	
    protected void initProvinceDatas()
	{
		List<ProvinceModel> provinceList = null;
    	AssetManager asset = getActivity().getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			//*/
			mProvinceDatas = new String[provinceList.size()];
        	for (int i=0; i< provinceList.size(); i++) {
        		// 遍历所有省的数据
        		mProvinceDatas[i] = provinceList.get(i).getName();
        		List<CityModel> cityList = provinceList.get(i).getCityList();
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// 遍历省下面的所有市的数据
        			cityNames[j] = cityList.get(j).getName();
        			List<DistrictModel> districtList = cityList.get(j).getDistrictList();
        			String[] distrinctNameArray = new String[districtList.size()];
        			DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// 遍历市下面所有区/县的数据
        				DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				// 区/县对于的邮编，保存到mZipcodeDatasMap
        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				distrinctArray[k] = districtModel;
        				distrinctNameArray[k] = districtModel.getName();
        			}
        			// 市-区/县的数据，保存到mDistrictDatasMap
        			mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
        		}
        		// 省-市的数据，保存到mCitisDatasMap
        		mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        	}
        } catch (Throwable e) {  
            e.printStackTrace();  
        } finally {
        	
        } 
	}
	@Override
	public void select_ok(JSONObject result) {
		timepicker_result =result;
		try{
			tv_time.setText(timepicker_result.getString("hour_start_am")+":"+timepicker_result.getString("minute_start_am")+" - "+timepicker_result.getString("hour_end_am")+":"+timepicker_result.getString("minute_end_am")
    			+" | "+timepicker_result.getString("hour_start_pm")+":"+timepicker_result.getString("minute_start_pm")+" - "+timepicker_result.getString("hour_end_pm")+":"+timepicker_result.getString("minute_end_pm"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private Select_ShopType.SelectListener shopTypeListener =new Select_ShopType.SelectListener() {
		
		@Override
		public void select_ok(JSONObject result) {
			selectShopType_result=result;
			try {
				tv_type.setText(result.getString("text"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	};
	@Override
	public void onResponse(JSONObject response) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		try {
			MyUtil.alert(response.getString("Message"), getContext());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		error.printStackTrace();
		try {
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyUtil.alert("请检查网络后重试", getContext());
	}
}
