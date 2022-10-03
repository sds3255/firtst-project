package com.study.method;

import java.util.Scanner;

public class Main_Operation {
	private static Scanner scan = new Scanner(System.in);	
	private static String choice;
	
	public static void main(String[] args) {
		Main_Operation.operation();
	}
	
	public static void operation() {
		while (true) {
			System.out.println("원하는 항목의 번호를 선택하세요.\n1.회원가입 2.수강신청 3.회원정보조회 4.프로그램조회 5.관리페이지 6.종료");
			System.out.print(">>>");
			choice = scan.nextLine();
			if (choice.equals("1")) {// 회원가입페이지 Membership =>완료
				Membership.member();
			} else if (choice.equals("2")) {// 수강신청페이지 Application  =>완료
				Application.lecture();
				break;			
			} else if (choice.equals("3")) {// 회원정보조회페이지 InfoSearch =>완료
				InfoSearch.search();
				break;				
			} else if (choice.equals("4")) {// 프로그램정보조회페이지 ProgramSearch =>완료
				ProgramSearch.search();
				break;				
			} else if (choice.equals("5")) {// 관리자페이지 AdmPage
				AdmPage.programUpdate();
				break;				
			} else if (choice.equals("6")) {// 종료
				System.out.println("페이지를 닫습니다.");
				break;				
			} else {
				System.out.println("번호를 잘못 입력했습니다. 다시 입력하세요");
			}
		}		
	}			
}