package me.ericbarber.sales;
/**
 * This is a test Java application interface for all ItemOperations and DatbaseConnections. Use this file to test certain aspects
 * of the Item Operations class prior to use within a web service or JSP page (or full fledged Java application).
 * 
 * @author Eric Barber
 * @version 0.1
 */

//Needed to generate XML
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Marshaller;


public class SalesTestInterface {

/*//Path for generated XML from an Array List <Item> e.g. generating an XML file of all approved items in an Array List.
	private static final String ITEMS_XML = "./items-jaxb.xml";
*/
	
	public static void main(String[] args) throws Exception {
		
		//ItemOperations contains most of the useful methods e.g. generateAllItems, changeItemStatus, etc.
		ItemOperations itemops = new ItemOperations();
		itemops.changeItemStatus(true, 2);
		//itemops.changeItemStatus(true, 1);
		//itemops.changeItemStatus(false, 1);
		//itemops.voteItem(2,"Up");
		///itemops.voteItem(1, "DOWN");
		//itemops.deleteSpecificItem(823);
		//itemops.insertNewItem("New Title Unapproved", "www.newurl.com", "www.newurl.com/img/thumb.gif", "A total unique and cool item!");	
		
		// saleItems is an empty ArrayList that will accept methods to generate items.
		//Items saleItems = new Items();
		//saleItems.setItems(itemops.generateAllItems());
		//saleItems.setItems(itemops.generateApprovedItems());
		
		// randomItem is a test Item object for the getRandomItem method.
		//Item randomItem = new Item();
		//randomItem = itemops.getRandomItem();
		//System.out.println(randomItem.getTitle());
		
		// specificItem is a test Item object for the getSpecificItem method.
		//Item specificItem = new Item();
		//specificItem = itemops.getSpecificItem(2);
		//System.out.println(specificItem.getUpVote());
		//System.out.println(specificItem.getTitle());
		
		//The following is testing the XML generation using JAXB from the ArrayList<Item>s.
		//JAXBContext context = JAXBContext.newInstance(Items.class);
		//Marshaller m = context.createMarshaller();
		//m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		//Outputs XML to console.
		//m.marshal(saleItems, System.out);
		
		//Outputs XML to an external file.
		//m.marshal(saleItems, new File(ITEMS_XML));
		
	}
}
