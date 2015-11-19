package com.shengping.paomanager.popup;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.shengping.paomanager.model.CityModel;
import com.shengping.paomanager.model.DistrictModel;
import com.shengping.paomanager.model.ProvinceModel;

public class CityUtil {
	private Context context;
	public static CityUtil getInstence(Context context){
		return new CityUtil(context);
	}
	public CityUtil(Context context){
		this.context=context;
		initProvinceDatas();
	}
	/**
	 * ����ʡ
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - ʡ value - ��
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - �� values - ��
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
	
	/**
	 * key - �� values - �ʱ�
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>(); 
	protected void initProvinceDatas()
	{
		List<ProvinceModel> provinceList = null;
    	AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // ����һ������xml�Ĺ�������
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// ����xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// ��ȡ��������������
			provinceList = handler.getDataList();
			//*/
			mProvinceDatas = new String[provinceList.size()];
        	for (int i=0; i< provinceList.size(); i++) {
        		// ��������ʡ������
        		mProvinceDatas[i] = provinceList.get(i).getName();
        		List<CityModel> cityList = provinceList.get(i).getCityList();
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// ����ʡ����������е�����
        			cityNames[j] = cityList.get(j).getName();
        			List<DistrictModel> districtList = cityList.get(j).getDistrictList();
        			String[] distrinctNameArray = new String[districtList.size()];
        			DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// ����������������/�ص�����
        				DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				// ��/�ض��ڵ��ʱ࣬���浽mZipcodeDatasMap
        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				distrinctArray[k] = districtModel;
        				distrinctNameArray[k] = districtModel.getName();
        			}
        			// ��-��/�ص����ݣ����浽mDistrictDatasMap
        			mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
        		}
        		// ʡ-�е����ݣ����浽mCitisDatasMap
        		mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        	}
        } catch (Throwable e) {  
            e.printStackTrace();  
        } finally {
        	
        } 
	}
	public JSONObject getByCode(String code){
		JSONObject json=new JSONObject();
		try{
			for (Map.Entry<String, String> entry : mZipcodeDatasMap.entrySet()) {  
	//			 System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
				if(entry.getValue().equals(code)){
					json.put("district", entry.getKey());
					break;
				}
			}
			if(json.has("district")){
				L1:
				for (Map.Entry<String, String[]> entry : mDistrictDatasMap.entrySet()) {  
					String[] temp=entry.getValue();
					for(String s:temp){
						if(s.equals(json.getString("district"))){
							json.put("city", entry.getKey());
							break L1;
						}
					}
				}
			}else{
				Log.e("cmd", "��ǰ�������ݲ��������б��룺"+code);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		return json;
	}
}
