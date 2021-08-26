package ht.dao;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import ht.DB;

public class Table {
    public boolean tableExists(String tableName){
        try{
            DatabaseMetaData md = DB.getInstance().getConnection().getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            boolean b = rs.next();
            rs.close();
            if(b){
            	return true;
            }else{
            	return false;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return false;
    }
}
