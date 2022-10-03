package com.study.B_vo;

public class Join_SearchVO {//수강신청항목 선택시 수강신청내역조회에 사용
	private String memberId;  
	private String programName;
	private int programID;
	private String className;
	private String time;
	private int price;
	private String teacher;	

	public Join_SearchVO() { }
	
	public Join_SearchVO(String memberId, String programName, int programID, String className, 
							String time, int price, String teacher) {
		this.memberId = memberId;
		this.programName = programName;
		this.programID = programID;
		this.className = className;
		this.time = time;
		this.price = price;
		this.teacher = teacher;		
	}

	@Override
	public String toString() {
		return "Join_SearchVO [programName=" + programName + ", className=" + className + ", time=" + time + ", price="
				+ price + ", teacher=" + teacher + ", memberId=" + memberId + ", programId=" + programID + "]";
	}

	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public int getProgramID() {
		return programID;
	}
	public void setProgramID(int programID) {
		this.programID = programID;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	
}
