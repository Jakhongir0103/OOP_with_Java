package it.polito.oop.production;

public class Car {
	private String code;
	private String name;
	private int year;
	private float displacement;
	private int enginetype;
	public Car(String code, String name, int year, float displacement, int enginetype) {
		super();
		this.code = code;
		this.name = name;
		this.year = year;
		this.displacement = displacement;
		this.enginetype = enginetype;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public int getYear() {
		return year;
	}
	public float getDisplacement() {
		return displacement;
	}
	public int getEnginetype() {
		return enginetype;
	}	
}
