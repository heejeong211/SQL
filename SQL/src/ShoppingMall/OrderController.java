package ShoppingMall;

import java.sql.*;
import java.util.ArrayList;

public class OrderController {
	Connection conn;

	public OrderController(ConnectionMaker m) throws Exception {
		conn = m.makeConnection();
	}

	public ArrayList<CustomerVO> selectAll() {

		ArrayList<CustomerVO> list = new ArrayList<>();
		String sql = "SELECT * FROM customer";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerVO c = new CustomerVO();
				c.setId(rs.getInt("id"));
				c.setShoppingmallId(rs.getString("shoppingmallId"));
				c.setName(rs.getString("name"));
				c.setPhoneNumber(rs.getString("phoneNumber"));
				c.setAddress(rs.getString("address"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<ItemVO> selectAll1() {

		ArrayList<ItemVO> list = new ArrayList<>();
		String sql = "SELECT * FROM item";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ItemVO c = new ItemVO();
				c.setId(rs.getInt("id"));
				c.setItem(rs.getString("item"));
				c.setPrice(rs.getInt("price"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public CustomerVO select(int id) {
		CustomerVO customerVO = new CustomerVO();
		String sql = "SELECT * FROM customer WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				customerVO.setId(id);
				customerVO.setShoppingmallId(rs.getString("shoppingmallId"));
				customerVO.setName(rs.getString("name"));
				customerVO.setPhoneNumber(rs.getString("phoneNumber"));
				customerVO.setAddress(rs.getString("address"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerVO;
	}

	public ItemVO select1(int id) {
		ItemVO itemVO = new ItemVO();
		String sql = "SELECT * FROM item WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				itemVO.setId(id);
				itemVO.setItem(rs.getString("item"));
				itemVO.setPrice(rs.getInt("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemVO;
	}

	public void delete(int id) {
		String sql = "DELETE FROM customer WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete1(int id) {
		String sql = "DELETE FROM item WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
