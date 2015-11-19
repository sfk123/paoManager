package com.shengping.paomanager.model;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.mapapi.model.LatLng;

public class Business {
	private int id;
	private String name;//�̼�����
	private String address;//�̼ҵ�ַ
	private String logoUrl;//�̼�logo
	private int type;//��������
	private String Area;//���б���
	private String discount;//�̼��ۿ�
	private JSONObject time;//Ӫҵʱ��
	private int star;//�Ǽ�
	private JSONArray phone;//���̵绰
	private String loginName;//��½����
	private String loginPwd;//��½����
	private int status;//Ӫҵ״̬
	private String token;//����
	private LatLng location;//�̼�����
	private double turnover;//Ӫҵ��
	private double KeTiXian;//�����ֶ��
	private String Bank;//������
	private String BankNumber;//���Ż�֧������
	private String BankName;//����
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
