package Board03;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnectionMaker implements ConnectionMaker {
	private final String URL = "jdbc:mysql://localhost:3306/example?serverTimezone=UTC";
	private final String ID = "root";
	private final String PASSWORD = "1111";
	public Connection makeConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(URL, ID, PASSWORD);
		return conn;//만들어진 커넥션 객체를 리턴해 주면 우리가 사용할 컨트롤러에서는 얘가 어떻게 만들어 지는지 알 필요가 없다.
	}

}
