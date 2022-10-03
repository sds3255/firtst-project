package com.study.method;

import java.util.Scanner;

import com.study.A_dao.MemberDAO;
import com.study.B_vo.MemberVO;

public class InfoSearch {// 회원정보조회 항목 "3" 선택시

	static Scanner scan = new Scanner(System.in);
	static MemberVO mvo = new MemberVO();
	static MemberDAO mdao = new MemberDAO();
	static String choice;
	static String inputId;
	static String inputPw;
	static boolean num = true;

	public static void search() {
		login();
		while (num) {
			System.out.println("원하는 항목의 번호를 선택하세요.\n 1.회원정보조회 2.회원정보수정 3.회원탈퇴 4.뒤로가기");
			System.out.println(">>>");
			choice = scan.nextLine();
			if (choice.equals("1")) {// 회원정보조회
				MemberVO info = mdao.select(mvo);
				System.out.println(info);

			} else if (choice.equals("2")) {// 회원정보수정
				System.out.println("변경을 원하는 번호를 선택하세요.\n 1.비밀번호 2.휴대폰번호 3.이메일 4.주소 5.취소");
				System.out.println(">>>");
				choice = scan.nextLine();
				selectNum();

			} else if (choice.equals("3")) {// 회원탈퇴
				System.out.println("정말로 탈퇴하시겠습니까? 1.네 또는 2.아니요");
				System.out.println(">>>");
				while (true) {
					choice = scan.nextLine();
					if (choice.equals("1")) {
						mdao.delete(mvo);
						System.out.println("회원탈퇴가 정상처리되었습니다.");
						Main_Operation.operation();
						num = false;
						break;
					} else if (choice.equals("2")) {
						break;
					} else {
						System.out.println("잘못 입력했습니다. 다시 입력하세요");
						System.out.println(">>>");
					}
				}
			} else if (choice.equals("4")) {// 뒤로가기
				Main_Operation.operation();
				num = false;
			} else {
				System.out.println("잘못 입력했습니다. 다시 입력하세요");
				System.out.println(">>>");
			}
		}
	}

	private static void selectNum() {
		switch (choice) {
		case "1": // 비밀번호 설정 =>길이는 5-12자리, 미입력시 재입력하도록 기능구현
			System.out.println("변경할 비밀번호를 입력하세요. (5~12자리)");
			System.out.print(">>>");
			while (true) {
				choice = scan.nextLine();
				if (choice.length() < 13 && choice.length() > 4) {
					mvo.setPw(choice);
					mdao.updatePassword(choice, inputId);
					System.out.println("비밀번호 변경이 완료되었습니다.");
					break;
				} else {
					System.out.println("5~12자리로 다시입력하세요.\n>>>");
				}
			}
			break;
		case "2":// 휴대폰번호작성항목 =>필수항목으로 작성하지 않을시 재입력 문구기능 구현 //구분자구현 못함...
			System.out.println("변경할 휴대폰번호를 입력하세요.(단,구분문자 포함하여 입력) \\n예시)010-0000-0000로 입력 ");
			System.out.print(">>>");
			while (true) {
				choice = scan.nextLine();
				if (choice.isEmpty()) {
					System.out.println("휴대폰번호가 입력되지 않았습니다. 다시입력하세요.");
					System.out.print(">>>");
				} else {
					mvo.setPhoneNum(choice);
					mdao.updatePhoneNum(choice, inputId);
					break;
				}
			}
			System.out.println("휴대폰번호 변경이 완료되었습니다.");
			break;
		case "3":// 이메일 검증, 이메일형식 기능구현못함
			System.out.println("변경할 이메일을 입력하세요. ");
			System.out.println(">>>");
			choice = scan.nextLine();
			mvo.setEmail(choice);
			mdao.updateEmail(choice, inputId);
			System.out.println("이메일 변경이 완료되었습니다.");
			break;
		case "4":// 주소형식 기능구현못함
			System.out.println("변경할 주소를 입력하세요. ");
			System.out.println(">>>");
			choice = scan.nextLine();
			mvo.setAddress(choice);
			mdao.updateAddress(choice, inputId);
			System.out.println("주소 변경이 완료되었습니다.");
			break;
		default:
			InfoSearch.search();
			break;
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
					System.out.println("해당되는 회원이 없습니다. 회원가입을 하세요.");
					Main_Operation.operation();
					break;
				} else {
					System.out.println("아이디 또는 비밀번호가 일치하지 않습니다. 다시 입력하세요");
				}
			}
		}
	}
}