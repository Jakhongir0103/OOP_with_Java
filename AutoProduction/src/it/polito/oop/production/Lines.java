package it.polito.oop.production;

import java.util.ArrayList;
import java.util.List;

public class Lines {
	private String lName;
	private Integer nCars;
	private Integer mot;
	public Lines(String lName, Integer nCars, Integer mot) {
		super();
		this.lName = lName;
		this.nCars = nCars;
		this.mot = mot;
	}
	public String getlName() {
		return lName;
	}
	public Integer getnCars() {
		return nCars;
	}
	public Integer getMot() {
		return mot;
	}
	
	private List<String> allocatedCars = new ArrayList<>();;
	public void allocateCar(String model) {
		allocatedCars.add(model);
	}
	public List<String> getAllocatedCars(){
		return allocatedCars;
	}
}
