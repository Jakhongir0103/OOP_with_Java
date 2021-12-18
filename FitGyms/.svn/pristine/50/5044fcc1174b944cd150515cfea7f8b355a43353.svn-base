package fit;
import java.util.*;
import java.util.stream.Collectors;


public class Fit {
    
    public static int MONDAY = 1;
    public static int TUESDAY = 2;
    public static int WEDNESDAY = 3;
    public static int THURSDAY = 4;
    public static int FRIDAY = 5;
    public static int SATURDAY = 6;
    public static int SUNDAY = 7;
    
	private int CustomerN = 0;
	private SortedMap<Integer, Customer> customers = new TreeMap<>();
	private ArrayList<Gymn> gymns = new ArrayList<>();
	
	
	public Fit() {
	
	}
	// R1 
	
	public void addGymn (String name) throws FitException {
		Gymn g = new Gymn(name);
		boolean b = gymns.contains(g);
		if (b == true) {
			throw new FitException ("Duplicated gymn");	
		}
		gymns.add(g);
	}
	
	public int getNumGymns() {
		return gymns.size();
	}
	
	//R2

	public void addLessons (String gymnname, String activity, int maxattendees, String slots, String ...allowedinstructors) throws FitException
	{
		
		String slotarray[]=slots.split(",");
        for (String s : slotarray) { 
        	String slot[]=s.split("\\.");
        	int day = Integer.parseInt(slot[0]);
        	int timeslot = Integer.parseInt(slot[1]);
        	if (day < 1 || day > 7 || timeslot < 8 || timeslot > 21) {
        		throw new FitException("Wrong day or time");
        	} else {
        		int i = gymns.indexOf(new Gymn(gymnname));
				if (i == -1) {
					throw new FitException("Gymn not found");
				} else {
					Lesson l = new Lesson (day, timeslot, activity, maxattendees, allowedinstructors);
					gymns.get(i).addLesson(l);
				}
        	}
        }
	}
	
	//R3
	public int addCustomer(String name) {
		CustomerN++; 
		customers.put(CustomerN, new Customer(CustomerN, name));
		return CustomerN;
	}
	
	public String getCustomer (int customerid) throws FitException{
		Customer c = customers.get(customerid);
		if (c == null) {
			throw new FitException("Customerid not found");
		} else {
			return customers.get(customerid).getName();
		}
	}
	
	//R4
	
	public void placeReservation(int customerid, String gymnname, int day, int slot) throws FitException{
		if (gymns.contains(new Gymn(gymnname))) {
			if (customers.containsKey(customerid)) {
				if (day >= 1 && day <= 7 && slot >= 8 && slot <= 21) {
					int i = gymns.indexOf(new Gymn(gymnname));
					gymns.get(i).getLesson(day, slot).addReservation(customers.get(customerid));
				} else {
					throw new FitException("Invalid DayTime");
				}
			} else {
				throw new FitException("Customer not found");
			}
		} else {
			throw new FitException("Gymn not found");
		}
	}
	
	public int getNumLessons(int customerid) {
		return gymns.stream().mapToInt(p -> p.getNumReservation(customerid)).sum();
	}
	
	
	//R5
	
	public void addLessonGiven (String gymnname, int day, int slot, String instructor) throws FitException{
		if (gymns.contains(new Gymn(gymnname))) {
				if (day >= 1 && day <= 7 & slot >= 8 && slot <= 21) {
					int i = gymns.indexOf(new Gymn(gymnname));
					gymns.get(i).getLesson(day, slot).addLessonGiven(instructor);
				} else {
					throw new FitException("Invalid DayTime");
				}
		} else {
			throw new FitException("Gymn not found");
		}
	}
	
	public int getNumLessonsGiven (String gymnname, String instructor) throws FitException{
		if (gymns.contains(new Gymn(gymnname))) {
				int i = gymns.indexOf(new Gymn(gymnname));
				return gymns.get(i).getNumLessonsGiven(instructor);
	} else {
		throw new FitException("Gymn not found");
	}
	}
	//R6
	
	public String mostActiveGymn() {
		Gymn g = gymns.stream().max((g1,g2) -> Integer.compare(g1.getNumLesson(), g2.getNumLesson())).get();
		return g.getName();
	}
	
	public Map<String, Integer> totalLessonsPerGymn() {		
		return gymns.stream().collect(Collectors.toMap(Gymn::getName, Gymn::getNumLesson));
	}
	
	public SortedMap<Integer, List<String>> slotsPerNofParticipants(String gymnname) throws FitException{
		if (gymns.contains(new Gymn(gymnname))) {
			int i = gymns.indexOf(new Gymn(gymnname));
			return gymns.get(i).slotsPerNofParticipants();
        } else {
        	throw new FitException("Gymn not found");
        }
	}

}
