package me.ericbarber.jaxb.test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class itemOperations {
	private dbconn DBConnection;
	private Connection connect;
	private Statement statement = null;
  	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
  	private ArrayList<item> items;
  	
	private void closePreparedStatementConnections(dbconn DB, PreparedStatement ps,Connection c) throws SQLException{
  		try{
		ps.close();
		c.close();
		DB.close();
  		}catch(Exception e){
  			e.printStackTrace();
  		}
  	}
	
	private void closeResultSetConnections(dbconn DB, Statement s,ResultSet rs,Connection c) throws SQLException{
  		try{
		rs.close();
		s.close();
		c.close();
		DB.close();
  		}catch(Exception e){
  			e.printStackTrace();
  		}
  	}
	
	private void connectToDb() throws SQLException{
		try{
		DBConnection = new dbconn();
		connect = DBConnection.getConnection();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private item generateItem(ResultSet rs, int id) throws SQLException{
		item returnItem = new item();
		try{
			while(rs.next()){
				if(rs.getInt("ID") == id){
				String title = rs.getString("title");
				String url = rs.getString("url");
				String thumburl = rs.getString("thumburl");
				String description = rs.getString("description");
				int upvote = rs.getInt("upvote");
				int downvote = rs.getInt("downvote");
				boolean status = rs.getBoolean("approved");

				returnItem.setTitle(title);
				returnItem.setUrl(url);
				returnItem.setThumb(thumburl);
				returnItem.setDescription(description);
				returnItem.setUpVote(upvote);
				returnItem.setDownVote(downvote);
				returnItem.setdbID(id);
				returnItem.setApproved(status);
			}
		}
		}catch (Exception e){
			e.printStackTrace();
		}
		return returnItem;
	}
	
	private ArrayList<item> generateArray(ArrayList<item> items, ResultSet rs) throws SQLException{
		try{
		while (rs.next()) {
			item newItem = new item();
			String title = rs.getString("title");
			String url = rs.getString("url");
			String thumburl = rs.getString("thumburl");
			String description = rs.getString("description");
			int upvote = rs.getInt("upvote");
			int downvote = rs.getInt("downvote");
			int id = rs.getInt("ID");
			boolean status = rs.getBoolean("approved");
			newItem.setTitle(title);
			newItem.setUrl(url);
			newItem.setThumb(thumburl);
			newItem.setDescription(description);
			newItem.setUpVote(upvote);
			newItem.setDownVote(downvote);
			newItem.setdbID(id);
			newItem.setApproved(status);
			items.add(newItem);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return items;
	}
  	
  	public void voteItemUp(int id) throws SQLException{
  		try{
			connectToDb();
  			preparedStatement = connect.prepareStatement("update SALESTEST.SALE set upvote = upvote + 1 where id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
  		}catch(Exception e){
  			e.printStackTrace();
  		}finally{
  			closePreparedStatementConnections(DBConnection,preparedStatement,connect);
  		}
  	}
  	
  	public void voteItemDown(int id) throws SQLException{
  		try{
  			connectToDb();
  			preparedStatement = connect.prepareStatement("update SALESTEST.SALE set downvote = downvote + 1 where id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
  		}catch(Exception e){
  			e.printStackTrace();
  		}finally{
  			closePreparedStatementConnections(DBConnection,preparedStatement,connect);	
  		}	
  	}
  	
  	public void deleteSpecificItem(int id) throws SQLException{
  		try{
  			connectToDb();
  			preparedStatement = connect.prepareStatement("DELETE from SALESTEST.SALE where id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
  		}catch (Exception e){
  			e.printStackTrace();
  		}finally{
  			closePreparedStatementConnections(DBConnection,preparedStatement,connect);	
  		}
  	}
  	
  	public void changeItemStatus(boolean status,int id) throws SQLException{
  		try{
  			connectToDb();
  			preparedStatement = connect.prepareStatement("update SALESTEST.SALE set approved = ? where id = ?");
			preparedStatement.setBoolean(1, status);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
  		}catch(Exception e){
  			e.printStackTrace();
  		}finally{
  			closePreparedStatementConnections(DBConnection,preparedStatement,connect);	
  		}
  	}
  	
  	public void insertNewItem(String newTitle, String newUrl, String thumbUrl, String description) throws SQLException{
  		try{
  			connectToDb();
  			preparedStatement = connect.prepareStatement("insert into SALESTEST.SALE values (default, ?, ?, ?, ?, 0, 0, ?)");
			preparedStatement.setString(1, newTitle);
			preparedStatement.setString(2, newUrl);
			preparedStatement.setString(3, thumbUrl);
			preparedStatement.setString(4, description);
			preparedStatement.setBoolean(5, false);
			preparedStatement.executeUpdate();
  		}catch (Exception e){
  			e.printStackTrace();
  		}finally{
  			closePreparedStatementConnections(DBConnection,preparedStatement,connect);
  		}	
  	}

	public item getSpecificItem(int id) throws SQLException{
		item specificItem = new item();
		try{
			connectToDb();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from SALESTEST.SALE");
			specificItem = generateItem(resultSet, id);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			closeResultSetConnections(DBConnection,statement,resultSet,connect);
		}
		return specificItem;
	}
	
	public item getRandomItem() throws SQLException{
		item randomItem = new item();
		int totalSales = 0;
		try{
			connectToDb();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT COUNT(ID) as count from SALESTEST.SALE");
			while(resultSet.next()){
				totalSales = resultSet.getInt("count");
			}
			Random rand = new Random();
			int randomSale = rand.nextInt((totalSales - 1)+1)+1;	
			statement.close();
			statement = connect.createStatement();
			while(randomItem.getTitle() == null){
				randomSale = rand.nextInt((totalSales - 1)+1)+1;
				resultSet = statement.executeQuery("select * from SALESTEST.SALE where ID =  " + randomSale);
				randomItem = generateItem(resultSet,randomSale);
				if(randomItem.getTitle() != null){
					break;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			closeResultSetConnections(DBConnection,statement,resultSet,connect);
		}
		return randomItem;
	}
	
  	public ArrayList<item> generateApprovedItems() throws SQLException{
		ArrayList<item> approvedItems = new ArrayList<item>();
  		try{
  			connectToDb();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from SALESTEST.SALE");
			items = new ArrayList<item>();
			items = generateArray(items, resultSet);
			for(item i : items){
				if(i.getApproved() == true){
					approvedItems.add(i);
				}
			}
			/*Unique stuff*/
  		}catch (Exception e){
  			e.printStackTrace();
  		}finally{
			closeResultSetConnections(DBConnection,statement,resultSet,connect);	
  		}
  		return approvedItems;
  	}
  	
  	public ArrayList<item> generateAllItems() throws SQLException{
  		try{
  			connectToDb();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from SALESTEST.SALE");
			items = new ArrayList<item>();
  			items = generateArray(items,resultSet);
  		}catch (Exception e){
  			
  		}finally{
			closeResultSetConnections(DBConnection,statement,resultSet,connect);
  		}
  		return items;
  	}
}
