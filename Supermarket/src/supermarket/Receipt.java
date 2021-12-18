package supermarket;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
	private int code;
	private List<String> products = new ArrayList<>();
	private int card;
	private double total;
	private int points = 0;
	public Receipt(int code) {
		super();
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	public List<String >getProducts() {
		return products;
	}
	public int getCard() {
		return card;
	}
	public void setProduct(String product) {
		this.products.add(product);
	}
	public void setCard(int card) {
		this.card = card;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
}
