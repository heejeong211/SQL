package Board03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BoardViewer {
	public static void main(String[] args) throws Exception {
		ConnectionMaker m = new MySqlConnectionMaker();//ConnectionMaker 는 인터페이스.
		BoardController c = new BoardController(m);//데이터베이스와 통신을 하는 보드컨트롤러 클래스 객체 c를 만듦. 보드 컨트롤러는 마이 에스큐엘이랑 연결이 되야 프로그램이 돌아가서 파라미터로 m을 넣어줌.
		Scanner scan = new Scanner(System.in);
		showMenu(c, scan);//c = 보드컨트롤러의 객체
		scan.close();
	}
	
	private static void showMenu(BoardController boardController, Scanner scan) {
		while(true) {
		System.out.println("============================");
		System.out.println("\t게시판\t");
		System.out.println("============================");
		System.out.println("1.목록보기 2.입력하기 3.종료하기");
		System.out.print(">");
		int choice = scan.nextInt();
		if(choice == 3) {
			System.out.println("사용해주셔서 감사합니다.");
			break; //scan.close();가 됨.
		} else if (choice == 1) {
			showList(boardController, scan);
		} else if (choice == 2) {
			insert(boardController, scan);
		}
	  }
   }
	private static void insert(BoardController boardController, Scanner scan) {
		scan.nextLine();//버퍼 메모리를 비워주기 위해서
		System.out.print("제목 : ");
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle(scan.nextLine());
		System.out.print("내용 : ");
		boardVO.setContent(scan.nextLine());
		boardController.insert(boardVO);//보드컨트롤러에서 데이터를 데리고 와서 입력.
		showList(boardController, scan);
		
	}

	private static void showList(BoardController boardController, Scanner scan) {
		ArrayList<BoardVO> list = boardController.selectAll();//보드컨트롤러에 안에 있는 내용을 보드브이오에 넣음. list의 내용은 보드브이오의 모든 내용임.
		Collections.reverse(list);
		System.out.println("==========================");
		System.out.println("글번호\t제목\t입력날짜");
		System.out.println("==========================");
		if(list.size() == 0) {//리스트에 글이 없는 경우
			System.out.println("작성된 글이 없습니다.");
		} else {//리스트에 글이 있는 경우
			for(BoardVO b : list) {
				System.out.println(b); //보드 브이오에서 toString()한 결과
			}
			System.out.println("글 번호 선택 (0은 메인 메뉴로)");
			System.out.print(">");
			int id = scan.nextInt();
			if (id != 0) {
				showOne(boardController, scan, id);//왼쪽에 할당받는 연산자가 없기 때문에 리턴값이 void임. showOne이 상세보기를 할 수 있도록 해줌.
			}
		}
	}

	private static void showOne(BoardController boardController, Scanner scan, int id) {
		BoardVO boardVO = boardController.select(id);
		if (boardVO.getId() == 0) { //잘못된 값을 넣었기 때문에 기본 생성자 값만 있음. 
			System.out.println("잘못 입력하셨습니다.");
			showList(boardController, scan);
		} else {//정상적으로 값들이 입력되있음.
			System.out.println("====================");
			System.out.println("제목: "+boardVO.getTitle());
			System.out.println("====================");
			System.out.println("작성일: "+boardVO.getWrittenDate());
			System.out.println("수정일: "+boardVO.getUpdatedDate());
			System.out.println("====================");
			System.out.println(boardVO.getContent());
			System.out.println("====================");
			System.out.println("1.수정 2.삭제 3.리스트로");
			System.out.println(">");
			int choice = scan.nextInt();
			if(choice == 3) {
				showList(boardController, scan);//메뉴를 보여줌.	
			} else if(choice == 1) {
				update(boardController, scan, id);//수정할 때. 통신과 입력받는거, 위치를 지정하기 위해 id를 써줌.
			} else if (choice == 2) {
				delete(boardController, id);//삭제할 때
				showList(boardController, scan);//삭제가 끝나면 쇼리스트를 통해서 목록을 볼 수 있게 됨.
			}
		}
	}

	private static void delete(BoardController boardController, int id) {
        boardController.delete(id); //삭제할 때는 아이디만 있으면 삭제 가능.
	}

	private static void update(BoardController boardController, Scanner scan, int id) {//빈 상자이기 때문에 세터를 이용해서 값을 넣어야 함.
		 scan.nextLine();
	     BoardVO boardVO = new BoardVO();
		 System.out.print("제목: ");
		 boardVO.setTitle(scan.nextLine());//보드브이오에 사용자가 입력한 수정할 내용을 담아야 함.
		 System.out.print("내용: ");
		 boardVO.setContent(scan.nextLine());
		 boardVO.setId(id);//아이디가 없으면 어디에 수정해야할 지 모름.
		 boardController.update(boardVO);//글을 수정하는 내용을 보드컨트롤러가 가지고 있음. 보드컨트롤러가 가지고 있는 파라미터가 보드브이오이기 때문에 여기에서도 써줘야 함.
		 showOne(boardController, scan, id);
	}
}



//우리가 지금 하고 있는 것이 마이에스큐엘인데 오라클로 바꿔줄려면 많은 곳을 바꿔줘야 한다.
//하드하게 하는 것을 소프트하게 바꾸어주기 위해서 보드3 패키지를 만듦.
//외부에서 가져와서 편하게 쓰는 것.