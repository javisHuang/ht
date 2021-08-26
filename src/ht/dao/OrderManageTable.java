package ht.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ht.DB;
import ht.form.OrderManageForm;

public class OrderManageTable extends Table {
	public static String Name="OrderManage";
	public static OrderManageTable getInstance() {
		return StaticInnerClassHolder.instance;
	}

	private static class StaticInnerClassHolder {
		private static OrderManageTable instance = new OrderManageTable();
	}
	public List<HashMap<String,Object>> querylist(){
		final String SQL = "select * from "+Name+"";
		try {
			Connection conn = DB.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			ResultSetMetaData md = rs.getMetaData();
		    int columns = md.getColumnCount();
		    List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();

		    while (rs.next()) {
		        HashMap<String,Object> row = new HashMap<String, Object>(columns);
		        for(int i=1; i<=columns; ++i) {
		            row.put(md.getColumnName(i),rs.getObject(i));
		        }
		        list.add(row);
		    }
		    return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void insertData(OrderManageForm orderform) throws SQLException{
		final String SQL = "INSERT INTO "+Name+" (orderId,datetime,createtime) VALUES (?,?,?)";
        PreparedStatement ps;
        ps = DB.getInstance().getConnection().prepareStatement(SQL);
        ps.setLong(1, orderform.getOrderId());
        ps.setString(2, orderform.getDatetime());
        ps.setString(3, orderform.getCreatetime());
        ps.executeUpdate();
        ps.close();
	}
	public void create() {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS "+Name+" (\n"
	                + "	orderId integer PRIMARY KEY,\n"
	                + "	datetime text NOT NULL\n"
	                + ");";
			Connection conn = DB.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		    stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
