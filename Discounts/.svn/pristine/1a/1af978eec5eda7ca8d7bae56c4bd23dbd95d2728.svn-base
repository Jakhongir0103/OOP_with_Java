package discounts;

import java.util.ArrayList;
import java.util.List;

class Product {
	private String id;
	private double price;
	private Category category;
	List<Line> lines = new ArrayList<>();
	
	String getId() {return id;}
	void addLine(Line l) {lines.add(l);}
	double getPrice() {return price;}
	Category getCategory() {return category;}
	
	Product(String productId, double price, Category category) {
		id = productId; this.price = price;
		this.category = category;
	}
	
	int getNofUnits() {
		return lines.stream().mapToInt(Line::getnOfUnits).sum();
	}
}
