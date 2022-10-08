package com.study.method;

import java.util.List;
import java.util.Scanner;

import com.study.A_dao.ApplicationDAO;
import com.study.A_dao.ProgramListDAO;
import com.study.B_vo.ProgramListVO;

public class ProgramSearch {// 프로그램정보조회 항목 "4" 선택시
	static Scanner scan = new Scanner(System.in);
	static ProgramListDAO pdao = new ProgramListDAO();
	static ApplicationDAO adao = new ApplicationDAO();
	static List<ProgramListVO> list = null;

	static String choice;
	static String programId;

	// 프로그램검색시 해당프로그램이 있는지 유무체크하여 해당플그램의 수업정보를 출력하는 기능 구현
	public static void search() {
		while (true) {
			System.out.println("원하는 항목의 번호를 선택하세요.\n 1.프로그램조회 2.뒤로가기");
			System.out.print(">>>");
			choice = scan.nextLine();
			if (choice.equals("1")) {// 프로그램조회
				List<String> pro = adao.selectProg();
				for (String li : pro) {
					System.out.print("\t" + li + "\t||");
				}
				System.out.println();
				System.out.println("조회하려는 종목명을 입력하세요.");
				System.out.print(">>>");
				choice = scan.nextLine();
				if (pdao.selectInfo(choice) == true) {
					System.out.println("\n - 현재 운영중인 " + choice + " 과정 목록 -");
					list = pdao.selectAll(choice);
					for (ProgramListVO vo : list) {
						System.out.println("[과목번호]" + vo.getProgramNum() + "\t" + "[종목명]" + vo.getProgramName() + "\t"
								+ "[과정번호]" + vo.getProgramID() + "\t" + "[과정명]" + vo.getClassName() + "\t   " + "[시간]"
								+ vo.getTime() + "\t" + "[최대수강인원]" + vo.getMaximum() + "\t " + "[가격]" + vo.getPrice()
								+ "\t " + "[강사명]" + vo.getTeacher());
						System.out.println();
					}
				} else {
					System.out.println("입력한 프로그램이 없습니다.");
				}
			} else if (choice.equals("2")) {// 뒤로가기
				Main_Operation.operation();
				break;

			} else {
				System.out.println("잘못 입력했습니다.");
			}
		}
	}
}