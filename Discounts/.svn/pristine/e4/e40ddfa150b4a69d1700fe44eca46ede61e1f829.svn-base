package discounts;
import java.util.*;
class Category {
	String id;
	private int percentage = 0;
	private List<Product> products = new ArrayList<>();
	
	Category (String id) {this.id = id;}
	void addProduct(Product p) {products.add(p);}
	
	void setPercentage(int p) {this.percentage = p;}
	int getPercentage() {return percentage;}
	
	double getAveragePrice() {return 
		products.stream().mapToDouble(Product::getPrice).average().getAsDouble();
	}
}
