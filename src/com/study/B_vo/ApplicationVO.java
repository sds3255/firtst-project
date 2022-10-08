package com.study.B_vo;

import java.util.Date;

public class ApplicationVO {
	private int applicationtId; 
	private String memberId;  //MEMBER JOIN
	private int programId; //PROGRAM_LIST JOIN
	private Date classDate;
	private String classCheck;
	
	public ApplicationVO() {	}

	public ApplicationVO(String memberId, int programId,  String classCheck) {

		this.memberId = memberId;
		this.programId = programId;	
		this.classCheck = classCheck;
	}

	public ApplicationVO(int applicationtId, String memberId, int programId, Date classDate, String classCheck) {
		this.applicationtId = applicationtId;
		this.memberId = memberId;
		this.programId = programId;
		this.classDate = classDate;
		this.classCheck = classCheck;
	}

	public int getApplicationtId() {
		return applicationtId;
	}

	public void setApplicationtId(int applicationtId) {
		this.applicationtId = applicationtId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public Date getClassDate() {
		return classDate;
	}

	public void setClassDate(Date classDate) {
		this.classDate = classDate;
	}

	public String getClassCheck() {
		return classCheck;
	}

	public void setClassCheck(String classCheck) {
		this.classCheck = classCheck;
	}

	@Override
	public String toString() {
		return "ApplictionVO [applicationtId=" + applicationtId + ", memberId=" + memberId + ", programId=" + programId
				+ ", classDate=" + classDate + ", classCheck=" + classCheck + "]";
	}
	

	
}
