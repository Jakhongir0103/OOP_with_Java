package it.polito.oop.production;

import java.util.HashMap;
import java.util.Map;

public class Factory {
	private String name;

	public Factory(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	private Map<String, Lines> lines = new HashMap<>();
	
	public void addLine(String lName, Integer nCars, Integer mot) {
		lines.put(lName, new Lines(lName, nCars, mot));
	}

	public Map<String, Lines> getLines() {
		return lines;
	}
	
	
}
