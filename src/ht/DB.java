package ht;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import ht.dao.OrderFormTable;
import ht.dao.OrderManageTable;
import ht.dao.VerTable;
import ht.service.UpgradeDBService;
public class DB {
	Connection connection = null;
	public Connection getConnection() {
		return connection;
	}

	private DB() {
	}

	public static DB getInstance() {
		return StaticInnerClassHolder.instance;
	}

	private static class StaticInnerClassHolder {
		private static DB instance = new DB();
	}

	public void check() throws Exception {
		try {
			createNewDatabase("ht.db");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("DB建立失敗");
		}
		if(VerTable.getInstance().isInit()){
			VerTable.getInstance().create();
			OrderManageTable.getInstance().create();
			OrderFormTable.getInstance().create();
		}else{
			if(Config.CURRENT_VERSION > VerTable.getInstance().getVer()){
				if(UpgradeDBService.getInstance().update()){
					VerTable.getInstance().update(Config.CURRENT_VERSION);
				}else{
					throw new Exception("升級失敗");
				};
			}
		}
	}
	
	/**
     * Connect to a sample database
     *
     * @param fileName the database file name
	 * @throws SQLException 
     */
    public void createNewDatabase(String fileName) throws SQLException {

        String url = "jdbc:sqlite:lib/" + fileName;
        connection = DriverManager.getConnection(url);
        if (connection != null) {
            DatabaseMetaData meta = connection.getMetaData();
            System.out.println("The driver name is " + meta.getDriverName());
            System.out.println("A new database has been created.");
        }
    }

}
