package it.polito.oop.futsal;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import  static java.util.stream.Collectors.*;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
    public final static boolean REQUIRED = true;
    public final static boolean DONT_CARE = false;
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }
    }
    private List<Field> fields = new ArrayList<Field>();
    private String open;
    private String close;
    private List<Associate> members = new ArrayList<Associate>();
    
    public void defineFields(Features... features) throws FutsalException {
        for(Features f : features) {
            if( (f.heating || f.ac) && !f.indoor) {
                throw new FutsalException("Cannot have heating or AC for outdoor field");
            }
            fields.add(new Field(fields.size()+1,f));
        }
    }
    
    public long countFields() {
        return fields.size();
    }

    public long countIndoor() {
        return fields.stream().filter(Field::isIndoor).count();
    }
    
    public String getOpeningTime() {
        return open;
    }
    
    public void setOpeningTime(String time) {
        this.open = time;
    }
    
    public String getClosingTime() {
        return close;
    }
    
    public void setClosingTime(String time) {
       this.close = time;
    }

    public int newAssociate(String first, String last, String mobile) {
        members.add(new Associate(members.size(),first,last,mobile));
        return members.size()-1;
    }
    
    public String getFirst(int associate) throws FutsalException {
        if(associate<0 || associate >= members.size()) 
            throw new FutsalException("Wrong party ID " + associate);
        return members.get(associate).getFirst();
    }
    
    public String getLast(int associate) throws FutsalException {
        if(associate<0 || associate >= members.size()) 
            throw new FutsalException("Wrong party ID " + associate);
        return members.get(associate).getLast();
    }
    
    public String getPhone(int associate) throws FutsalException {
        if(associate<0 || associate >= members.size()) 
            throw new FutsalException("Wrong party ID " + associate);
        return members.get(associate).getMobile();
    }
    
    public int countAssociates() {
       return members.size();
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
    	LocalTime lt = LocalTime.parse(time);
        Duration d = Duration.between(LocalTime.parse(open), lt);
        long minutes = d.getSeconds()/60L;
        if( minutes % 60 != 0) {
            throw new FutsalException("Wrong time");
        }
        
        if(lt.isBefore(LocalTime.parse(open)) || lt.isAfter(LocalTime.parse(close))) {
            throw new FutsalException("Wrong time");
        }
        
        if(field<1 || field > fields.size()) 
            throw new FutsalException("Wrong field ID " + associate);

        Field f = fields.get(field-1);
        
        if(associate<0 || associate >= members.size()) 
            throw new FutsalException("Wrong field ID " + associate);
        Associate a = members.get(associate);
        
        f.book(a, time);
    }

    public boolean isBooked(int field, String time) {
        Field f = fields.get(field-1);
        Associate a = f.booking(time);
        return a != null;
    }
    

    public int getOccupation(int field) {
        Field f = fields.get(field-1);
        return f.getOccupation();
    }
    
    public List<FieldOption> findOptions(String time, Features required){
        return fields.stream().
                filter(f -> f.match(required)).
                sorted(Comparator.comparing(FieldOption::getOccupation).
                        thenComparing(FieldOption::getField)).
                collect(toList())
                ;
    }
    
    public long countServedAssociates() {
        return fields.stream().flatMap(f->f.allBookedAssociates().stream()).distinct().count();
    }
    
    public Map<Integer,Long> fieldTurnover() {
        return fields.stream().collect(toMap(Field::getNumber,f -> (long)(f.countBookings())));
    }
    
    public double occupation() {
        double slots = Duration.between(LocalTime.parse(open),LocalTime.parse(close)).getSeconds()/3600;
        
        int bookings = fields.stream().mapToInt(Field::countBookings).sum();
        
        return bookings/(slots*fields.size());
    }
    
}
