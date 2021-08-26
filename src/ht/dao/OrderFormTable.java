package ht.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ht.DB;
import ht.form.OrderFormForm;

public class OrderFormTable extends Table {
	public static String Name="OrderForm";
	public static OrderFormTable getInstance() {
		return StaticInnerClassHolder.instance;
	}

	private static class StaticInnerClassHolder {
		private static OrderFormTable instance = new OrderFormTable();
	}
	public void insertData(List<OrderFormForm> orderforms) throws SQLException{
		final String SQL = "INSERT INTO "+Name+" (orderId,key,value) VALUES (?,?,?)";
		for(OrderFormForm orderform:orderforms){
	        PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(SQL);
	        ps.setLong(1, orderform.getOrderId());
	        ps.setString(2, orderform.getKey());
	        ps.setString(3, orderform.getValue());
	        ps.executeUpdate();
	        ps.close();
		}
	}
	public void create() {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS "+Name+" (\n"
	                + "	orderId integer,\n"
	                + "	key text NOT NULL,\n"
	                + "	value text NOT NULL,\n"
	                + " primary key (orderId,key));";
			Connection conn = DB.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		    stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
