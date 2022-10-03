package com.study.A_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.study.B_vo.ProgramListVO;

public class ProgramListDAO {
	Scanner sc = new Scanner(System.in);

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	static {
		try {
			Class.forName(CommonDriver.DRIVER);

		} catch (ClassNotFoundException e) {
			System.out.println(" >> JDBC, DRIVER LOADING Fail");
		}
	}

	// 프로그램정보조회 기능
	public List<ProgramListVO> selectAll(String programName) {
		List<ProgramListVO> list = new ArrayList<ProgramListVO>();

		try {
			conn = CommonDriver.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PROGRAMNUM, PROGRAMNAME, PROGRAMID, CLASSNAME, TIME, MAXIMUM, PRICE, TEACHER");
			sql.append(" FROM PROGRAM_LIST");
			sql.append(" WHERE PROGRAMNAME = ?  ");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, programName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProgramListVO vo = new ProgramListVO(rs.getInt("PROGRAMNUM"), rs.getString("PROGRAMNAME"),
						rs.getInt("PROGRAMID"), rs.getString("CLASSNAME"), rs.getString("TIME"), rs.getInt("MAXIMUM"),
						rs.getInt("PRICE"), rs.getString("TEACHER"));
				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(conn, pstmt,rs);
		}
		return list;
	}

	// 프로그램 수정 기능 중 전 프로그램 목록 출력(프로그램 번호, 프로그램 이름, 과목 번호, 과목 번호, 클래스 이름, 비용, 시간)
	public void proDateAll(ProgramListVO pvo) {
		System.out.println(pvo.getProgramNum() + "\t" + pvo.getProgramID() + "\t" + pvo.getClassName() + "\t"
				+ pvo.getTime() + "\t" + pvo.getMaximum() + "\t" + pvo.getPrice() + "\t" + pvo.getTeacher());
	}

	public void proDateAll(List<ProgramListVO> list) {
		for (ProgramListVO vo : list) {
			proDateAll(vo);
		}
		System.out.println();
	}

	// 수강과목삭제시 해당과목이 있는지 기능
	public boolean selectInfo(String programName) {
		boolean result = false;
		try {
			conn = CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PROGRAMNAME ");
			sql.append("FROM PROGRAM_LIST ");
			sql.append("WHERE PROGRAMNAME=? ");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, programName);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(conn, pstmt,rs);
		}
		return result;
	}

	// 수강과목삭제시 해당과목이 있는지 기능
	public boolean selectInfo2(String programId) throws SQLSyntaxErrorException {
		boolean result = false;
		try {
			conn = CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PROGRAMID ");
			sql.append("FROM PROGRAM_LIST ");
			sql.append("WHERE PROGRAMID=?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, programId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			
		} finally {
			CommonDriver.close(conn, pstmt,rs);
		}
		return result;
	}

	// 프로그램수정시 해당종목이 있는지 기능
	public boolean selectInfo2(int programNum) {
		boolean result = false;
		try {
			conn = CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT PROGRAMNUM ");
			sql.append("FROM PROGRAM_LIST ");
			sql.append("WHERE PROGRAMNUM=?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, programNum);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(conn, pstmt);
		}
		return result;
	}

	// 프로그램 정보수정 기능(과목이름변경)
	public void updateClassName(String choice, String programId) {
		try {
			conn = CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PROGRAM_LIST ");
			sql.append(" SET CLASSNAME=? ");
			sql.append("WHERE PROGRAMID=?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, choice);
			pstmt.setString(2, programId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(conn, pstmt);
		}
	}

	// 프로그램정보수정 기능(시간변경)
	public void updateTime(String choice, String programId) {
		try {
			conn = CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PROGRAM_LIST ");
			sql.append(" SET TIME=? ");
			sql.append("WHERE PROGRAMID=?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, choice);
			pstmt.setString(2, programId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(conn, pstmt);
		}
	}

	// 프로그램정보수정 기능(강사변경)
	public void updateTeacher(String choice, String programId) {
		try {
			conn = CommonDriver.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PROGRAM_LIST ");
			sql.append(" SET TEACHER=? ");
			sql.append("WHERE PROGRAMID=?");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, choice);
			pstmt.setString(2, programId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(conn, pstmt);
		}
	}

	// 프로그램추가 기능
	public void insert(ProgramListVO vo) {
		try {
			conn = CommonDriver.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PROGRAM_LIST");
			sql.append(" (PROGRAMNUM, PROGRAMNAME, PROGRAMID, CLASSNAME, TIME, MAXIMUM, PRICE, TEACHER)");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, vo.getProgramNum());
			pstmt.setString(2, vo.getProgramName());
			pstmt.setInt(3, vo.getProgramID());
			pstmt.setString(4, vo.getClassName());
			pstmt.setString(5, vo.getTime());
			pstmt.setInt(6, vo.getMaximum());
			pstmt.setInt(7, vo.getPrice());
			pstmt.setString(8, vo.getTeacher());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(conn, pstmt);
		}
	}

	// 프로그램 삭제기능
	public int delete(int PROGRAMID) {
		int result = 0;

		try {
			conn = CommonDriver.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM PROGRAM_LIST WHERE PROGRAMID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, PROGRAMID);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CommonDriver.close(conn, pstmt);
		}

		return result;
	}

}