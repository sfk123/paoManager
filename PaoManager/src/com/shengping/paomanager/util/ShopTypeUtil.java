package com.shengping.paomanager.util;

import java.util.HashMap;
import java.util.Map;

public class ShopTypeUtil {
	private Map<String, Integer> types=new HashMap<String, Integer>();
	public static ShopTypeUtil getInstence(){
		return new ShopTypeUtil();
	}
	public ShopTypeUtil(){
		types.put("²ÍÌü", 1);
    	types.put("Ë®¹ûµê", 2);
    	types.put("³¬ÊÐ", 3);
    	types.put("³èÎïµê", 4);
    	types.put("¾Æ×¯", 5);
    	types.put("ÏÊ»¨µê", 6);
    	types.put("ÇéÈ¤µê", 7);
    	types.put("Ò©µê", 8);
    	types.put("Ï´ÒÂµê", 9);
    	types.put("×â³µµê", 10);
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
