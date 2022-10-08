package com.study.method;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.study.A_dao.ApplicationDAO;
import com.study.A_dao.MemberDAO;
import com.study.A_dao.ProgramListDAO;
import com.study.B_vo.ApplicationVO;
import com.study.B_vo.Join_SearchVO;
import com.study.B_vo.MemberVO;
import com.study.B_vo.ProgramListVO;

public class Application {// 수강신청 항목 "2" 선택시
	static Scanner scan = new Scanner(System.in);
	static ApplicationDAO adao = new ApplicationDAO();
	static ApplicationVO avo = new ApplicationVO();
	static ProgramListDAO pdao = new ProgramListDAO();
	static ProgramListVO pvo = new ProgramListVO();
	static MemberDAO mdao = new MemberDAO();
	static MemberVO mvo = new MemberVO();
	static Join_SearchVO jvo = new Join_SearchVO();

	static List<ProgramListVO> list = null;
	static List<Join_SearchVO> list2 = null;
	static List<Integer> list3 = null;

	static String inputId;
	static String inputPw;
	static String programId;
	static String choice;
	static String programName;

	public static void lecture() {
		login(); // 로그인 먼저하기

		while (true) {
			System.out.println("원하는 항목의 번호를 선택하세요. \n1.수강신청 2.수강신청결과조회 3.수강신청취소 4.뒤로가기");
			System.out.print(">>>");
			choice = scan.nextLine();
			if (choice.equals("1")) {// 수강신청
				try {
					application();
				} catch (Exception e) {
					System.out.println("잘못입력했습니다. 과정번호를 숫자로 정확하게 입력하세요.");
				}
			} else if (choice.equals("2")) {// 수강신청결과조회
				try {
					list2 = adao.select(inputId);
					if (list2.isEmpty() != true) {
						System.out.println("<수강신청내역>");
						for (Join_SearchVO vo : list2) {
							System.out.println(vo);
						}
					} else {
						System.out.println("수강신청내역이 없습니다.");
					}
				} catch (SQLSyntaxErrorException e) {
					System.out.println("잘못 입력했습니다. 숫자로 정확하게 입력하세요");
				}
			} else if (choice.equals("3")) {// 수강신청취소
				lectureCancel();
			} else if (choice.equals("4")) {// 뒤로가기
				Main_Operation.operation();
				break;
			} else {
				System.out.println("잘못 입력했습니다. 다시 입력하세요");
				System.out.print(">>>");
			}
		}
	}

	// 로그인기능 구현
	public static void login() {
		boolean num = true;
		while (num) {
			System.out.print("아이디 입력>>>");
			inputId = scan.nextLine();
			System.out.print("비밀번호 입력>>>");
			inputPw = scan.nextLine();
			if (mdao.selectInfo(inputId, inputPw) == true) {
				System.out.println("로그인에 성공했습니다.");
				mvo.setId(inputId);
				mvo.setPw(inputPw);
				num = false;
			} else {
				if (mdao.selectInfo(inputId) == false) {
					System.out.println("해당되는 회원이 없습니다. 회원가입을 해주세요.");
					Main_Operation.operation();
					break;
				} else {
					System.out.println("아이디 또는 비밀번호가 일치하지 않습니다. 다시 입력하세요");
				}
			}
		}
	}

	// 수강신청 =>동일강좌 신청불가 및 수강과목 유무, 초과인원시 신청불가, 원하는 수업없을시 뒤로가기(프로그램다시고를수있는 기회) 기능 구현
	private static void application() throws SQLSyntaxErrorException, NumberFormatException {
		while (true) {
			System.out.println("신청을 원하는 종목명을 입력하세요.");
			List<String> pro = adao.selectProg();
			for(String li:pro) {
				System.out.print("\t"+li+"\t||");
			}
			System.out.println();
			System.out.print(">>>");
			programName = scan.nextLine();
			System.out.println("\n - 현재 운영중인 " + programName + " 과정 목록 -");
			if (pdao.selectInfo(programName) == true) {
				list = pdao.selectAll(programName);
				for (ProgramListVO vo : list) {
					System.out.println("[과목번호]"+vo.getProgramNum() + "\t" + "[종목명]"+vo.getProgramName() + "\t" + "[과정번호]"+vo.getProgramID() + "\t"
							+ "[과정명]"+vo.getClassName() + "\t   " + "[시간]"+vo.getTime() + "\t" + "[최대수강인원]"+vo.getMaximum() + "\t "
							+ "[가격]"+vo.getPrice() + "\t " + "[강사명]"+vo.getTeacher());
					System.out.println();
				}
				System.out.println("신청을 원하는 과정번호(programID)를 입력하세요. 뒤로가기를 원하면 #을 입력해주세요.");
				System.out.print(">>>");
				while (true) {
					programId = scan.nextLine();
					if (programId.equals("#")) {
						break;
					} else {
						if (adao.selectInfo(Integer.parseInt(programId)) == true) {
							System.out.println("해당과정은 수강인원이 초과되었습니다. 다른 과정의 번호(programID)를 선택하세요.");
							System.out.print(">>>");
						} else if (adao.selectInfo(inputId, Integer.parseInt(programId)) == true) {
							System.out.println("중복수강은 할 수 없습니다. 다른과정을 선택하세요.");
							System.out.print(">>>");
						} else if (pdao.selectInfo2(programId) == true) {
							while (true) {
								System.out.println("수강을 하시겠습니까? 1.네 또는 2.아니오");
								choice = scan.nextLine();
								if (choice.equals("1")) {
									avo.setProgramId(Integer.parseInt(programId));
									avo.setMemberId(inputId);
									avo.setClassCheck("수강");
									adao.insert(avo);
									System.out.println("수강신청이 완료되었습니다.");
									break;
								} else if (choice.equals("2")) {
									System.out.println("수강신청이 취소되었습니다.");
									break;
								} else {
									System.out.println("잘못 입력했습니다. 다시 입력하세요");
									System.out.println(">>>");
								}
							}
							break;
						} else {
							System.out.println("해당하는 수강과정이 없습니다. 다시입력하세요");
							System.out.println(">>>");
						}
					}
				}
				break;
			} else {
				System.out.println("해당하는 수강과정이 없습니다.");
			}
		}
	}

	// 수강신청취소
	private static void lectureCancel() {
		list3 = new ArrayList<Integer>();
		try {
			list2 = adao.select(inputId);
			if (list2.isEmpty() != true) {
				System.out.println("<수강신청내역>");
				for (Join_SearchVO vo : list2) {
					System.out.println(vo);
					list3.add(vo.getProgramID());
				}
				System.out.println("취소를 원하는 과정번호(programId)를 입력하세요.");
				System.out.print(">>>");
				programId = scan.nextLine();
				if (list3.contains(Integer.parseInt(programId))) {
					System.out.println("정말로 수강취소를 하시겠습니까? 1.네 2.아니오");
					System.out.print(">>>");
					while (true) {
						choice = scan.nextLine();
						if (choice.equals("1")) {
							adao.delete(inputId, programId);
							System.out.println("수강취소가 완료되었습니다.");
							break;
						} else if (choice.equals("2")) {
							break;
						} else {
							System.out.println("잘못 입력했습니다. 다시 입력하세요");
							System.out.print(">>>");
						}
					}
				} else {
					System.out.println("해당 과정은 수강신청내역에 없습니다.");
				}
			} else {
				System.out.println("수강신청내역이 없습니다.");
			}
		} catch (Exception e) {
			System.out.println("잘못 입력했습니다.");
		}
	}
}