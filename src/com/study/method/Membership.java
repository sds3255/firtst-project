package com.study.method;

import java.util.Scanner;

import com.study.A_dao.MemberDAO;
import com.study.B_vo.MemberVO;

public class Membership {// 회원가입 항목 "1" 선택시
	static Scanner scan = new Scanner(System.in);
	static MemberVO mvo = new MemberVO();
	static MemberDAO mdao = new MemberDAO();
	static String inputId;
	static String inputPw;
	static String info;

	public static void member() {// 각항목 기입하다가 메인메뉴로 돌아가는(취소)기능 구현x..... =>대신 마지막에 최종 회원가입의향물어보는것으로 대체함
		// 아이디 설정 =>길이는 5-12자리, 중복아이디 확인, 미입력시 재입력하도록 기능구현
		System.out.println("사용할 아이디를 입력하세요. (5~12자리) ");
		System.out.print(">>>");
		while (true) {
			inputId = scan.nextLine();
			if (inputId.length() < 13 && inputId.length() > 4 && inputId.isEmpty() == false) {
				if (mdao.selectInfo(inputId) == true) {
					System.out.println("이미 존재하는 아이디입니다. 다시입력하세요.");
					System.out.print(">>>");
				} else {
					mvo.setId(inputId);
					break;
				}
			} else {
				System.out.println("아이디가 입력되지 않았거나 범위를 벗어났습니다. 5~12자리로 다시입력하세요.");
				System.out.print(">>>");
			}
		}
		// 비밀번호 설정 =>길이는 5-12자리, 미입력시 재입력하도록 기능구현
		System.out.println("사용할 비밀번호를 입력하세요. (5~12자리)");
		System.out.print(">>>");
		while (true) {
			inputPw = scan.nextLine();
			if (inputPw.length() < 13 && inputPw.length() > 4) {
				mvo.setPw(inputPw);
				break;
			} else if (inputPw.isEmpty()) {
				System.out.println("비밀번호가 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				System.out.println("범위를 벗어났습니다. 5~12자리로 다시입력하세요.");
				System.out.print(">>>");
			}
		}

		// 이름작성항목 =>필수항목으로 작성하지 않을시 재입력 문구기능구현
		System.out.println("본인 이름을 입력하세요.");
		System.out.print(">>>");
		while (true) {
			info = scan.nextLine();
			if (info.isEmpty()) {
				System.out.println("이름이 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				mvo.setName(info);
				break;
			}
		}
		// 생년월일작성항목 =>필수항목으로 작성하지 않을시 재입력 문구기능 및 주민번호 앞자리 입력 및 동명이인 구별기능 구현
		System.out.println("본인의 생년월일 6자리(주민등록번호 앞6자리)를 입력하세요. \n예시) 2012년 01월 09일 =>120109로 입력");
		System.out.print(">>> ");
		while (true) {
			info = scan.nextLine();
			if (info.length() == 6) {
				if (mdao.selectSameName(mvo.getName(), info) == true) {
					System.out.println("이미 가입된 회원입니다.");
					Main_Operation.operation();
				} else {
					mvo.setBirthdate((info));
					break;
				}
			} else if (info.isEmpty()) {
				System.out.println("생년월일이 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				System.out.println("생년월일 범위를 벗어납니다. 다시입력하세요.");
				System.out.print(">>>");
			}
		}
		// 휴대폰번호작성항목 =>필수항목으로 작성하지 않을시 재입력 문구기능 구현 //구분자구현 못함...
		System.out.println("본인 휴대폰번호를 입력하세요. (단,구분문자 포함하여 입력) \n예시)010-0000-0000로 입력");
		System.out.print(">>>");
		while (true) {
			info = scan.nextLine();
			if (info.isEmpty()) {
				System.out.println("휴대폰번호가 입력되지 않았습니다. 다시입력하세요.");
				System.out.print(">>>");
			} else {
				mvo.setPhoneNum(info);
				break;
			}
		}
		// 이메일 검증, 이메일형식 기능구현못함
		System.out.println("본인 이메일을 입력해주세요. (선택사항)");
		System.out.print(">>>");
		info = scan.nextLine();
		mvo.setEmail(info);
		
		// 주소형식 기능구현못함
		System.out.println("본인 주소를 입력해주세요. (선택사항)");
		System.out.print(">>>");
		info = scan.nextLine();
		mvo.setAddress(info);
		
		//마지막 회원가입의향 기능구현
		System.out.println("회원가입을 하시겠습니까? 1.네 2.아니오");
		System.out.print(">>>");
		while (true) {
			info = scan.nextLine();
			if (info.equals("1")) {
				mdao.insert(mvo);
				System.out.println("회원가입이 완료되었습니다.");
				break;
			} else if (info.equals("2")) {
				System.out.println("회원가입이 취소되었습니다.");
				Main_Operation.operation();
				break;
			} else {
				System.out.println("잘못입력하였습니다. 다시입력하세요.");
				System.out.print(">>>");
			}
		}
	}
}
