package ht;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Tools {
	public static Component findComById(JPanel panel,String key){
		Component[] children = panel.getComponents();
		for (Component child : children) {
		    if (child instanceof JTextField) {
		        Object c = ((JTextField)child).getClientProperty("id");
		        if (c instanceof String) {
		        	if(c.equals(key)){
		        		return (JTextField)child;
		        	}
		        }
		    }
		}
		return null;
	}
	public static Long getOrderId(){
		return Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
	}
}
