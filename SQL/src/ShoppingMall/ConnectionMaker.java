package ShoppingMall;

import java.sql.Connection;

public interface ConnectionMaker {
	public Connection makeConnection() throws Exception;
}
