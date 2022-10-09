package com.study.method;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Scanner;

import com.study.A_dao.ApplicationDAO;
import com.study.A_dao.MasterDAO;
import com.study.A_dao.ProgramListDAO;
import com.study.B_vo.MasterVO;
import com.study.B_vo.ProgramListVO;

public class AdmPage {// 관리자페이지 항목 "5" 선택시
	static Scanner scan = new Scanner(System.in);
	static ProgramListVO pvo = new ProgramListVO();
	static ProgramListDAO pdao = new ProgramListDAO();
	static ApplicationDAO adao = new ApplicationDAO();
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

	// 프로그램 수정기능 => 과정중간에 뒤로가기기능 및 항목이름 구현함
	private static void selectProgram() {
		boolean num = true;
		while (num) {
			System.out.println("수정 할 종목이름을 입력하세요. 메뉴로 돌아가기는 #을 입력하세요.");
			List<String> pro = adao.selectProg();
			for (String p : pro) {
				System.out.print("\t" + p + "\t//");
			}
			System.out.println();
			System.out.print(">>>");
			programName = scan.nextLine();
			if (!programName.equalsIgnoreCase("#")) {
				System.out.println("\n - 현재 운영중인 " + programName + " 과정 목록 -");
				if (pdao.selectInfo(programName) == true) {
					list = pdao.selectAll(programName);
					for (ProgramListVO vo : list) {
						System.out.println("[종목번호]" + vo.getProgramNum() + "\t" + "[종목명]" + vo.getProgramName() + "\t"
								+ "[과정번호]" + vo.getProgramID() + "\t" + "[과정명]" + vo.getClassName() + "\t" + "[시간]"
								+ vo.getTime() + "\t  " + "[최대수강인원]" + vo.getMaximum() + "\t " + "[가격]" + vo.getPrice()
								+ "\t " + "[강사명]" + vo.getTeacher());
					}
					System.out.println();
				} else {
					System.out.println("해당되는 종목이 없습니다. 다시입력하세요.");
				}
				System.out.println("변경 할 과정번호를 입력하세요. 메뉴로 돌아가기는 #을 입력하세요.");
				System.out.println(">>>");
				programId = scan.nextLine();
				if (!programId.equalsIgnoreCase("#")) {
					while (num) {
						try {
							if (pdao.selectInfo2(programId) == true) {
								num = true;
								while (num) {
									System.out.println("변경 할 항목의 번호를 선택하세요.\n 1.과정명 2.시간 3.강사명  메뉴로 돌아가기는 #을 입력하세요.");
									System.out.print(">>>");
									choice = scan.nextLine();
									if (!choice.equalsIgnoreCase("#")) {
										num = false;
										switch (choice) {
										case "1":
											System.out.println("변경할 과정명을 입력하세요. 메뉴로 돌아가기는 #을 입력하세요.");
											System.out.println(">>>");
											choice = scan.nextLine();
											if (!choice.equalsIgnoreCase("#")) {
												pvo.setClassName(choice);
												pdao.updateClassName(choice, programId);
												System.out.println("과정명이 변경되었습니다.");
											}
											num = false;
											break;
										case "2":
											System.out.println(
													"변경할 과정 시간을 입력하세요.예)07:00-08:00 , 14:00-15:00 \n메뉴로 돌아가기는 #을 입력하세요.");
											System.out.println(">>>");
											choice = scan.nextLine();
											if (!choice.equalsIgnoreCase("#")) {
												pvo.setTime(choice);
												pdao.updateTime(choice, programId);
												System.out.println("과정 시간이 변경되었습니다.");
											}
											num = false;
											break;
										case "3":
											System.out.println("변경할 강사명을 입력하세요. 메뉴로 돌아가기는 #을 입력하세요. ");
											System.out.println(">>>");
											choice = scan.nextLine();
											if (!choice.equalsIgnoreCase("#")) {
												pvo.setTeacher(choice);
												pdao.updateTeacher(choice, programId);
												System.out.println("강사가 변경되었습니다.");
											}
											num = false;
											break;
										default:
											System.out.println("잘못 입력했습니다");
										}
									}
									num = false;
								}
							} else {
								System.out.println("해당되는 항목이 없습니다. 과정번호를 숫자로 정확하게 입력하세요.");
								System.out.println(">>>");
								programId = scan.nextLine();
							}
						} catch (SQLSyntaxErrorException e) {
						}
					}
				}
				num = false;
			}
			num = false;
		}
	}

	// 프로그램 추가기능 => // 각항목 기입하다가 메인메뉴로 돌아가는(취소)기능 구현함.
	private static void insertProgram() {
		// 종목번호지정=>필수입력사항으로 미입력시 재입력기능구현
		System.out.println("새로운 종목번호를 지정하세요.(단 동일종목일 경우 동일번호사용) 메뉴로 돌아가기는 #을 입력하세요. ");
		List<ProgramListVO> pro = pdao.selectProg();
		for (ProgramListVO p : pro) {
			System.out.print("[" + p.getProgramNum() + ":" + p.getProgramName() + "]\t");
		}
		System.out.println();
		System.out.print(">>>");
		programId = scan.nextLine();
		if (!programId.equalsIgnoreCase("#")) {
			while (true) {
				if (programId.isEmpty()) {
					System.out.println("종목번호가 입력되지 않았습니다. 다시입력하세요.");
					System.out.print(">>>");
					programId = scan.nextLine();
				} else {
					pvo.setProgramNum(Integer.parseInt(programId));
					break;
				}
			}

			// (프로그램 추가기능)종목명지정=>필수입력사항으로 미입력시 재입력기능구현
			int result = pdao.collectPro(Integer.parseInt(programId));
			String results = pdao.collectPro2(Integer.parseInt(programId));
			System.out.println("새로운 종목명을 지정하세요.(단 동일종목일 경우 동일명사용) 메뉴로 돌아가기는 #을 입력하세요.");
			System.out.print(">>>");
			choice = scan.nextLine();
			if (!choice.equalsIgnoreCase("#")) {
				
				while (true) {
					if (choice.isEmpty()) {
						System.out.println("종목명이 입력되지 않았습니다. 다시입력하세요.");
						System.out.print(">>>");
						choice = scan.nextLine();					
					} else {
						if(result>0) {//종목번호 이미 존재
							if(results.equals(choice)) {//choice가 DB의 종목명과 같으면 set
								pvo.setProgramName(choice);
								break;
							}else {//같지않으면 알림생성
								System.out.println("해당종목번호에는 이미 지정된 종목명이 있습니다. 다시 입력하세요.");
								System.out.print(">>>");
								choice = scan.nextLine();
							}
						} else {//종목번호 없음 새로 생성해야함						
							int resultss = pdao.collectPro3(choice);
							if(resultss>0) {//choice가 db의 종목명과 같으면 알림생성
								System.out.println("해당종목에는 이미 지정된 종목번호가 있습니다. 다시 입력하세요.");
								System.out.print(">>>");
								choice = scan.nextLine();								
							}else {
								pvo.setProgramName(choice);
								break;
							}				
						}
					}
				}
				// (프로그램 추가기능)과정번호지정=>primary key로 중복불가 처리 및 필수입력사항으로 미입력시 재입력기능구현
				System.out.println("새로운 과정번호를 지정하세요.(단, 기존 과정번호와 중복불가) 메뉴로 돌아가기는 #을 입력하세요.");
				System.out.print(">>>");
				choice = scan.nextLine();
				if (!choice.equalsIgnoreCase("#")) {
					while (true) {
						if (choice.isEmpty()) {
							System.out.println("과정번호가 입력되지 않았습니다. 다시입력하세요.");
							System.out.print(">>>");
							choice = scan.nextLine();
						} else {
							try {
								if (pdao.selectInfo2(choice) == true) {
									System.out.println("동일한 과정번호가 있습니다. 다시입력하세요.");
									System.out.print(">>>");
									choice = scan.nextLine();
								} else {
									pvo.setProgramID(Integer.parseInt(choice));
									break;
								}
							} catch (Exception e) {
								System.out.println("잘못입력했습니다. 과정번호를 숫자로 정확하게 입력하세요.");
								System.out.println(">>>");
								choice = scan.nextLine();
							}
						}
					}
					// (프로그램 추가기능)과정명지정=>필수입력사항으로 미입력시 재입력기능구현
					System.out.println("새로운 과정명을 지정하세요. 메뉴로 돌아가기는 #을 입력하세요.");
					System.out.print(">>>");
					choice = scan.nextLine();
					if (!choice.equalsIgnoreCase("#")) {
						while (true) {
							if (choice.isEmpty()) {
								System.out.println("과정명이 입력되지 않았습니다. 다시입력하세요.");
								System.out.print(">>>");
								choice = scan.nextLine();
							} else {
								pvo.setClassName(choice);
								break;
							}
						}

						// (프로그램 추가기능)시간지정 =>필수입력사항으로 미입력시 재입력기능구현,시간형식지정기능구현못함
						System.out.println("새로운 시간을 지정하세요.예)07:00-08:00 , 14:00-15:00 메뉴로 돌아가기는 #을 입력하세요.");
						System.out.print(">>>");
						choice = scan.nextLine();
						if (!choice.equalsIgnoreCase("#")) {
							while (true) {
								if (choice.isEmpty()) {
									System.out.println("시간이 입력되지 않았습니다. 다시입력하세요.");
									System.out.print(">>>");
									choice = scan.nextLine();
								} else {
									pvo.setTime(choice);
									break;
								}
							}
							// (프로그램 추가기능)최대인원지정=>필수입력사항으로 미입력시 재입력기능구현
							System.out.println("새로운 수강최대인원을 지정하세요. 메뉴로 돌아가기는 #을 입력하세요.");
							System.out.print(">>>");
							choice = scan.nextLine();
							if (!choice.equalsIgnoreCase("#")) {
								while (true) {
									if (!choice.isEmpty()) {
										if (Integer.parseInt(choice) > 10 || Integer.parseInt(choice) < 5) {
											System.out.println("프로그램은 최소 5명부터 오픈가능하며 최대 10명까지 가능합니다. 다시 지정하세요.");
											System.out.println(">>>");
											choice = scan.nextLine();
										} else {
											pvo.setMaximum(Integer.parseInt(choice));
											break;
										}
									} else {
										System.out.println("인원이 입력되지 않았습니다. 다시입력하세요.");
										System.out.print(">>>");
										choice = scan.nextLine();
									}
								}
								// (프로그램 추가기능)가격지정=>필수입력사항으로 미입력시 재입력기능구현
								System.out.println("새로운 가격을 지정하세요. 메뉴로 돌아가기는 #을 입력하세요.");
								System.out.print(">>>");
								choice = scan.nextLine();
								if (!choice.equalsIgnoreCase("#")) {
									while (true) {
										if (choice.isEmpty()) {
											System.out.println("가격이 입력되지 않았습니다. 다시입력하세요.");
											System.out.print(">>>");
											choice = scan.nextLine();
										} else {
											pvo.setPrice(Integer.parseInt(choice));
											break;
										}
									}
									// (프로그램 추가기능)강사명지정=>필수입력사항으로 미입력시 재입력기능구현
									System.out.println("새로운 강사명을 입력하세요. 메뉴로 돌아가기는 #을 입력하세요.");
									System.out.print(">>>");
									choice = scan.nextLine();
									if (!choice.equalsIgnoreCase("#")) {
										while (true) {
											if (choice.isEmpty()) {
												System.out.println("강사명이 입력되지 않았습니다. 다시입력하세요.");
												System.out.print(">>>");
												choice = scan.nextLine();
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
								}
							}
						}
					}
				}
			}
		}
	}

	// 프로그램삭제 기능 => 과정중간에 뒤로가기기능 및 항목이름 구현함
	private static void deleteProgram() {
		boolean num = true;
		System.out.println("삭제를 원하는 종목의 이름을 입력하세요. 메뉴로 돌아가려면 #을 입력하세요.");
		List<String> pro = adao.selectProg();
		for (String p : pro) {
			System.out.print("\t" + p + "\t//");
		}
		System.out.println();
		System.out.print(">>>");
		programName = scan.nextLine();
		if (!programName.equalsIgnoreCase("#")) {
			while (num) {
				if (pdao.selectInfo(programName) == true) {
					System.out.println("\n - 현재 운영중인 " + programName + " 과정 목록 -");
					list = pdao.selectAll(programName);
					for (ProgramListVO vo : list) {
						System.out.println("[종목번호]" + vo.getProgramNum() + "\t" + "[종목명]" + vo.getProgramName() + "\t"
								+ "[과정번호]" + vo.getProgramID() + "\t" + "[과정명]" + vo.getClassName() + "\t" + "[시간]"
								+ vo.getTime() + "\t  " + "[최대수강인원]" + vo.getMaximum() + "\t " + "[가격]" + vo.getPrice()
								+ "\t " + "[강사명]" + vo.getTeacher());
					}
					System.out.println("삭제를 원하는 과정의 번호(programId)를 입력하세요. 메뉴로 돌아가려면 #을 입력하세요.");
					System.out.print(">>>");
					programId = scan.nextLine();
					if (!programId.equalsIgnoreCase("#")) {
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
								System.out.println("해당하는 과정이 없습니다.");
							}
						} catch (Exception e) {
							System.out.println("잘못입력했습니다. 과정번호를 숫자로 정확하게 입력하세요.");
						}
					}
					num = false;
				} else {
					System.out.println("해당하는 종목이 없습니다.");
				}
				num = false;
			}
		}
	}
}