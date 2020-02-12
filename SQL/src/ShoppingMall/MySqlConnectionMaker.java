package ShoppingMall;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnectionMaker implements ConnectionMaker {
	private final String URL = "jdbc:mysql://localhost:3306/homework?serverTimezone=UTC";
	private final String ID = "root";
	private final String PASSWORD = "1111";
	public Connection makeConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(URL, ID, PASSWORD);
		return conn;
	}
}
