//PRODUCTLOOKUP.JAVA 


import java.util.Iterator;

public class ProductLookup {
	DictionaryADT<String,StockItem> dictionary;


	// Constructor.  There is no argument-less constructor, or default size
	public ProductLookup(int maxSize){
		dictionary = 
				new Hashtable<String,StockItem>(maxSize); 
//				new BinarySearchTree<String,StockItem>();
//				new BalancedTree<String,StockItem>();
	}

	// Adds a new StockItem to the dictionary
	public void addItem(String SKU, StockItem item){
		dictionary.insert(SKU, item);
	}

	// Returns the StockItem associated with the given SKU, if it is
	// in the ProductLookup, null if it is not.
	public StockItem getItem(String SKU){
		if(dictionary.getValue(SKU)==null)
			return  null;
		return (StockItem) dictionary.getValue(SKU);
	}

	// Returns the retail price associated with the given SKU value.
	// -.01 if the item is not in the dictionary
	public float getRetail(String SKU){
		if(dictionary.getValue(SKU)==null)
			return  -.01f;
		return dictionary.getValue(SKU).getRetail();
	}

	// Returns the cost price associated with the given SKU value.
	// -.01 if the item is not in the dictionary
	public float getCost(String SKU) {
		if(dictionary.getValue(SKU)==null)
			return  -.01f;
		return dictionary.getValue(SKU).getCost();
	}

	// Returns the description of the item, null if not in the dictionary.
	public String getDescription(String SKU){
		if(dictionary.getValue(SKU)==null)
			return  null;
		return dictionary.getValue(SKU).getDescription();
	}


	// Deletes the StockItem associated with the SKU if it is
	// in the ProductLookup.  Returns true if it was found and
	// deleted, otherwise false.  
	public boolean deleteItem(String SKU){
		return dictionary.remove(SKU);
	}

	// Prints a directory of all StockItems with their associated
	// price, in sorted order (ordered by SKU).
	public void printAll(){
		Iterator<StockItem> stockIterator = this.values();
		while(stockIterator.hasNext()){
			System.out.println(stockIterator.next().toString());
		}
	}

	// Prints a directory of all StockItems from the given vendor, 
	// in sorted order (ordered by SKU).
	public void print(String vendor){
		Iterator<StockItem> stockIterator = this.values();
		while(stockIterator.hasNext()){
			StockItem item = stockIterator.next();
			if(item.getVendor().equals(vendor))
				System.out.println(item.toString());
		}
	}

	// An iterator of the SKU keys.
	public Iterator<String> keys(){
		return dictionary.keys();
	}

	// An iterator of the StockItem values.    
	public Iterator<StockItem> values(){
		return dictionary.values();
	}
}