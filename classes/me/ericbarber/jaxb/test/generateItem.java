package me.ericbarber.jaxb.test;

import java.sql.*;
import java.util.ArrayList;

public class generateItem {
	
	private dbconn DBConnection;
	private Connection connect;
	private Statement statement = null;
  	private ResultSet resultSet = null;
  	private ArrayList<item> items;
  	
  	public generateItem(){
  		try {
			readDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
  	}
  	
  	public void readDataBase() throws Exception {
		try{
			DBConnection = new dbconn();
		    connect = DBConnection.getConnection();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from SALESTEST.SALE");
			writeResultSet(resultSet);
		} catch (Exception e){
			e.printStackTrace();
		}
  	}
  	
  	private void writeResultSet(ResultSet resultSet) throws SQLException {
		try{
			items = new ArrayList<item>();
		while (resultSet.next()) {
			item newItem = new item();
			
			String title = resultSet.getString("title");
			String url = resultSet.getString("url");
			String thumburl = resultSet.getString("thumburl");
			String description = resultSet.getString("description");
			int upvote = resultSet.getInt("upvote");
			int downvote = resultSet.getInt("downvote");
			int id = resultSet.getInt("ID");
			boolean status = resultSet.getBoolean("approved");
			
			newItem.setTitle(title);
			newItem.setUrl(url);
			newItem.setThumb(thumburl);
			newItem.setDescription(description);
			newItem.setUpVote(upvote);
			newItem.setDownVote(downvote);
			newItem.setdbID(id);
			newItem.setApproved(status);
			
			if(status == true){
			items.add(newItem);
			}
			
		}
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			resultSet.close();
			statement.close();
			connect.close();
			DBConnection.close();
		}
	}
  	
  	public ArrayList<item> generateItems(){
  		return items;
  	}
}
