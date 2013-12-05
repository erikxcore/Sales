package me.ericbarber.salesservice;

import java.io.StringWriter;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import me.ericbarber.sales.Item;
import me.ericbarber.sales.ItemOperations;
import me.ericbarber.sales.Items;

@Path("/salesservice")
public class Sales {
ItemOperations itemops = new ItemOperations();

@GET
@Path("/randomitem")
@Produces(MediaType.TEXT_XML)
public String getRandomItem(){
	Item item = new Item();
	try{
	item = itemops.getRandomItem();
	}catch (Exception e){
		return "Please try again later.";
	}
	return "<?xml version=\"1.0\"?>" + 
	"<item>" + 
	"<title>" + item.getTitle() +"</title>" +	
	"<url>" + item.getUrl() +"</url>" +	
	"<thumb>" + item.getThumb() +"</thumb>" +	
	"<description>" + item.getDescription() +"</description>" +	
	"<upvotes>" + item.getUpVote() +"</upvotes>" +	
	"<downvotes>" + item.getDownVote() +"</downvotes>" +	
	"<status>" + item.getApproved() +"</status>" +	
	"</item>";
}

@GET
@Path("/item/{id}")
@Produces(MediaType.TEXT_XML)
public String getSpecificItem(@PathParam("id") int id){
	Item item = new Item();
	try{
	item = itemops.getSpecificItem(id);
	}catch (Exception e){
		return "Please try again later.";
	}
	return "<?xml version=\"1.0\"?>" + 
	"<item>" + 
	"<title>" + item.getTitle() +"</title>" +	
	"<url>" + item.getUrl() +"</url>" +	
	"<thumb>" + item.getThumb() +"</thumb>" +	
	"<description>" + item.getDescription() +"</description>" +	
	"<upvotes>" + item.getUpVote() +"</upvotes>" +	
	"<downvotes>" + item.getDownVote() +"</downvotes>" +	
	"<status>" + item.getApproved() +"</status>" +	 
	"<id>" + item.getdbID() +"</id>" +	 
	"</item>";
}

@POST
@Path("/vote/{id}&{type}")
@Produces(MediaType.TEXT_XML)
public String voteItem(@PathParam("id") int id, @PathParam("type") String type){
String result;
try {
	itemops.voteItem(id, type);
	result = "Thanks for voting!";
} catch (Exception e) {
	 result = "Please try again later.";
}
return "<?xml version=\"1.0\"?>" + "<result>"
		+ result + "</result>";
}


@GET
@Path("/allitems")
@Produces(MediaType.TEXT_XML)
public String getAllItems(){
	Items saleItems = new Items();
	String result = "";
	StringWriter sw = new StringWriter();
	try {
	saleItems.setItems(itemops.generateAllItems());
	JAXBContext context = JAXBContext.newInstance(Items.class);
	Marshaller m = context.createMarshaller();
	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	m.marshal(saleItems, sw);
	result = sw.toString();
	} catch (Exception e) {
		result = "Please try again later.";
		return "<?xml version=\"1.0\"?>" + "<result>"
		+ result + "</result>";
	}
	return result;
}

@GET
@Path("/approveditems")
@Produces(MediaType.TEXT_XML)
public String getApprovedItems(){
	Items saleItems = new Items();
	String result = "";
	StringWriter sw = new StringWriter();
	try {
	saleItems.setItems(itemops.generateApprovedItems());
	JAXBContext context = JAXBContext.newInstance(Items.class);
	Marshaller m = context.createMarshaller();
	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	m.marshal(saleItems, sw);
	result = sw.toString();
	} catch (Exception e) {
		result = "Please try again later.";
		return "<?xml version=\"1.0\"?>" + "<result>"
		+ result + "</result>";
	}
	return result;
}



@POST
@Path("/delete/{id}")
@Produces(MediaType.TEXT_XML)
public String deleteItem(@PathParam("id") int id){
String result;
try{
	itemops.deleteSpecificItem(id);
	result = "You have successfully deleted item " + id;
}catch(Exception e){
	result = "Please try again later.";
}
return "<?xml version=\"1.0\"?>" + "<result>"
+ result + "</result>";
}

@POST
@Path("/add/{title}&{url}&{thumb}&{description}")
@Produces(MediaType.TEXT_XML)
public String insertItem(@PathParam("title") String title,@PathParam("url") String url,@PathParam("thumb") String thumb,@PathParam("description") String description){
String result;
try{
	itemops.insertNewItem(title, url, thumb, description);
	result = "You have successfully added an item.";
}catch(Exception e){
	result = "Please try again later.";
}
return "<?xml version=\"1.0\"?>" + "<result>"
+ result + "</result>";
}

@POST
@Path("/changestatus/{id}&{status}")
@Produces(MediaType.TEXT_XML)
public String changeItemStatus(@PathParam("id") int id, @PathParam("status") String status){
String result;
boolean s = false;
if(status.toUpperCase().equals("APPROVE")){
	s = true;
}else if(status.toUpperCase().equals("DISAPPROVE")){
	s = false;
}
try {
	itemops.changeItemStatus(s, id);
	if(s == true){
		result = "You have approved item " + id;
	}else{
		result = "You have denied item " +id;
	}
} catch (Exception e) {
	 result = "Please try again later.";
}
return "<?xml version=\"1.0\"?>" + "<result>"
		+ result + "</result>";
}

}
