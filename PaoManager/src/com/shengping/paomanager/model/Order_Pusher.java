package com.shengping.paomanager.model;

import java.util.Date;
import java.util.List;

public class Order_Pusher {
	private String orderNum ;//������
	private String payType;//֧����ʽ
	private Date date;//�µ�ʱ��
	private String userName;//�û�����
	private String sex;//�û��Ա�
	private String userPhone;//�û��绰
	private List<Business> businesses;//�̼��б�
	private String address;//�û���ַ
	private double total;//���ƽ��
	private int status;//����״̬1�ǿ���������2��������ɣ�3��ʧ��
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public List<Business> getBusinesses() {
		return businesses;
	}
	public void setBusinesses(List<Business> businesses) {
		this.businesses = businesses;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
