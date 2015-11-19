package com.shengping.paomanager.util;

import java.util.HashMap;
import java.util.Map;

public class ShopTypeUtil {
	private Map<String, Integer> types=new HashMap<String, Integer>();
	public static ShopTypeUtil getInstence(){
		return new ShopTypeUtil();
	}
	public ShopTypeUtil(){
		types.put("����", 1);
    	types.put("ˮ����", 2);
    	types.put("����", 3);
    	types.put("�����", 4);
    	types.put("��ׯ", 5);
    	types.put("�ʻ���", 6);
    	types.put("��Ȥ��", 7);
    	types.put("ҩ��", 8);
    	types.put("ϴ�µ�", 9);
    	types.put("�⳵��", 10);
	}
	public String getTypeByValue(int type){
		for (Map.Entry<String, Integer> entry : types.entrySet()) {
			if(type==entry.getValue()){
				return entry.getKey();
			}
		}
		return null;
	}
	public Map<String, Integer> getData(){
		return types;
	}
}
