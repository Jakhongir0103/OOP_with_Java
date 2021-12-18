package it.polito.oop.vaccination;

public class Person {
	private String first;
	private String lastName;
	private String ssn;
	private int year;
	/*the lines 9, 29-34 have been used to implement R5, which I have not done during the exam*/
	private boolean allocated = false;
	public Person(String first, String lastName, String ssn, int year) {
		super();
		this.first = first;
		this.lastName = lastName;
		this.ssn = ssn;
		this.year = year;
	}
	public String getFirst() {
		return first;
	}
	public String getLastName() {
		return lastName;
	}
	public String getSsn() {
		return ssn;
	}
	public int getYear() {
		return year;
	}
	public boolean isAllocated() {
		return allocated;
	}
	public void setAllocated(boolean allocated) {
		this.allocated = allocated;
	}
}
