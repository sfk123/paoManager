package com.shengping.paomanager.model;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.mapapi.model.LatLng;

public class Business {
	private int id;
	private String name;//商家名称
	private String address;//商家地址
	private String logoUrl;//商家logo
	private int type;//店铺类型
	private String Area;//城市编码
	private String discount;//商家折扣
	private JSONObject time;//营业时间
	private int star;//星级
	private JSONArray phone;//店铺电话
	private String loginName;//登陆名称
	private String loginPwd;//登陆密码
	private int status;//营业状态
	private String token;//令牌
	private LatLng location;//商家坐标
	private double turnover;//营业额
	private double KeTiXian;//可提现额度
	private String Bank;//开户行
	private String BankNumber;//卡号或支付宝号
	private String BankName;//户名
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public JSONObject getTime() {
		return time;
	}
	public void setTime(JSONObject time) {
		this.time = time;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public JSONArray getPhone() {
		return phone;
	}
	public void setPhone(JSONArray phone) {
		this.phone = phone;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public LatLng getLocation() {
		return location;
	}
	public void setLocation(LatLng location) {
		this.location = location;
	}
	public double getTurnover() {
		return turnover;
	}
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	public double getKeTiXian() {
		return KeTiXian;
	}
	public void setKeTiXian(double keTiXian) {
		KeTiXian = keTiXian;
	}
	public String getBank() {
		return Bank;
	}
	public void setBank(String bank) {
		Bank = bank;
	}
	public String getBankNumber() {
		return BankNumber;
	}
	public void setBankNumber(String bankNumber) {
		BankNumber = bankNumber;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
}
