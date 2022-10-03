package com.study.A_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MasterDAO {	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	static Scanner scan = new Scanner(System.in);
	
	static {
		try {
			Class.forName(CommonDriver.DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(" JDBC 로딩 실패 ");
		}		
	}
	
	//로그인할때 검색기능
	public boolean selectInfo(String id,String pw) {
		boolean result =false;
		try {
			conn = CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MASTERID, MASTERPW ");
			sql.append("FROM MASTER ");
			sql.append("WHERE MASTERID=? AND MASTERPW= ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			if (rs.next()) 
					result = true;
							
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(conn, pstmt, rs);
		}		
		return result;
	}
	
}