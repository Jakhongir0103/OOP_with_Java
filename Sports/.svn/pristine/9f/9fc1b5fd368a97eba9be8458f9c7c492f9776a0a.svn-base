package sports;

import java.util.LinkedList;
import java.util.List;


class Product {
    String name; 
    Category category;
    Activity activity;
    private List<Rating> ratings=new LinkedList<>();

    Product (String name, Activity activity, Category category) {
        this.name = name; this.category = category; this.activity = activity;
    }
    
    String getName() {return name;}
    
    Activity getActivity() {
        return activity;
    }

    public void addRating(Rating r) {
        this.ratings.add(r);
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public double getStars() {
        return ratings.stream().mapToInt(Rating::getStars).average().orElse(0);
    }
    
}
