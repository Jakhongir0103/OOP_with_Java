package it.polito.oop.production;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
	private String name;
	private int capacity;
	public Warehouse(String name, int capacity) {
		super();
		this.name = name;
		this.capacity = capacity;
	}
	public String getName() {
		return name;
	}
	public int getCapacity() {
		return capacity;
	}	
	
	private List<String> carsParked = new ArrayList<>();
	public List<String> getCarsParked() {
		return carsParked;
	}
	public void setCarsParked(List<String> carsParked) {
		this.carsParked = carsParked;
	}
	public void parkCar(String model) {
		this.carsParked.add(model);
	}
	public void removeCar(String model) {
		this.carsParked.remove(model);
	}
}
