package me.ericbarber.jaxb.test;

//import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class SQLtoXMLTest {
//	private static final String ITEMS_XML = "./items-jaxb.xml";
	public static void main(String[] args) throws Exception {
		
		itemOperations itemops = new itemOperations();
		items saleItems = new items();
		
		//item randomItem = new item();
		//item specificItem = new item();
		//itemops.changeItemStatus(true, 3);
		//itemops.changeItemStatus(false, 1);
		//itemops.voteItemUp(2);
		//itemops.voteItemDown(1);
		saleItems.setItems(itemops.generateAllItems());
		//saleItems.setItems(itemops.generateApprovedItems());
		
		JAXBContext context = JAXBContext.newInstance(items.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		m.marshal(saleItems, System.out);
		
		//m.marshal(saleItems, new File(ITEMS_XML));
		//itemops.deleteSpecificItem(9);
		//itemops.insertNewItem("New Title Unapproved", "www.newurl.com", "www.newurl.com/img/thumb.gif", "A total unique and cool item!");
		//specificItem = itemops.getSpecificItem(1);
		//randomItem = itemops.getRandomItem();
		//System.out.println(specificItem.getTitle());
		//System.out.println(randomItem.getTitle());
		
	}

}
