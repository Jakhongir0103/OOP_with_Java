package fit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Lesson {
	private int day;
	private int timeslot;
	private String activity;
	private int maxattendees;
	private String instructorname=null;
	private String[] allowedinstructors;
	private List<Customer> attendees = new ArrayList<>();
		
	Lesson(int dayofweek, int timeslot, String activity, int maxattendees, String instructornames[]) {
		this.day = dayofweek; 
		this.timeslot = timeslot;
		this.activity = activity;
		this.maxattendees = maxattendees;
		this.allowedinstructors = instructornames;
	}
	
	public int getNumAttendees() {
		return attendees.size();
	}
	public int getDay() {
		return day;
	}

	public int getTimeslot() {
		return timeslot;
	}

	public String getActivity() {
		return activity;
	}
	
	public String getInstructor() {
		return instructorname;
	}
	
	public boolean isReserved(int customerid) {
		return attendees.stream().anyMatch(t -> {return t.getId()==customerid;});
	}
	
	public void addReservation (Customer c) throws FitException {
		if (attendees.size() < maxattendees) {
				if (attendees.contains(c)) {
					throw new FitException("Customer already registered");
				} else {
					attendees.add(c);
				}
		} else {
			throw new FitException ("Class full");
		}
	}
	
	public void addLessonGiven (String instructor) throws FitException {
		if (!Arrays.asList(allowedinstructors).contains(instructor)) {
			throw new FitException("Instructor not allowed");
		} else {
			this.instructorname=instructor;
		}
	}
	
	

	
	
	
}
