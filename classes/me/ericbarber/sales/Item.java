package me.ericbarber.sales;
/**
 * Item is the model for any Sale Item that is pulled from the Database.
 * 
 * @author Eric Barber
 * @version 0.1
 */

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "title", "url", "thumb", "description", "upVote", "downVote","dbID","approved"})
public class Item {

	private String title;
	private String url;
	private String thumburl;
	private String description;
	private int upvote;
	private int downvote;
	private int dbID;
	private boolean approved;
	
	/**
	 * Returns a String representation of an item title.
	 * 
	 * @return title
	 * @since 0.1
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Set a String representation of an item title.
	 * 
	 * @param title - Desired title of an item.
	 * @since 0.1
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns a String representation of an item's URL.
	 * 
	 * @return url
	 * @since 0.1
	 */
	public String getUrl(){
		return url;
	}
	
	/**
	 * Set a String representation of an item's URL.
	 * 
	 * @param url - Desired direct URL for an item's sale.
	 */
	public void setUrl(String url){
		this.url = url;
	}
	
	/**
	 * Returns a String representation of an item's thumbnail URL.
	 * 
	 * @return thumburl
	 */
	public String getThumb(){
		return thumburl;
	}
	
	/**
	 * Set a String representation of an item's thumbnail URL.
	 * 
	 * @param thumburl
	 */
	public void setThumb(String thumburl){
		this.thumburl = thumburl;
	}
	
	/**
	 * Returns a String representation of an item's description.
	 * 
	 * @return description
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Set a String representation of an item's description.
	 * 
	 * @param description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Returns a integer representation of how many 'up votes' an item has.
	 * 
	 * @return upvote
	 */
	public int getUpVote(){
		return upvote;
	}
	
	/**
	 * Set an integer representation of how many votes an item should have. Should first get total
	 * votes then add however many votes (typically one).
	 * 
	 * @param upvote
	 */
	public void setUpVote(int upvote){
		this.upvote = upvote;
	}
	
	/**
	 * Returns an integer representation of how many 'down votes' an item should have.
	 * 
	 * @return downvote
	 */
	public int getDownVote(){
		return downvote;
	}
	
	/**
	 * Set an integer representation of how many votes an item should have. Should first get total
	 * votes then add however many votes (typically one).
	 * 
	 * @param downvote
	 */
	public void setDownVote(int downvote){
		this.downvote = downvote;
	}
	
	/**
	 * Returns an integer representation of the ID an item has in the database.
	 * 
	 * @return dbID
	 */
	public int getdbID(){
		return dbID;
	}
	
	/**
	 * Set an integer representation of what an Item's ID is within a database. Should only be used after getting an item
	 * from the database and then setting the dbID variable locally for manipulation such as deletion, voting, or changing
	 * approval status.
	 * 
	 * @param id
	 */
	public void setdbID(int id){
		this.dbID = id;
	}
	
	/**
	 * Returns a boolean representation of an item's approval status. If true then the item should be accessible by the 'public' via
	 * getApprovedItems, if false the Item will only be in an Items Array List if generateAllItems is called.
	 * 
	 * @return approved
	 * @see Items
	 */
	public boolean getApproved(){
		return approved;
	}
	
	/**
	 * Set a boolean representation of an item's approval status. If true then the item should be accessible by the 'public' via
	 * getApprovedItems, if false the Item will only be in an Items Array List if generateAllItems is called.
	 * 
	 * @param status
	 * @see Items
	 */
	public void setApproved(boolean status){
		this.approved = status;
	}
}
