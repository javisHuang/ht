package ht.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ht.Config;
import ht.DB;
import ht.dao.OrderManageTable;

public class UpgradeDBService {
	public static UpgradeDBService getInstance() {
		return StaticInnerClassHolder.instance;
	}

	private static class StaticInnerClassHolder {
		private static UpgradeDBService instance = new UpgradeDBService();
	}

	public boolean update() {
		boolean isok=true;
		try {
			DB.getInstance().getConnection().setAutoCommit(false);
			switch(Config.CURRENT_VERSION){
				case 1:
					up1();
			}
			DB.getInstance().getConnection().commit();
			DB.getInstance().getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			isok=false;
			try {
				e.printStackTrace();
				DB.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return isok;
	}
	private boolean up1() throws SQLException {
		String SQL = "ALTER TABLE "+OrderManageTable.Name+" ADD createtime text";
		Connection conn = DB.getInstance().getConnection();
		Statement stmt = conn.createStatement();
		stmt.execute(SQL);
	    stmt.close();
		return true;
	}

}
