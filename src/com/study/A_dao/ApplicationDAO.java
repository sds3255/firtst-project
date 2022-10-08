package com.study.A_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import com.study.B_vo.ApplicationVO;
import com.study.B_vo.Join_SearchVO;

public class ApplicationDAO {
	Connection con;
	PreparedStatement pstmt;
	private ResultSet rs;

	static {
		try {
			Class.forName(CommonDriver.DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생]JDBC DRIVER LOADING FAIL");
		}
	}

	// INSERT(수강신청시 정보입력)---->INDEX(+)
	public void insert(ApplicationVO vo) {
		try {
			con = CommonDriver.getConnection();
			String sql = "";
			sql += "INSERT INTO APPLICATION ";
			sql += "     (APPLICATIONID, MEMBERID, PROGRAMID, CLASS_CHECK) ";
			sql += "VALUES (APPLICATION_SEQ.NEXTVAL, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, vo.getMemberId());
			pstmt.setInt(2, vo.getProgramId());
			pstmt.setString(3, vo.getClassCheck());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con,pstmt);
		}
	}

	// 수강신청시 과목 중복체크기능
	public boolean selectInfo(String id, int programId) {
		boolean result = false;
		try {
			con = CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PROGRAMID ");
			sql.append("FROM APPLICATION ");
			sql.append("WHERE MEMBERID=? AND PROGRAMID = ? AND CLASS_CHECK='수강'");
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, id);
			pstmt.setInt(2, programId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con,pstmt,rs);
		}
		return result;
	}

	// 수강신청시 초과인원확인기능
	public boolean selectInfo(int programId) {
		boolean result = false;
		try {
			con = CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT A.PROGRAMID ");
			sql.append("FROM APPLICATION A, PROGRAM_LIST P ");
			sql.append(
					"WHERE A.PROGRAMID = P.PROGRAMID AND P.MAXIMUM<=(SELECT COUNT(*) FROM APPLICATION WHERE PROGRAMID=? GROUP BY ?)");
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setInt(1, programId);
			pstmt.setInt(2, programId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con,pstmt,rs);
		}
		return result;
	}

	// SELECT(수강신청조회)
	public List<Join_SearchVO> select(String id) throws SQLSyntaxErrorException {
		Join_SearchVO vo = null;
		List<Join_SearchVO> list = new ArrayList<Join_SearchVO>();
		boolean num= true;
		try {
			con =CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MEMBERID,PROGRAMNAME,A.PROGRAMID,CLASSNAME,TIME,PRICE,TEACHER ");
			sql.append("FROM APPLICATION A , PROGRAM_LIST P  ");
			sql.append("WHERE A.PROGRAMID = P.PROGRAMID AND A.MEMBERID=? ");
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			while (num) {
				if (rs.next()) {
					vo = new Join_SearchVO(rs.getString("MEMBERID"), rs.getString("PROGRAMNAME"), rs.getInt("PROGRAMID"),
							rs.getString("CLASSNAME"), rs.getString("TIME"), rs.getInt("PRICE"),
							rs.getString("TEACHER"));
					list.add(vo);
				}else {
				num=false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con,pstmt,rs);
		}
		return list;
	}

	// UPDATE(수강신청취소) //1.수강취소(비수강) 2.취소(메인메뉴로 돌아가기)
	public void updateClassCheck(int id, int num) {

		try {
			con = CommonDriver.getConnection();

			String sql = "";
			sql += "UPDATE APPLICATION SET CLASS_CHECK =? ";
			sql += "WHERE MEMBERID=? ";
			pstmt = con.prepareStatement(sql);
			if (num == 1) {
				pstmt.setString(1, "비수강");
			} else if (num == 2) {
				pstmt.setString(1, "수강");
			}
			pstmt.setInt(2, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con,pstmt);
		}
	}

	// DELETE(수강신청취소) 
	public void delete(String id, String programId) {
		try {
			con =CommonDriver.getConnection();

			String sql = "";
			sql += "DELETE FROM APPLICATION ";
			sql += "WHERE MEMBERID=? AND PROGRAMID=? ";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, programId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(con,pstmt);
		}
	}
	
	

}
