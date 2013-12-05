package me.ericbarber.jaxb.test;

//import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
//import javax.xml.bind.annotation.XmlElement;

//@XmlElement(name = "item")
@XmlType(propOrder = { "title", "url", "thumb", "description", "upVote", "downVote","dbID","approved"})
public class item {

	private String title;
	private String url;
	private String thumburl;
	private String description;
	private int upvote;
	private int downvote;
	private int dbID;
	private boolean approved;
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getThumb(){
		return thumburl;
	}
	
	public void setThumb(String thumburl){
		this.thumburl = thumburl;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public int getUpVote(){
		return upvote;
	}
	
	public void setUpVote(int upvote){
		this.upvote = upvote;
	}
	
	public int getDownVote(){
		return downvote;
	}
	
	public void setDownVote(int downvote){
		this.downvote = downvote;
	}
	
	public int getdbID(){
		return this.dbID;
	}
	
	public void setdbID(int id){
		this.dbID = id;
	}
	
	public boolean getApproved(){
		return this.approved;
	}
	
	public void setApproved(boolean status){
		this.approved = status;
	}
	}
}
