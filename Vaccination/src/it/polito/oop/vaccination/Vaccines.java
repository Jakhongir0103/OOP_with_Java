package it.polito.oop.vaccination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Vaccines {

    public final static int CURRENT_YEAR = java.time.LocalDate.now().getYear();
    public final static int MAX = 100000;

    // R1
    /**
     * Add a new person to the vaccination system.
     *
     * Persons are uniquely identified by SSN (italian "codice fiscale")
     *
     * @param first first name
     * @param lastName last name
     * @param ssn italian "codice fiscale"
     * @param year birth year
     * @return {@code false} if ssn is duplicate,
     */
    private Map<String, Person> persons = new HashMap<>();
    public boolean addPerson(String first, String lastName, String ssn, int year) {
    	if(persons.containsKey(ssn)) {
    		return false;
    	}
    	persons.put(ssn, new Person(first, lastName, ssn, year));
        return true;
    }

    /**
     * Count the number of people added to the system
     *
     * @return person count
     */
    public int countPeople() {
        return persons.keySet().size();
    }

    /**
     * Retrieves information about a person.
     * Information is formatted as ssn, last name, and first name
     * separate by {@code ','} (comma).
     *
     * @param ssn "codice fiscale" of person searched
     * @return info about the person
     */
    public String getPerson(String ssn) {
        return persons.values().stream().filter(p -> p.getSsn().equals(ssn)).findFirst().map(p -> p.getSsn() + ", " + p.getLastName() + ", " + p.getFirst()).get();
    }

    /**
     * Retrieves of a person given their SSN (codice fiscale).
     *
     * @param ssn "codice fiscale" of person searched
     * @return age of person (in years)
     */
    public int getAge(String ssn) {
        return persons.values().stream().filter(p -> p.getSsn().equals(ssn)).findFirst().map(p -> CURRENT_YEAR - p.getYear()).get();
    }

    /**
     * Define the age intervals by providing the breaks between intervals.
     * The first interval always start at 0 (non included in the breaks)
     * and the last interval goes until infinity (not included in the breaks).
     * All intervals are closed on the lower boundary and open at the upper one.
     * <p>
     * For instance {@code setAgeIntervals(40,50,60)}
     * defines four intervals {@code "[0,40)", "[40,50)", "[50,60)", "[60,+)"}.
     *
     * @param brks the array of breaks
     */
    private List<Intervals> intervals = new ArrayList<>();
    public void setAgeIntervals(int... brks) {
		intervals.add(new Intervals(0, brks[0]));
    	for(int i = 1; i < brks.length; i++) {
    		intervals.add(new Intervals(brks[i - 1], brks[i]));
    	}
		intervals.add(new Intervals(brks[brks.length - 1], MAX));
    }

    /**
     * Retrieves the labels of the age intervals defined.
     *
     * Interval labels are formatted as {@code "[0,10)"},
     * if the upper limit is infinity {@code '+'} is used
     * instead of the number.
     *
     * @return labels of the age intervals
     */
    public Collection<String> getAgeIntervals() {
        List<String> res = new ArrayList<>();
        for(Intervals i: intervals) {
        	int min = i.getMin();
        	int max = i.getMax();
        	if(max == MAX) {
        		res.add("[" + min + ",+)");
        	}
        	else {
        		res.add("[" + min + "," + max + ")");
        	}
        }
    	return res;
    }

    /**
     * Retrieves people in the given interval.
     *
     * The age of the person is computed by subtracting
     * the birth year from current year.
     *
     * @param range age interval label
     * @return collection of SSN of person in the age interval
     */
    public Collection<String> getInInterval(String range) {
    	int min = Integer.parseInt(range.split(",")[0].split("\\[")[1]);
    	int max;
    	if(!range.split(",")[1].split("\\)")[0].equals("+")) {
        	max = Integer.parseInt(range.split(",")[1].split("\\)")[0]);
    	}else {
    		max = MAX;
    	}
        return persons.values().stream().filter(p -> CURRENT_YEAR - p.getYear() >= min && CURRENT_YEAR - p.getYear() < max).map(p -> p.getSsn()).collect(Collectors.toList());
    }
    
    // R2
    /**
     * Define a vaccination hub
     *
     * @param name name of the hub
     * @throws VaccineException in case of duplicate name
     */
    private Map<String, Hub> hubs = new HashMap<>();
    public void defineHub(String name) throws VaccineException {
    	if(hubs.containsKey(name)) {
    		throw new VaccineException();
    	}
    	hubs.put(name, new Hub(name));
    }

    /**
     * Retrieves hub names
     *
     * @return hub names
     */
    public Collection<String> getHubs() {
        return hubs.keySet();
    }

    /**
     * Define the staffing of a hub in terms of
     * doctors, nurses and other personnel.
     *
     * @param name name of the hub
     * @param countDoctors number of doctors
     * @param nurses number of nurses
     * @param o number of other personnel
     * @throws VaccineException in case of undefined hub, or any number of personnel not greater than 0.
     */
    public void setStaff(String name, int countDoctors, int nurses, int o) throws VaccineException {
    	if(!hubs.containsKey(name)) {
    		throw new VaccineException();
    	}
    	if(countDoctors <= 0) {
    		throw new VaccineException();
    	}
    	hubs.get(name).setStaff(countDoctors, nurses, o);    	
    }

    /**
     * Estimates the hourly vaccination capacity of a hub
     *
     * The capacity is computed as the minimum among
     * 10*number_doctor, 12*number_nurses, 20*number_other
     *
     * @param hub name of the hub
     * @return hourly vaccination capacity
     * @throws VaccineException in case of undefined or hub without staff
     */
    public int estimateHourlyCapacity(String hub) throws VaccineException {
    	if(!hubs.containsKey(hub)) {
    		throw new VaccineException();
    	}
    	if(hubs.get(hub).getStaff() == null) {
    		throw new VaccineException();
    	}
    	int min1 = hubs.get(hub).getStaff().getCountDoctors() * 10;
    	int min2 = hubs.get(hub).getStaff().getNurses() * 12;
    	int min3 = hubs.get(hub).getStaff().getO() * 20;
    	
    	int res = Integer.min(min1, min2);
    	
        return Integer.min(res, min3);
    }

    // R3
    /**
     * Load people information stored in CSV format.
     *
     * The header must start with {@code "SSN,LAST,FIRST"}.
     * All lines must have at least three elements.
     *
     * In case of error in a person line the line is skipped.
     *
     * @param people {@code Reader} for the CSV content
     * @return number of correctly added people
     * @throws IOException in case of IO error
     * @throws VaccineException in case of error in the header
     */
    public long loadPeople(Reader people) throws IOException, VaccineException {
    	int count = 0;
    	int lineNum = 1;
		try {
	        BufferedReader br = new BufferedReader(people);
	        String[] line = br.readLine().split(",");
	        if(!(line.length == 4 && (line[0].equals("SSN") && line[1].equals("LAST") && line[2].equals("FIRST") && line[3].equals("YEAR")))) {
	        	if(listener != null) {
	        		listener.accept(lineNum, line[0] + "," + line[1] + "," + line[2] + "," + line[3]);
	        	}else{
	        		throw new VaccineException();
	        	}
	        }
	        String lines = br.readLine();
	        while(lines != null) {
	        	lineNum++;
	        	String[] line2 = lines.split(",");
	        	if(lines.split(",").length == 4 && this.addPerson(line2[2], line2[1], line2[0], Integer.parseInt(line2[3])) == true) {
	        		count++;
	        	}else if(listener != null){
	            	listener.accept(lineNum, lines);
	        	}
	            lines = br.readLine();
	        }
	        br.close();
		}catch(IOException e) {
			System.out.println("An error occured!");
		}
        return count;
    }

    // R4
    /**
     * Define the amount of working hours for the days of the week.
     *
     * Exactly 7 elements are expected, where the first one correspond to Monday.
     *
     * @param h workings hours for the 7 days.
     * @throws VaccineException if there are not exactly 7 elements or if the sum of all hours is less than 0 ore greater than 24*7.
     */
    private Integer[] hours = new Integer[7];
    public void setHours(int... h) throws VaccineException {
    	if(h.length != 7) {
        	throw new VaccineException();
    	}
    	for(int i: h) {
    		if(i > 12) {
            	throw new VaccineException();
    		}
    	}
    	for(int i = 0; i < 7; i++) {
    		hours[i] = h[i];
    	}	
    }

    /**
     * Returns the list of standard time slots for all the days of the week.
     *
     * Time slots start at 9:00 and occur every 15 minutes (4 per hour) and
     * they cover the number of working hours defined through method {@link #setHours}.
     * <p>
     * Times are formatted as {@code "09:00"} with both minuts and hours on two
     * digits filled with leading 0.
     * <p>
     * Returns a list with 7 elements, each with the time slots of the corresponding day of the week.
     *
     * @return the list hours for each day of the week
     */
    public List<List<String>> getHours() {
    	List<List<String>> res = new ArrayList<>();
    	for(int day = 0; day < 7; day++) {
    		List<String> days = new ArrayList<>();
    		for(int h = 9; h < 9 + hours[day]; h++) {
        		for(int m = 0; m < 60; m += 15) {
        			days.add(String.format("%02d:%02d",h , m));
        		}
        	}
    		res.add(days);
    	}
        return res;
    }

    /**
     * Compute the available vaccination slots for a given hub on a given day of the week
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the hourly capacity (see {@link #estimateCapacity} of the hub.
     *
     * @return
     * @throws VaccineException 
     */
    public int getDailyAvailable(String hub, int day) {
    	int min = 0;
    	try {min = estimateHourlyCapacity(hub);} catch(Exception e) {}
    	return min * this.hours[day];
    }

    /**
     * Compute the available vaccination slots for each hub and for each day of the week
     * <p>
     * The method returns a map that associates the hub names (keys) to the lists
     * of number of available hours for the 7 days.
     * <p>
     * The availability is computed as the number of working hours of that day
     * multiplied by the capacity (see {@link #estimateCapacity} of the hub.
     *
     * @return
     */
    public Map<String, List<Integer>> getAvailable() {
    	Map<String, List<Integer>> result = new HashMap<>();
    	for(String h: this.hubs.keySet()) {
    		result.put(h, new ArrayList<>());
    	}
    	for(String k: result.keySet()) {
    		for(int day = 0; day < 7; day++) {
        		result.get(k).add(getDailyAvailable(k, day));
    		}
    	}
        return result;
    }

    /**
     * Computes the general allocation plan a hub on a given day.
     * Starting with the oldest age intervals 40%
     * of available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is the list of SSNs (codice fiscale) of the
     * persons allocated to that day
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     *
     * @param hub name of the hub
     * @param day day of week index (0 = Monday)
     * @return the list of daily allocations
     */
    public List<String> allocate(String hub, int day) {
    	List<String> allocatedPeople = new ArrayList<>();
    	int n = getDailyAvailable(hub, day);
    	int notAllocatedSize;
    	List<String> ageIntervals = List.copyOf(getAgeIntervals());
    	for(double ratio = 0.4; ratio <= 1; ratio += 0.6) {
	    	for(int i = ageIntervals.size() - 1; i >= 0 && n != 0; i--) {
	    		List<String> notAllocatedPeopleSSN = getInInterval(ageIntervals.get(i)).stream()
	    														.filter(p -> persons.get(p).isAllocated() == false)
	    														.collect(Collectors.toList());
	    		if(n * ratio < notAllocatedPeopleSSN.size()) {
	    			notAllocatedSize = (int) (n * ratio);
	    		}else {
	    			notAllocatedSize = notAllocatedPeopleSSN.size();
	    		}
				for(int j = 0; j < notAllocatedSize; j++) {
					hubs.get(hub).allocatePeopleForDay(day, persons.get(notAllocatedPeopleSSN.get(j)));
					persons.get(notAllocatedPeopleSSN.get(j)).setAllocated(true);
					allocatedPeople.add(notAllocatedPeopleSSN.get(j));
				}
				n -= notAllocatedSize;
	    	}    		
    	}
        return allocatedPeople;
    }

    /**
     * Removes all people from allocation lists and
     * clears their allocation status
     */
    public void clearAllocation() {
    	persons.values().stream().filter(p -> p.isAllocated() == true).forEach(p -> {
    		p.setAllocated(false);
    	});
    }

    /**
     * Computes the general allocation plan for the week.
     * For every day, starting with the oldest age intervals
     * 40% available places are allocated
     * to persons in that interval before moving the the next
     * interval and considering the remaining places.
     * <p>
     * The returned value is a list with 7 elements, one
     * for every day of the week, each element is a map that
     * links the name of each hub to the list of SSNs (codice fiscale)
     * of the persons allocated to that day in that hub
     * <p>
     * <b>N.B.</b> no particular order of allocation is guaranteed
     * but the same invocation (after {@link #clearAllocation}) must return the same
     * allocation.
     *
     * @return the list of daily allocations
     */
    public List<Map<String, List<String>>> weekAllocate() {
    	List<Map<String, List<String>>> result = new ArrayList<>();
    	for(int day = 0; day < 7; day++) {
	    	Map<String, List<String>> map = new HashMap<>();
	    	for(Hub h: hubs.values()){
		    	this.allocate(h.getName(), day);
		    	map.put(h.getName(), h.getAllocatePeopleForDay(day));
		    }
	    	result.add(map);
    	}
    	return result;
    }

    // R5
    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system
     *
     * @return proportion of allocated people
     */
    public double propAllocated() {
    	return persons.values().stream().filter(p -> p.isAllocated() == true).count() / (double) persons.values().size();
    }

    /**
     * Returns the proportion of allocated people
     * w.r.t. the total number of persons added
     * in the system, divided by age interval.
     * <p>
     * The map associates the age interval label
     * to the proportion of allocates people in that interval
     *
     * @return proportion of allocated people by age interval
     */
    public Map<String, Double> propAllocatedAge() {
    	Map<String, Double> res = new HashMap<>();;
    	
    	for(String intvl: getAgeIntervals()) {
			Collection<String> interval = getInInterval(intvl);
    		double ratio = persons.values().stream().filter(p -> p.isAllocated() == true && interval.contains((p.getSsn())) == true).count() / (double) persons.values().stream().count(); 
    		res.put(intvl, ratio);
    	}
        return res;
    }

    /**
     * Retrieves the distribution of allocated persons
     * among the different age intervals.
     * <p>
     * For each age intervals the map reports the
     * proportion of allocated persons in the corresponding
     * interval w.r.t the total number of allocated persons
     *
     * @return
     */
    public Map<String, Double> distributionAllocated() {
    	Map<String, Double> res = new HashMap<>();;
    	
    	System.out.println(getAgeIntervals().size());
		for(String intvl: getAgeIntervals()) {
			Collection<String> interval = getInInterval(intvl);
    		double ratio = persons.values().stream().filter(p -> p.isAllocated() == true && interval.contains((p.getSsn())) == true).count() / (double) persons.values().stream().filter(p -> p.isAllocated() == true).count();
    		res.put(intvl, ratio);
    	}
        return res;
    }

    // R6
    /**
     * Defines a listener for the file loading method.
     * The {@ accept()} method of the listener is called
     * passing the line number and the offending line.
     * <p>
     * Lines start at 1 with the header line.
     *
     * @param listener the listener for load errors
     */
    private BiConsumer<Integer, String> listener;
    public void setLoadListener(BiConsumer<Integer, String> listener) {
    	this.listener = listener;
    }
}
