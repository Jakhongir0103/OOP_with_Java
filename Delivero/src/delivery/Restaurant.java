package delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Restaurant {
	private String name;
	private List<Integer> rating = new ArrayList<>();;
	
	public Restaurant(String name, int rating) {
		super();
		this.name = name;
		this.rating.add(rating);
	}
	
	public String getName() {
		return name;
	}
	public List<Integer> getRating() {
		return rating;
	}
	public double getAverage() {
		return this.rating.stream().collect(Collectors.averagingLong(r -> r));
	}
}
