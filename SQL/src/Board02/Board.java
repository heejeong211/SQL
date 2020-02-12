package Board02;
//게시판 예제 02
//CRUD 명령어를 최소한 메소드로 분리시키자!!

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
	int id;//필드
	String title;
	String content;
	Date writtenDate;
	Date updatedDate;
	final static String URL = "jdbc:mysql://localhost:3306/example?serverTimezone=UTC";//상수로 만들지 않으면 모든 메소드에 얘네를 만들어 줘야함.
	final static String USERNAME = "root";//변할 수 있는 수면  final은 빼고 써야 함.
	final static String PASSWORD = "1111";	
	//SELECT * FROM board;
	public static ArrayList<Board> selectAll() {//메소드. 리턴 타입은 어레이리스트
		ArrayList<Board> list = new ArrayList<>();//어레이 리스트 객체를 하나 만듦. 사이즈가 0. <>꺽쇠가 없으면 아무 객체나 들어올 수 있음. list의 템플릿.
		try {
		     Class.forName("com.mysql.cj.jdbc.Driver");//컴퓨터랑 통신할 설정(DB랑 통신하기 위한 기본 설정)
		     Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);//데이터베이스랑 연결할 때 데이터베이스주소, 접속할 아이디, 접속할 아이디의 비밀번호 하면 커넥션 객체가 오류가 안남.
		     String query = "SELECT * FROM board";
		     PreparedStatement pstmt = conn.prepareStatement(query);//커넥션 객체의 경우에는 우리가 쿼리를 준비시켜줄 수 있음.
		     ResultSet rs = pstmt.executeQuery();//결과값이 존재하는 쿼리임. 결과가 리턴되서 rs에 들어가 있음.
		     while (rs.next()) {//종료 조건 rs.next()임. 종료 됬는지 안됬는지는 ture, flase로 알려줌. 다음 row로 커서를 옮겨줌.
		    	 Board b = new Board(); //새로 들어가는 애들은 보드로 초기화시켜서 넣어주자.
		    	 b.id = rs.getInt("id");//세팅 하기 전 값 0. 몇 번 째로 들어갈 지 몰라서  index번호를 넣거나 아님 컬럼의 이름을 집어넣어야함.
		    	 b.title = rs.getString("title");//null
		    	 b.content = rs.getString("content");//null. 데이터베이스에서는  TEXT타입이었는데, String으로 받아줘도 됨. 같은 문자라.
		    	 b.writtenDate = rs.getDate("writtenDate");//null
		    	 b.updatedDate = rs.getDate("updatedDate");//null
		    	 list.add(b);//list에 추가해줘야 함.
		     }
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;//while문 끝나면 list에 보드에 모든 내용을 담게 되는 것임.
	}
	
	public static Board select(int id) {//int id 파리미터로 연결
		Board b = new Board();//초기화값이 0임. 클래스 타입은 null임. 리턴을 할 객체 b를 만듦. 하나만 리턴 해도 됨.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String sql = "SELECT * FROM board WHERE id = ?";//id값을 줘서 한개만 뽑아 오겠다.
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);//setInt 파라미터로 넘어온 id값을 미리 세팅해 줄 수 있음. 저기 id에 모든 int가 올 수 있음. PreparedStatement이거 때문에
			ResultSet rs = pstmt.executeQuery();//empty든 한개짜리 결과값이든.
			while(rs.next()) {//while문으로 안써도 됨. 하지만 커서를 강제로 옮겨버림. 
				b.id = rs.getInt("id");
				b.title = rs.getString("title");
				b.content = rs.getString("content");
				b.writtenDate = rs.getDate("writtenDate");
		    	b.updatedDate = rs.getDate("updatedDate");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;//b에는 id, title.....들이 해당값으로 초기화 되서 들어가 있음. 얘네들이 없으면 0,null로 초기화됨.
	}
	
	public static void insert(Board b) {//insert의 파라미터는 Board b임.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String sql = "INSERT INTO board(title, content, writtenDate, updatedDate)"
					+ "VALUES(?, ?, NOW(), NOW())";//?는 우리가 필요한 만큼 넣어줄 수 있음. 단, 물음표 개수 맞춰줘야 함.
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.title);
			pstmt.setString(2, b.content);
			pstmt.execute();//insert는 결과값이 없음.
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(int id) {//id값을 통해서 삭제함.
		try {
			Class.forName("com.mysqp.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String query = "DELETE FROM board WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.execute();//execute는 항상 리절트셋이 리턴됨. 결과값이 리턴됨. 입력, 수정, 삭제는 결과값(int,String...)은 없음. 그래서 안에 query 안씀.
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void update(Board b) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String query = "UPDATE board SET title = ?, content = ?, updatedDate = NOW() WHERE ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, b.title);
			pstmt.setString(2, b.content);
			pstmt.setInt(3, b.id);//수정할 id값을 줘야 함.
			pstmt.execute();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while(true) { //무한루프 만들기.
			System.out.println("===게시판===");//메뉴 ui를 만든 것임.
			System.out.println("1. 목록 2. 입력 3. 종료");
			System.out.print("> ");
			int choice = scan.nextInt();
			if (choice == 3) { //사용자가 종료누름.
				System.out.println("사용해주셔서 감사합니다.");
				break; //반복문 종료
			} else if (choice == 1) {
				ArrayList<Board> list = selectAll();//static 에서 selectAll을 했기 때문에 다 static을 붙여줘야 함. new어레이리스트 할 필요가 없음.
				Collections.reverse(list);//역순만들기.
				System.out.println("=====목록=====");
				if (list.size() == 0) {//보드테이블에 아무런 글도 없는 것.
					System.out.println("글이 없습니다.");
				} else {//보드테이블에 글이 있음.
					System.out.println("번호\t제목\t작성일\t");
					for(Board b : list) {//리스트의 템플릿은 보드. 보드에 들어있는 애들을 임시로 보드 b에 복사시킨 것임. 복사 시켰다는 것은 보는 것은 되지만 데이터 수정은 안됨. 값을 보여주기만 하면 되니까 foreach문을 사용한 것임.
						System.out.println(b.id+"\t"+b.title+"\t"+b.writtenDate);
					}
					System.out.println("글번호 선택 (0은 종료)");
					System.out.print("> ");
					int boardId = scan.nextInt();//사용자로부터 받은 숫자를 보드아이디에 넣음.
					if (boardId != 0) {//사용자가 어떤 글을 보기 위해서 그 번호를 입력 한 것임.
						Board b = select(boardId);//사용자가 입력한 번호 = boardId, 보드 b 기본 생성자로 호출된 값이 들어온것 보통은 0, String이나 Date는 null.
						if(b.id == 0) {//잘못 숫자를 입력하면 아래 내용이 출력됨. select 메소드 봐야함.
							System.out.println("잘못 선택하셨습니다.");
						} else {
							System.out.println("===================");
							System.out.println("제목 : "+b.title);
							System.out.println("===================");
							System.out.println("작성일: "+b.writtenDate);
							System.out.println("수정일: "+b.updatedDate);
							System.out.println("===================");
							System.out.println("\t내용\t");
							System.out.println(b.content);
							System.out.println("===================");
							System.out.println("1. 수정 2. 삭제 (0은 메인 메뉴로)");
							System.out.print("> ");
							choice = scan.nextInt();
							if (choice == 1) {//제목과 내용을 새로 입력해서 수정하는 것.
								scan.nextLine();
								Board update = new Board();
								update.id = boardId;//현재 목록들은 보드아이디에 있음.
								System.out.print("제목: ");
								update.title = scan.nextLine();
								System.out.print("내용: ");
								update.content = scan.nextLine();
								update(update);
							} else if(choice == 2) {//삭제
								delete(boardId);
							}
						}
					}
				}
			}else if (choice == 2) {//메인 메뉴에서 2를 사용했을 때.
				scan.nextLine();
				System.out.println("===================");
				System.out.println("제목: ");
				String title = scan.nextLine();
				System.out.println("===================");
				System.out.println("내용: ");
				String content = scan.nextLine();
				Board b = new Board();
				b.title = title;
				b.content = content;
				insert(b);
				

			}
		}
		scan.close();
	}
}

//저 보드클래스를 다른 곳에서 재사용 안됨. 고급화된 프로그램을 만들게 되서 이 코드를 재사용 할 수 있을까?
//3단계가 스프링이랑 연관됨. 스프링같은 경우에는 얘네 자체가 재사용성임. 스프링사용하기 전에 ejb를 사용함.
//ejb는 시스템의 그 전체가 올라가야되기 때문에 재사용성이 낮아지고 의존성이 높아짐. 자바의 탄생 이유에 반하게 된다.
//즉, 이 코드를 사용하기 위해서는 필요없는 메인 메소드까지 다 가져가야 함.