package discounts;

import java.util.*;
class Purchase {
	private List<Line> lines = new ArrayList<>();
	void addLine(Line l) {lines.add(l);}
	private double purchaseAmount = 0;
	private double purchaseDiscount = 0;
	int id;
	
	void setId(int id) {this.id = id;}
	
	
	double getPurchaseAmount() {return purchaseAmount;}
	double getPurchaseDiscount() {return purchaseDiscount;}
	
	private double getAmountWithDiscounts() {
		double amount = 0;
		for (Line l: lines) {
			Product product = l.getProduct();
			amount += l.getnOfUnits() * (product.getPrice() * 
					(1 - product.getCategory().getPercentage()/100.0));		
		}
		return amount;
	}
	
	private double getAmount() {
		double amount = 0;
		for (Line l: lines) {
			Product product = l.getProduct();
			amount += l.getnOfUnits() * product.getPrice();	
		}
		return amount;
	}
	
	void setAmount(boolean yesDiscount) {
		if (!yesDiscount) {
			purchaseAmount = getAmount();
			purchaseDiscount = 0;
		} else {
			purchaseAmount = getAmountWithDiscounts();
			purchaseDiscount = getAmount() - purchaseAmount;
		}
	}
	
	int getNofUnits() {
		return lines.stream().mapToInt(Line::getnOfUnits).sum();
	}
}
