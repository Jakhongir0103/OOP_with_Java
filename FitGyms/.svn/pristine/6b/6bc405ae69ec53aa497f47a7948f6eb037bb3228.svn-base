package fit;

//import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Gymn  {
	private String name;
	private SortedMap<String, Lesson> lessons = new TreeMap<>();

	public Gymn (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumLesson() {
		return lessons.size();
	}
	
	public int getNumReservation(int customerid) {
		return (int) lessons.values().stream().filter(p -> p.isReserved(customerid)).count();
	}
	
	public void addLesson (Lesson l) throws FitException {
		if (lessons.containsKey(l.getDay()+"."+l.getTimeslot())) {
			throw new FitException ("Slot already in use");	
		}
		lessons.put(l.getDay()+"."+l.getTimeslot(),l);
	}
	
    @Override
	public boolean equals (Object o) {
    	Gymn g = (Gymn) o;
		return g.name.equals(name);
	}
    
    public Lesson getLesson (int day, int slot) throws FitException {
    	Lesson l = lessons.get(day+"."+slot);
    	if (l == null) {
    		throw new FitException ("Lesson not found");
    	} else {
    		return l;
    	}
    }
    
    public int getNumLessonsGiven(String instructor) {
    	return (int) lessons.
    			values().
    			stream().
    			map(p -> p.getInstructor()).
    			filter(p -> {if (p!=null) return p.equals(instructor); else return false;}).
    			count();
    }
    
    public SortedMap<Integer, List<String>> slotsPerNofParticipants () {
    	
        return lessons.entrySet().stream().
                collect(Collectors.groupingBy( e -> e.getValue().getNumAttendees(), 
                        TreeMap::new,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        // As an alternative, without using streams:
        //
//    	TreeMap<Integer, List<String>> r = new TreeMap<Integer, List<String>>();
//    	
//		Iterator<String> iterator = lessons.keySet().iterator();
//		while(iterator.hasNext()) {
//			String key   = (String) iterator.next();
//			Lesson value = (Lesson) lessons.get(key);
//			if (r.containsKey(value.getNumAttendees())) {
//				r.get(value.getNumAttendees()).add(value.getDay()+"."+value.getTimeslot());
//			} else {
//				List<String> l = new ArrayList<String>();
//				l.add(value.getDay()+"."+value.getTimeslot());
//				r.put(value.getNumAttendees(), l);
//			}
//		}
//		
//		return r;
    }
	
}
