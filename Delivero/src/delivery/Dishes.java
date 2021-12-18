package delivery;

public class Dishes {
	private String name;
	private String restaurantName;
	private float price;
	
	public Dishes(String name, String restaurantName, float price) {
		super();
		this.name = name;
		this.restaurantName = restaurantName;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public float getPrice() {
		return price;
	}
}