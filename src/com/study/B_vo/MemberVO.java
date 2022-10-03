package com.study.B_vo;

import java.util.Date;

public class MemberVO {
	private String id;
	private String pw;
	private String name;
	private String birthdate;
	private String phoneNum;
	private String email ;
	private String address ;
	private Date date ;
	
	public MemberVO() {}

	public MemberVO(String id, String pw, String name, String birthdate, String phoneNum, String email, String address, Date date) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.birthdate = birthdate;
		this.phoneNum = phoneNum;
		this.email = email;
		this.address = address;
		this.date = date;

	}

	public MemberVO(String id, String pw, String name, String birthdate, String phoneNum) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.birthdate = birthdate;
		this.phoneNum = phoneNum;

	}
	
	public MemberVO(String id, String pw, String name, String birthdate, String phoneNum, String address) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.birthdate = birthdate;
		this.phoneNum = phoneNum;
		this.address = address;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}



	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pw=" + pw + ", name=" + name + ", birthdate=" + birthdate + ", phoneNum="
				+ phoneNum + ", email=" + email + ", address=" + address + ", date=" + date +"]";
	}

}
