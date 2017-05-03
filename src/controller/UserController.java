package controller;


import modal.User;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import database.DbConn;


public class UserController {
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;
	boolean result = false;
	public int registerUser(User user)throws Exception{
		int state = 0;

		try{
			 Registry creg = LocateRegistry.getRegistry(host);
	         StaticRI cstub = (StaticRI)creg.lookup(regName);
	         result = cstub.registerUser(user);
	         JOptionPane.showMessageDialog(null,result, "Result", JOptionPane.WARNING_MESSAGE);
	         if(result){
	        	 JOptionPane.showMessageDialog(null,"Register", "Successful register", JOptionPane.WARNING_MESSAGE);
	         } else {
	        	 JOptionPane.showMessageDialog(null,"Register", "Error on register", JOptionPane.WARNING_MESSAGE);
	         }
		}catch(Exception err){
			 JOptionPane.showMessageDialog(null,err, "Connection with Server Error :", JOptionPane.WARNING_MESSAGE);
		}

		return state;
	}
	
	
}
