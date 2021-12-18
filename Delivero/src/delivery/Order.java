package delivery;

public class Order {
	private String dishNames[];
	private int quantities[]; 
	private String customerName;
	private String restaurantName;
	private int deliveryTime;
	private int deliveryDistance;
	private boolean assigned = false;
	
	public Order(String[] dishNames, int[] quantities, String customerName, String restaurantName, int deliveryTime,
			int deliveryDistance) {
		super();
		this.dishNames = dishNames;
		this.quantities = quantities;
		this.customerName = customerName;
		this.restaurantName = restaurantName;
		this.deliveryTime = deliveryTime;
		this.deliveryDistance = deliveryDistance;
	}

	public String[] getDishNames() {
		return dishNames;
	}

	public int[] getQuantities() {
		return quantities;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public int getDeliveryDistance() {
		return deliveryDistance;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
}
