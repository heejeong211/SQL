import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//자바와 연결하기 위해서는 자바의 SQL패키지의 커넥션 클래스랑 드라이버매니저 클래스랑  SQLException 클래스를 불러와야함
//또한 자바에서는 MySQL 드라이버를 기본으로 제공하지 않기 때문에 외부에서 받아와서 로딩해줘야 함.
public class Ex01 {
	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");        //아이피주소
			 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila?serverTimezone=UTC",
					 "root", "1111");
			 System.out.println("로그인 성공");
		} catch(ClassNotFoundException | SQLException e) {
			   e.printStackTrace();			
		} finally {
			if (conn != null && !conn.isClosed());
			conn.close();
		}
	}

}

//가장 기본적인 MySQL이랑 연결하는 프로그램임.
