package com.study.method;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Scanner;

import com.study.A_dao.MasterDAO;
import com.study.A_dao.ProgramListDAO;
import com.study.B_vo.MasterVO;
import com.study.B_vo.ProgramListVO;

public class AdmPage {// 관리자페이지 항목 "5" 선택시
	static Scanner scan = new Scanner(System.in);
	static ProgramListVO pvo = new ProgramListVO();
	static ProgramListDAO pdao = new ProgramListDAO();
	static MasterVO mvo = new MasterVO();
	static MasterDAO mdao = new MasterDAO();
	static List<ProgramListVO> list = null;
	static String choice;
	static String programId;
	static String programName;
	static String inputId;
	static String inputPw;

	public static void programUpdate() {
		boolean num = true;
		System.out.println("###시스템관리자만 접근가능합니다###");
		admLogin();
		while (num) {
			System.out.println("원하는 항목의 번호를 선택하세요.\n 1.프로그램수정 2.프로그램추가 3.프로그램삭제 4.뒤로가기");
			System.out.print(">>>");
			choice = scan.nextLine();
			if (choice.equals("1")) {// 프로그램수정
				selectProgram();
			} else if (choice.equals("2")) {// 프로그램추가
				System.out.println("추가하는 프로그램의 정보를 입력하세요.");
				insertProgram();
			} else if (choice.equals("3")) {// 프로그램삭제
				deleteProgram();
			} else if (choice.equals("4")) {// 뒤로가기
				Main_Operation.operation();
				num = false;
			} else {
				System.out.println("잘못 입력했습니다. 다시 입력해주세요");
			}
		}
	}

	// 관리자계정로그인
	private static void admLogin() {
		boolean num = true;
		while (num) {
			System.out.print("아이디 입력>>>");
			inputId = scan.nextLine();
			System.out.print("비밀번호 입력>>>");
			inputPw = scan.nextLine();
			if (mdao.selectInfo(inputId, inputPw) == true && inputPw.equals("ADMIN")) {
				System.out.println("로그인이 성공되었습니다.");
				mvo.setId(inputId);
				mvo.setPw(inputPw);
				num = false;
			} else {
				System.out.println("아이디 또는 비밀번호가 일치하지 않습니다. 다시 입력해주세요");
			}
		}
	}

	// 프로그램 수정기능 => 과정중간에 뒤로가기기능 및 항목이름 구현못함
	private static void selectProgram() {
		boolean num = true;
		while (true) {
			System.out.println("수정 할 종목이름을 입력하세요");
			System.out.println(">>>");
			programName = scan.nextLine();
			System.out.println("\n - 현재 운영중인 " + programName + " 과정 목록 -");
			if (pdao.selectInfo(programName) == true) {
				list = pdao.selectAll(programName);
				for (ProgramListVO vo : list) {
					System.out.println(vo.getProgramNum() + "\t" + vo.getProgramName() + "\t" + vo.getProgramID() + "\t"
							+ vo.getClassName() + "\t" + vo.getTime() + "\t  " + vo.getMaximum() + "\t " + vo.getPrice()
							+ "\t " + vo.getTeacher());
				}
				while (true) {
					System.out.println("변경 할 과정번호를 입력하세요.");
					System.out.println(">>>");
					programId = scan.nextLine();
					try {
						if (pdao.selectInfo2(programId) == true) {
							num = true;
							while (num) {
								System.out.println("변경 할 항목의 번호를 선택하세요.\n 1.과정명 2.시간 3.강사명 , 뒤로 돌아가려면 0을 입력하세요."); // 과정번호잘못입력시 기능구현
								System.out.print(">>>");
								choice = scan.nextLine();
								switch (choice) {
								case "1":
									System.out.println("변경할 과정명을 입력하세요. ");
									System.out.println(">>>");
									choice = scan.nextLine();
									pvo.setClassName(choice);
									pdao.updateClassName(choice, programId);
									System.out.println("과정명이 변경되었습니다.");
									break;
								case "2":
									System.out.println("변경할 과정 시간을 입력하세요.예)07:00-08:00 , 14:00-15:00 ");
									System.out.println(">>>");
									choice = scan.nextLine();
									pvo.setTime(choice);
									pdao.updateTime(choice, programId);
									System.out.println("과정 시간이 변경되었습니다.");
									break;
								case "3":
									System.out.println("변경할 강사명을 입력하세요. ");
									System.out.println(">>>");
									choice = scan.nextLine();
									pvo.setTeacher(choice);
									pdao.updateTeacher(choice, programId);
									System.out.println("강사가 변경되었습니다.");
									break;
								case "0":
									num = false;
									break;
								default:
									System.out.println("잘못 입력했습니다");
								}
							}
						} else {
							System.out.println("해당되는 항목이 없습니다. 과정번호를 숫자로 정확하게 입력하세요.");
							System.out.println(">>>");
						}
					} catch (SQLSyntaxErrorException e) {
					}
				}
			} else {
				System.out.println("해당되는 종목이 없습니다. 다시입력하세요.");
			}
		}
	}

	// 프로그램 추가기능 => // 각항목 기입하다가 메인메뉴로 돌아가는(취소)기능 구현x.....=>대신 마지막에 최종 최종추가의향
	// 물어보는것으로 대체함
	private static void insertProgram() {
		// 종목번호지정=>필수입력사항으로 미입력시 재입력기능구현
		System.out.println("종목번호를 지정하세요.(단 동일종목일 경우 동일번호사용)");
		System.out.println(">>>");
		while (true) {
			choice = scan.nextLine();
			if (choice.isEmpty()) {
				System.out.println("종목번호가 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				pvo.setProgramNum(Integer.parseInt(choice));
				break;
			}
		}
		// (프로그램 추가기능)종목명지정=>필수입력사항으로 미입력시 재입력기능구현
		System.out.println("종목명을 지정하세요.(단 동일종목일 경우 동일명사용)");
		System.out.println(">>>");
		while (true) {
			choice = scan.nextLine();
			if (choice.isEmpty()) {
				System.out.println("종목명이 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				pvo.setProgramName(choice);
				break;
			}
		}
		// (프로그램 추가기능)과정번호지정=>primary key로 중복불가 처리 및 필수입력사항으로 미입력시 재입력기능구현
		System.out.println("과정번호를 지정하세요.(단, 기존 과정번호와 중복불가)");
		System.out.println(">>>");
		while (true) {
			choice = scan.nextLine();
			if (choice.isEmpty()) {
				System.out.println("과정번호가 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				try {
					if (pdao.selectInfo2(choice) == true) {
						System.out.println("동일한 과정번호가 있습니다. 다시입력하세요.");
						System.out.print(">>>");
					} else {
						pvo.setProgramID(Integer.parseInt(choice));
						break;
					}
				} catch (Exception e) {
					System.out.println("잘못입력했습니다. 과정번호를 숫자로 정확하게 입력하세요.");
					System.out.println(">>>");
				}
			}
		}
		// (프로그램 추가기능)과정명지정=>필수입력사항으로 미입력시 재입력기능구현
		System.out.println("과정명을 지정하세요.");
		System.out.println(">>>");
		while (true) {
			choice = scan.nextLine();
			if (choice.isEmpty()) {
				System.out.println("과정명이 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				pvo.setClassName(choice);
				break;
			}
		}

		// (프로그램 추가기능)시간지정 =>필수입력사항으로 미입력시 재입력기능구현,시간형식지정기능구현못함
		System.out.println("시간을 지정하세요.예)07:00-08:00 , 14:00-15:00");
		System.out.println(">>>");
		while (true) {
			choice = scan.nextLine();
			if (choice.isEmpty()) {
				System.out.println("시간이 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				pvo.setTime(choice);
				break;
			}
		}
		// (프로그램 추가기능)최대인원지정=>필수입력사항으로 미입력시 재입력기능구현
		System.out.println("수강최대인원을 지정하세요.");
		System.out.println(">>>");
		while (true) {
			choice = scan.nextLine();
			if (!choice.isEmpty()) {
				if (Integer.parseInt(choice) > 10 || Integer.parseInt(choice) < 5) {
					System.out.println("프로그램은 최소 5명부터 오픈가능하며 최대 10명까지 가능합니다. 다시 지정하세요.");
					System.out.println(">>>");
				} else {
					pvo.setMaximum(Integer.parseInt(choice));
					break;
				}
			} else {
				System.out.println("인원이 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			}
		}
		// (프로그램 추가기능)가격지정=>필수입력사항으로 미입력시 재입력기능구현
		System.out.println("가격을 지정하세요.");
		System.out.println(">>>");
		while (true) {
			choice = scan.nextLine();
			if (choice.isEmpty()) {
				System.out.println("가격이 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				pvo.setPrice(Integer.parseInt(choice));
				break;
			}
		}
		// (프로그램 추가기능)강사명지정=>필수입력사항으로 미입력시 재입력기능구현
		System.out.println("강사명을 입력하세요.");
		System.out.println(">>>");
		while (true) {
			choice = scan.nextLine();
			if (choice.isEmpty()) {
				System.out.println("강사명이 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				pvo.setTeacher(choice);
				break;
			}
		}
		// (프로그램 추가기능)마지막 프로그램추가의향 기능구현
		System.out.println("프로그램을 추가 하시겠습니까? 1.네 2.아니오");
		System.out.print(">>>");
		while (true) {
			choice = scan.nextLine();
			if (choice.equals("1")) {
				pdao.insert(pvo);
				System.out.println("프로그램 추가가 완료되었습니다.");
				break;
			} else if (choice.equals("2")) {
				System.out.println("프로그램 추가가 취소되었습니다.");
				Main_Operation.operation();
				break;
			} else {
				System.out.println("잘못입력하였습니다. 다시입력하세요.");
				System.out.print(">>>");
			}
		}
	}

	// 프로그램삭제 기능
	private static void deleteProgram() {
		while (true) {
			System.out.println("삭제를 원하는 종목의 이름을 입력하세요.");
			System.out.println(">>>");
			programName = scan.nextLine();
			System.out.println("\n - 현재 운영중인 " + programName + " 과정 목록 -");
			if (pdao.selectInfo(programName) == true) {
				list = pdao.selectAll(programName);
				for (ProgramListVO vo : list) {
					System.out.println(vo.getProgramNum() + "\t" + vo.getProgramName() + "\t" + vo.getProgramID() + "\t"
							+ vo.getClassName() + "\t" + vo.getTime() + "\t  " + vo.getMaximum() + "\t " + vo.getPrice()
							+ "\t " + vo.getTeacher());
				}
				System.out.println("삭제를 원하는 과정의 번호(programId)를 입력하세요");
				System.out.println(">>>");
				programId = scan.nextLine();
				try {
					if (pdao.selectInfo2(programId) == true) {
						pvo.setProgramID(Integer.parseInt(programId));
						// 마지막 한번더 확인 기능구현
						System.out.println("정말로 삭제 하시겠습니까? 1.네 2.아니오");
						System.out.print(">>>");
						while (true) {
							choice = scan.nextLine();
							if (choice.equals("1")) {
								pdao.delete(Integer.parseInt(programId));
								System.out.println("선택한 과정이 삭제되었습니다.");
								break;
							} else if (choice.equals("2")) {
								System.out.println("프로그램 삭제가 취소되었습니다.");
								
								break;
							} else {
								System.out.println("잘못입력하였습니다. 다시입력하세요.");
								System.out.print(">>>");
							}
						}
					} else {
						System.out.println("해당하는 수강과정이 없습니다.");
					}
				} catch (Exception e) {
					System.out.println("잘못입력했습니다. 과정번호를 숫자로 정확하게 입력하세요.");

				}
			} else {
				System.out.println("해당하는 수강과정이 없습니다.");
			}
			break;
		}
	}
}