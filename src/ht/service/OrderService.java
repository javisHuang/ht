package ht.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import ht.DB;
import ht.dao.OrderFormTable;
import ht.dao.OrderManageTable;
import ht.form.OrderFormForm;
import ht.form.OrderManageForm;

public class OrderService {
	public static OrderService getInstance() {
		return StaticInnerClassHolder.instance;
	}

	private static class StaticInnerClassHolder {
		private static OrderService instance = new OrderService();
	}
	
	public void addOrder(OrderManageForm ordermform,List<OrderFormForm> orderoforms){
		try {
			DB.getInstance().getConnection().setAutoCommit(false);
			OrderManageTable.getInstance().insertData(ordermform);
			OrderFormTable.getInstance().insertData(orderoforms);
			DB.getInstance().getConnection().commit();
			DB.getInstance().getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				DB.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public Object[][] queryList() {
		List<HashMap<String, Object>> maps = OrderManageTable.getInstance().querylist();
		Object[][] record = new Object[maps.size()][3];
		for(int i=0;i<maps.size();i=i+1){
			HashMap<String, Object> r = maps.get(i);
			Object createtime = r.get("createtime");
			if(createtime == null){
				createtime = "";
			}
			record[i] = new Object[]{r.get("orderId"),r.get("datetime"),createtime};
		}
		return record;
	}
}
