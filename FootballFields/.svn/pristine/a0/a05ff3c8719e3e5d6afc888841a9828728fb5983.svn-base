package it.polito.oop.futsal;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import static java.util.stream.Collectors.*;

import it.polito.oop.futsal.Fields.Features;

public class Field implements FieldOption {
    
    private class Booking{
        public Booking(Associate p2, String time2) {
            this.a = p2;
            this.time = time2;
        }
        Associate a;
        String time;
    }
    
    private int number;
    private Features features;
    private Map<String,Booking> bookings = new TreeMap<>();
    private int cumDiff;
    private long countServed;

    public Field(int n, Features features) {
        this.number = n;
        this.features = features;
    }

    public boolean isIndoor() {
        return features.indoor;
    }

    public void book(Associate p, String time) {
        bookings.put(time,new Booking(p,time));
    }
    
    public int estimationError() {
        return (int)(cumDiff / countServed);
    }

    public int getNumber() {
        return number;
    }

    public Associate booking(String time) {
        Booking b = bookings.get(time);
        return b==null?null:b.a;
    }

    public int countBookings() {
        return bookings.size();
    }
    
    public boolean match(Features f) {
        return  ( !f.indoor || (f.indoor && this.features.indoor) ) 
              && ( !f.heating || (f.heating && this.features.heating) )
              && ( !f.ac || (f.ac && this.features.ac) )
              ;
    }

    public int getOccupation() {
        return bookings.size();
    }

    public Collection<Associate> allBookedAssociates() {
        return bookings.values().stream().map( b -> b.a ).collect(toList());
    }

	@Override
	public int getField() {
		return number;
	}
}
