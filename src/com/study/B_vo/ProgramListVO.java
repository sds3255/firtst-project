package com.study.B_vo;

public class ProgramListVO {
	private int programNum;
	private String programName;
	private int programID;
	private String className;
	private String time;
	private int maximum;
	private int price;
	private String teacher;
	
	public ProgramListVO() { }

	public ProgramListVO(int programID, String className, String time, int maximum, int price, String teacher) {
		super();
		this.programID = programID;
		this.className = className;
		this.time = time;
		this.maximum = maximum;
		this.price = price;
		this.teacher = teacher;
	}

	public ProgramListVO(int programNum, String programName, int programID, String className, String time, int maximum,
			int price, String teacher) {
		this.programNum = programNum;
		this.programName = programName;
		this.programID = programID;
		this.className = className;
		this.time = time;
		this.maximum = maximum;
		this.price = price;
		this.teacher = teacher;
	}

	public int getProgramNum() {
		return programNum;
	}

	public void setProgramNum(int programNum) {
		this.programNum = programNum;
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

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
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

	@Override
	public String toString() {
		return "ProgramListVO [programNum=" + programNum + ", programName=" + programName + ", programID=" + programID
				+ ", className=" + className + ", time=" + time + ", maximum=" + maximum + ", price=" + price
				+ ", teacher=" + teacher + "]";
	}
	
	
	
}