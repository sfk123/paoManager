package com.shengping.paomanager.util;

public class UrlUtil {
	private final static String host="http://192.168.1.63:8080/paotui";
	public final static String Service_Shop="/shopservice/";
	public final static String Business_Logo="/images/business/logo/";

	public static String getUrl(String method,String service){
		return host+service+method;
	}
	public static String getAgreementUrl(){//用户协议地址
		return host+"/html/agreement.html";
	}
	public static String getUrl(String service){
		return host+service;
	}
}
