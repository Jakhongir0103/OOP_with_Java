package discounts;
import java.util.*;

class Card {
	private int id = 0;
	private List<Purchase> purchases = new ArrayList<>();
    private String name;
	
	int getNofPurchases() {return purchases.size();}
	void addPurchase(Purchase p) {purchases.add(p);}
	int getId() {return id;}
	
	Card(int id, String name) {
	    this.id = id;
	    this.name=name;
	}
	
	String getName() { return name; }
	
	int getTotalPurchase() {
		return (int) Math.round(purchases.stream().mapToDouble(Purchase::getPurchaseAmount).sum());
		//if (id != 0) return (int) Math.round(purchases.stream().mapToDouble(Purchase::getAmoundDiscounted).sum());
		//else return (int) Math.round(purchases.stream().mapToDouble(Purchase::getAmoundNotDiscounted).sum());
	}
	
	int getTotalDiscount() {
		if (id == 0) return 0;
		else 
			return (int) Math.round(purchases.stream().mapToDouble(Purchase::getPurchaseDiscount).sum());
	}
}
