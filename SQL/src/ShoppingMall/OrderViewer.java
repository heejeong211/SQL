package ShoppingMall;

import java.util.*;

public class OrderViewer {
	public static void main(String[] args) throws Exception {
		ConnectionMaker m = new MySqlConnectionMaker();
		OrderController c = new OrderController(m);
		Scanner scan = new Scanner(System.in);
		showMenu(c, scan);
		scan.close();
	}

	private static void showMenu(OrderController orderController, Scanner scan) {
		while (true) {
			System.out.println("=========================");
			System.out.println("♥♡HJ쇼핑몰에 오실 걸 환영합니다♡♥");
			System.out.println("=========================");
			System.out.println("1.회원정보 2.주문확인 3.종료하기");
			System.out.print(">");
			int choice = scan.nextInt();
			if (choice == 3) {
				System.out.println("이용해주셔서 감사합니다.");
				break;
			} else if (choice == 1) {
				showList(orderController, scan);
			} else if (choice == 2) {
				showList1(orderController, scan);
			}
		}

	}

	private static void showList(OrderController orderController, Scanner scan) {
		ArrayList<CustomerVO> list = orderController.selectAll();
		System.out.println("========================================================");
		System.out.println("회원번호\tID\t이름\t핸드폰번호\t\t주소");
		System.out.println("========================================================");
		if (list.size() == 0) {
			System.out.println("작성된 글이 없습니다.");
		} else {
			for (CustomerVO c : list) {
				System.out.println(c);
			}
			System.out.println("========================================================");
			System.out.println("\"회원탈퇴를 원하시는 분들은 본인의 회원번호를 눌러주세요.\"");
			System.out.println("========================================================");
			System.out.println("0.뒤로가기");
			System.out.print(">");
			int id = scan.nextInt();
			if (id != 0) {
				showOne(orderController, scan, id);

			}

		}
	}

	private static void showOne(OrderController orderController, Scanner scan, int id) {
		CustomerVO customerVO = orderController.select(id);
		if (customerVO.getId() == 0) {
			System.out.println("잘못 입력하셨습니다.");
			showList(orderController, scan);
		} else {
			System.out.println("==============================");
			System.out.println("정말로 정말로 탈퇴하시겠습니까? ㅠㅅㅜ ");
			System.out.println("==============================");
			System.out.println("1.회원탈퇴 2.뒤로가기 3.홈으로 돌아가기");
			System.out.print(">");
			int choice = scan.nextInt();
			if (choice == 2) {
				showList(orderController, scan);
			} else if (choice == 1) {
				delete(orderController, id);
				delete1(orderController, id);
				showList(orderController, scan);
			}
		}

	}

	private static void delete(OrderController orderController, int id) {
		orderController.delete(id);

	}

	private static void showList1(OrderController orderController, Scanner scan) {
		ArrayList<ItemVO> list = orderController.selectAll1();
		System.out.println("================================================");
		System.out.println("\"더 자세한 주문 조회를 하고 싶으시면 본인의 회원번호를 누르세요\"");
		System.out.println("------------------------------------------------");
		System.out.println("주문번호\t상품\t가격");
		System.out.println("================================================");
		if (list.size() == 0) {
			System.out.println("작성된 글이 없습니다.");
		} else {
			for (ItemVO c : list) {
				System.out.println(c);
			}
			System.out.println("===============================================");
			System.out.println("0.뒤로가기");
			System.out.print(">");
			int id = scan.nextInt();
			if (id != 0) {
				showOne1(orderController, scan, id);

			}
		}
	}

	private static void showOne1(OrderController orderController, Scanner scan, int id) {
		CustomerVO customerVO = orderController.select(id);
		if (customerVO.getId() == 0) {
			System.out.println("잘못 입력하셨습니다.");
			showList(orderController, scan);
		} else {
			System.out.println("=======================");
			System.out.println("회원아이디: " + customerVO.getShoppingmallId());
			System.out.println("회원이름: " + customerVO.getName());
			System.out.println("핸드폰번호: " + customerVO.getPhoneNumber());
			System.out.println("회원주소: " + customerVO.getAddress());
			System.out.println("=======================");
			System.out.println("1.주문취소 2.뒤로가기 3.홈으로 돌아가기");
			System.out.print(">");
			int choice = scan.nextInt();
			if (choice == 2) {
				showList1(orderController, scan);
			} else if (choice == 1) {
				delete1(orderController, id);
				showList1(orderController, scan);
			}
		}
	}

	private static void delete1(OrderController orderController, int id) {
		orderController.delete1(id);
	}
}