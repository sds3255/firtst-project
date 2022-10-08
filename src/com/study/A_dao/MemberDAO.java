package com.study.A_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.study.B_vo.MemberVO;

public class MemberDAO {
	Connection con;
	PreparedStatement pstmt;
	private ResultSet rs;
	Scanner scan = new Scanner(System.in);

	static {
		try {
			Class.forName(CommonDriver.DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생]JDBC DRIVER LOADING FAIL");
		}
	}

	// MEMBER TABLE INSERT METHOD (회원가입시)
	public void insert(MemberVO vo) {
		try {
			con = CommonDriver.getConnection();
			
			String sql = "";
			sql += "INSERT INTO MEMBER ";
			sql += "     (MEMBERID, PASSWORD, NAME, BIRTH_DATE, PHONE_NUM, EMAIL, ADDRESS ) ";
			sql += "VALUES (?, ?, ?, ?, ? || '-' || ? || '-' || ?, ?, ? )";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getBirthdate());
			pstmt.setString(5, vo.getPhoneNum().substring(0, 3));
			pstmt.setString(6, vo.getPhoneNum().substring(3,7));
			pstmt.setString(7, vo.getPhoneNum().substring(7));
			pstmt.setString(8, vo.getEmail());
			pstmt.setString(9, vo.getAddress());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con, pstmt);
		}
	}

	// MEMBER TABLE SELECT METHOD(회원정보조회)
	public MemberVO select(MemberVO vo) {
		try {
			con = CommonDriver.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MEMBERID, PASSWORD, NAME, TO_CHAR(BIRTH_DATE,'YYYY-MM-DD') BIRTH, PHONE_NUM, EMAIL, ADDRESS, MEMBERSHIP_STARTDATE ");
			sql.append("FROM MEMBER ");
			sql.append("WHERE MEMBERID=? AND PASSWORD=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new MemberVO(rs.getString("MEMBERID"), rs.getString("PASSWORD"), rs.getString("NAME"),
						rs.getString("BIRTH"), rs.getString("PHONE_NUM"), rs.getString("EMAIL"),
						rs.getString("ADDRESS"), rs.getDate("MEMBERSHIP_STARTDATE")); }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con, pstmt,rs);
		}
		return vo;
	}
	
	//로그인할때 검색기능
	public boolean selectInfo(String id,String pw) {
		boolean result =false;
		try {
			con = CommonDriver.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MEMBERID, PASSWORD ");
			sql.append("FROM MEMBER ");
			sql.append("WHERE MEMBERID=? AND PASSWORD= ?");
			
			pstmt = con.prepareStatement(sql.toString());		
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {  result = true; }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			CommonDriver.close(con, pstmt,rs);
		}		
		return result;
	}
	
	//회원가입시 아이디 중복체크기능
	public boolean selectInfo(String id) {
		boolean result = false;
		try {
			con = CommonDriver.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MEMBERID ");
			sql.append("FROM MEMBER ");
			sql.append("WHERE MEMBERID=?");
			
			pstmt = con.prepareStatement(sql.toString());			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if (rs.next()) { result = true;	}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con, pstmt,rs);
		}		
		return result;
	}
	
	//회원가입시 동명이인 체크기능
	public boolean selectSameName(String name,String birthdate) {
		boolean result = false;
		try {
			con = CommonDriver.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT NAME, BIRTH_DATE ");
			sql.append("FROM MEMBER ");
			sql.append("WHERE NAME=? AND BIRTH_DATE=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			pstmt.setString(2, birthdate);
			
			rs = pstmt.executeQuery();
			if (rs.next()) { result = true;	}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con, pstmt,rs);
		}		
		return result;
	}
	
	// MEMBER TABLE DELETE METHOD(회원정보삭제=탈퇴)
	public void delete(MemberVO vo) {
		try {
			con = CommonDriver.getConnection();

			String sql = "";
			sql += "DELETE FROM MEMBER ";
			sql += "WHERE MEMBERID=? AND PASSWORD=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con, pstmt);
		}
	}

	// MEMBER TABLE UPDATE METHOD(회원정보수정-비밀번호)
	public void updatePassword(String pw, String id) {
		try {
			con = CommonDriver.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MEMBER ");
			sql.append(" SET PASSWORD=? ");
			sql.append("WHERE MEMBERID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pw);
			pstmt.setString(2, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con, pstmt);
		}
	}
	
	// MEMBER TABLE UPDATE METHOD(회원정보수정-휴대폰번호)
	public void updatePhoneNum(String phoneNum, String id) {
		try {
			con = CommonDriver.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MEMBER ");
			sql.append(" SET PHONE_NUM= ? || '-' || ? || '-' || ? ");
			sql.append("WHERE MEMBERID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, phoneNum.substring(0, 3));
			pstmt.setString(2, phoneNum.substring(3, 7));
			pstmt.setString(3, phoneNum.substring(7));
			pstmt.setString(4, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con, pstmt);
		}
	}
	
	// MEMBER TABLE UPDATE METHOD(회원정보수정-이메일)
	public void updateEmail(String email, String id) {
		try {
			con = CommonDriver.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MEMBER ");
			sql.append(" SET EMAIL=? ");
			sql.append("WHERE MEMBERID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			pstmt.setString(2, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con, pstmt);
		}
	}
	
	// MEMBER TABLE UPDATE METHOD(회원정보수정-주소)
	public void updateAddress(String address, String id) {
		try {
			con = CommonDriver.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE MEMBER ");
			sql.append(" SET ADDRESS=? ");
			sql.append("WHERE MEMBERID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, address);
			pstmt.setString(2, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con, pstmt);
		}
	}

}
