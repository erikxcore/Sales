package me.ericbarber.jaxb.test;

import java.sql.Connection;
import java.sql.DriverManager;


public class dbconn {
 	  private static final String DRIVER = "com.mysql.jdbc.Driver";
 	  private static final String URL = "jdbc:mysql://127.0.0.1:3306";
 	  private static final String USERNAME = "root";
 	  private static final String PASSWORD = "pass1";
 	  public Connection connect = null;
	
	  public Connection getConnection() throws Exception{
		  try{
		  	Class.forName(DRIVER);
		  	connect = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		  }catch(Exception e){
			  System.out.println("No connection!");
			  e.printStackTrace();
		  	throw e;
		  }finally{
		  	close();
		  }
		  return connect;
	  }

		   public void close() {
		      try {
		        if (connect != null) {
		          connect.close();
		        }
		      } catch (Exception e) {
		    	  e.printStackTrace();
		      }
		    }
}