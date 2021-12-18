package it.polito.oop.vaccination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hub {
	private String name;
	private Staff staff;
	/*the lines 13, 28-49 have been used to implement R5, which I have not done during the exam*/
	private Map<Integer, List<Person>> allocatedPeoplePerDay = new HashMap<>();  
	public Hub(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}	
	public void setStaff(int countDoctors, int nurses, int o) {
		this.staff = new Staff(countDoctors, nurses, o);
	}
	public Staff getStaff() {
		return staff;
	}

	public List<String> getAllocatePeopleForDay(int day) {
		if(this.allocatedPeoplePerDay.isEmpty()) {
			return null;
		}
		if(!this.allocatedPeoplePerDay.containsKey(day)) {
			return null;
		}
		return this.allocatedPeoplePerDay.get(day).stream()
												  .map(p -> p.getSsn())
												  .collect(Collectors.toList());
	}
	public void clearAllocation() {
		this.allocatedPeoplePerDay = new HashMap<>();
	}
	public void allocatePeopleForDay(int day, Person person) {
		if(this.allocatedPeoplePerDay.containsKey(day)) {
			this.allocatedPeoplePerDay.get(day).add(person);
		}else {
			this.allocatedPeoplePerDay.put(day, new ArrayList<>());
			this.allocatedPeoplePerDay.get(day).add(person);
		}
	}
}
