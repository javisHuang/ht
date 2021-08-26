package ht.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ht.DB;

public class VerTable extends Table{
	public static String Name="Ver";
	public static VerTable getInstance() {
		return StaticInnerClassHolder.instance;
	}
	private static class StaticInnerClassHolder {
		private static VerTable instance = new VerTable();
	}
	public int getVer(){
		final String SQL = "select * from "+Name+"";
		try {
			Connection conn = DB.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			rs.next();
			int ver = rs.getInt("ver");
			rs.close();
		    stmt.close();
			return ver;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	public void update(int currentVersion) {
		try {
			String sql = "UPDATE "+Name+" SET ver = "+currentVersion+";";
			Connection conn = DB.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		    stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void create() {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS "+Name+" (\n"
	                + "	ver integer);";
			Connection conn = DB.getInstance().getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			final String SQL = "INSERT INTO "+Name+" (ver) VALUES (0)";
			stmt.execute(SQL);
		    stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean isInit(){
		if(tableExists(Name)){
			return false;
		}
		return true;
	}
}
