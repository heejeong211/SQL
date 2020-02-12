package Board01;
//데이터베이스 통신을 이용한 게시판 예제 01
//가장 원시적인 형태의 게시판 예제

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Board {
	int id;
	String title;
	String content;
	Date writtenDate;
	Date updatedDate;
	
	public boolean equals(Object o) { //equals 모든 객체에 사용. 가능 이 o가 보드의 객체인지 확인. b로 객체 바꿔준 뒤 아이디값만 확인.
		if (o instanceof Board) {
			Board b = (Board)o;
			if (b.id == this.id) {
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) throws SQLException{
		Connection conn = null;
		Scanner scan = new Scanner(System.in);
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/example?serverTimezone=UTC",
					 "root", "1111");
			 while(true) {
				 System.out.println("---게시판---");
				 System.out.println("1. 글 목록보기 2. 글 작성하기 3. 종료");
				 System.out.print("> ");
				 int choice = scan.nextInt();
				 if(choice == 3) {
					 System.out.println("사용해주셔서 감사합니다.");
					 break;
				 } else if (choice == 1) {
					 //글 목록 보여주기
					 //글 목록 보여주기는 번호 제목 작성날짜 순으로 출력이 된다.
					 //그리고 사용자는 번호를 입력해서 원하는 글로 이동한다.
					 ArrayList<Board> list = new ArrayList<>();//SELECT 결과값을 넣어주기 위해 Array를 씀.
					 String query = "SELECT * FROM board";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 ResultSet rs= pstmt.executeQuery();//쿼리의 결과를 ResultSet이라고 함.
					 while(rs.next()) {
						 Board b = new Board();
						 b.id = rs.getInt("id");
						 b.title = rs.getString("title");
						 b.content = rs.getString("content");
						 b.writtenDate = rs.getDate("writtenDate");
						 b.updatedDate = rs.getDate("updatedDate");
						 list.add(b);
					 }
					 System.out.println("==================");
					 System.out.println("글번호\t제목\t작성날짜");
					 System.out.println("==================");
					 if (list.size() == 0) {
						 System.out.println("아무런 글이 없습니다.");
					 } else {
						 Collections.reverse(list);//list 역순으로 출력하는 것.
						 for(Board b : list) {//list에 있는 하나하나를 Board b 라고 임시로 이름을 선언함. 값을 수정 삭제 안됨.
							 System.out.println(b.id+"\t"+b.title+"\t"+b.writtenDate);
						 }
						 System.out.println("글번호 선택(0은 종료)");
						 System.out.print(">");
						 int boardNum = scan.nextInt();
						 if(boardNum != 0) {
							for(Board b : list) {
								if (b.id == boardNum) {
									System.out.println("===제목===");
									System.out.println(b.title);
									System.out.print("작성일");
									System.out.println(b.writtenDate);
									System.out.println("===내용===");
									System.out.println(b.content);
									System.out.print("1. 수정 2. 삭제 3. 메인으로");
									System.out.print(">");
									choice = scan.nextInt();
									if(choice == 2) {
										query = "DELETE FROM board WHERE id = ?";//물음표에 원하는 값을 넣을 수 있음.
										pstmt = conn.prepareStatement(query);
										pstmt.setInt(1, boardNum); //1은 위치, 첫번째 자리에 boardNum을 넣어줘
										pstmt.execute();
									} else if (choice == 1) {
										System.out.print("제목: ");
										scan.nextLine();//스캐너의 경우에는 버퍼 메모리를 사용함. 엔터키는 문자. 버퍼 메모리에 숫자만 가져옴.
										String title = scan.nextLine();
										System.out.print("내용: ");
										String content = scan.nextLine();
										query = "UPDATE board SET title = ?,content = ?, updatedDate = NOW() WHERE id = ?";
										pstmt = conn.prepareStatement(query);
										pstmt.setString(1, title);
										pstmt.setString(2, content);
										pstmt.setInt(3, boardNum);
										pstmt.execute();
									}
								}
							}
						 }
					 }
				 } else if (choice == 2) {
					 //글 작성하기
					 //제목:
					 //내용:
					 //요렇게 떠서 사용자가 입력할 수 있게 한다.
					 scan.nextLine();
					 System.out.print("제목: ");
					 String title = scan.nextLine();
					 System.out.print("내용: ");
					 String content = scan.nextLine();
					 String query = "INSERT INTO board(title, content, writtenDate, updatedDate)" 
					 + "VALUES(?, ?, NOW(), NOW())";
					 PreparedStatement pstmt = conn.prepareStatement(query);
					 pstmt.setString(1, title);
					 pstmt.setString(2, content);
					 pstmt.execute();
				 }
			 }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
			scan.close();
		}
	}
}

//여기서 배열쓰면 안됨. 글자가 몇개나 들어갈 줄 알고...
