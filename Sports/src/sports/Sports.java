package sports;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

/**
 * Facade class for the research evaluation system
 *
 */
public class Sports {
    private TreeMap<String, Activity> activities = new TreeMap<>();
    private TreeMap<String, Category> categories = new TreeMap<>();
    private TreeMap<String, Product> products = new TreeMap<>();
    private HashMap<String,List<Rating>> userRatings = new HashMap<>();

    
//R1
    /**
     * Define the activities types treated in the portal.
     * The method can be invoked multiple times to add different activiteis.
     * 
     * @param actvities names of the activities
     * @throws SportsException thrown if no activity is provided
     */
    public void defineActivities (String... activities) throws SportsException {
        if(activities.length==0) throw new SportsException("No activities provided");
        for (int i = 0; i < activities.length; i++)
            this.activities.put(activities[i], new Activity(activities[i]));
    }

    /**
     * Retrieves the names of the defined activities.
     * 
     * @return activities names sorted alphabetically
     */
    public List<String> getActivities() {
        return new ArrayList<>(activities.keySet());
    }


    /**
     * Add a new category of sport products and the linked activities
     * 
     * @param name name of the new category
     * @param activities reference activities for the category
     * @throws SportsException thrown if any of the specified activity does not exist
     */
    public void addCategory(String name, String... linkedActivities) throws SportsException {
        Category cat = new Category(name);
        for(String act : linkedActivities) {
            Activity activity = activities.get(act);
            if(activity==null) throw new SportsException("Unknown activity: " + act);
            activity.addCategory(cat);
        }
        categories.put(name, cat);
    }
    
    /**
     * Retrieves number of categories.
     * 
     * @return categories count
     */
    public int countCategories() {
        return categories.size();
    }

    /**
     * Retrieves all the categories linked to a given activity.
     * 
     * @param activity the activity of interest
     * @return list of categories (sorted alphabetically)
     */
    public List<String> getCategoriesForActivity(String activity) {
        Activity a = activities.get(activity);
        if (a == null) return new ArrayList<String>();
        return a.catStream().map(Category::getName).sorted().collect(toList());
    }

//R2
    /**
     * Add a research group and the relative disciplines.
     * 
     * @param name name of the research group
     * @param disciplines list of disciplines
     * @throws SportsException thrown in case of duplicate name
     */
    public void addProduct(String name, String activityName, String categoryName) throws SportsException {
        if (products.containsKey(name)) throw new SportsException("Duplicate product name");
        Activity act = activities.get(activityName);
        Category cat = categories.get(categoryName);
        Product product = new Product(name,act,cat);
        products.put (name, product);
        cat.addProduct(product);
        act.addProduct(product);
    }

    /**
     * Retrieves the list of products for a given category.
     * The list is sorted alphabetically.
     * 
     * @param categoryName name of the category
     * @return list of products
     */
    public List<String> getProductsForCategory(String categoryName){
        Category cat = categories.get(categoryName);
        if(cat==null) return new ArrayList<>();
        return cat.getProducts().stream().map(Product::getName).sorted().collect(toList());
    }

    /**
     * Retrieves the list of products for a given activity.
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @return list of products
     */
    public List<String> getProductsForActivity(String activityName){
        Activity act = activities.get(activityName);
        if(act==null) return new ArrayList<>();
        return act.getProducts().stream().map(Product::getName).sorted().collect(toList());
    }

    /**
     * Retrieves the list of products for a given activity and a set of categories
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @param categoryNames names of the categories
     * @return list of products
     */
    public List<String> getProducts(String activityName, String... categoryNames){
        Activity act = activities.get(activityName);
        if(act==null) return new ArrayList<>();
        return
        Stream.of(categoryNames)
        .map(categories::get)
        .flatMap(c->c.getProducts().stream())
        .filter(p->act.getProducts().contains(p))
        .map(Product::getName).sorted().collect(toList());
    }

//R3
    /**
     * Add a new product rating
     * 
     * @param productName name of the product
     * @param userName name of the user submitting the rating
     * @param numStars score of the rating in stars
     * @param comment comment for the rating
     * @throws SportsException thrown numStars is not correct
     */
    public void addRating(String productName, String userName, int numStars, String comment) throws SportsException {
        if(numStars<0 || numStars>5) throw new SportsException("Wrong number of stars: " + numStars);
        Product product = products.get(productName);

        Rating r = new Rating(product,numStars,comment);
        product.addRating(r);
        
        List<Rating> ratings = userRatings.computeIfAbsent(userName, u -> new LinkedList<>() );
        ratings.add(r);
    }

    

    /**
     * Retrieves the ratings for the given product.
     * The ratings are sorted by descending number of stars.
     * 
     * @param productName name of the product
     * @return list of ratings sorted by stars
     */
    public List<String> getRatingsForProduct(String productName) {
        Product product = products.get(productName);
        
        return product.getRatings().stream()
                .sorted(comparing(Rating::getStars,reverseOrder()))
                .map(Rating::toString)
                .collect(toList())
                ;
    }


//R4
    /**
     * Returns the average number of stars of the rating for the given product.
     * 
     * 
     * @param productName name of the product
     * @return average rating
     */
    public double getStarsOfProduct (String productName) {
        Product product = products.get(productName);
        return product.getStars();
    }

    /**
     * Computes the overall average stars of all ratings
     *  
     * @return average stars
     */
    public double averageStars() {
        return
           userRatings.values().stream()
           .flatMap(Collection::stream)
           .mapToInt(Rating::getStars)
           .average().orElse(0)
           ;
        
    }

//R5 
    /**
     * For each activity return the average stars of the entered ratings.
     * 
     * Activity names are sorted alphabetically.
     * 
     * @return the map associating activity name to average stars
     */
    public SortedMap<String, Double> starsPerActivity() {
        return
            userRatings.values().stream()
            .flatMap(Collection::stream)
            .collect(groupingBy( 
                         r -> r.getProduct().getActivity().getName(),
                         TreeMap::new,
                         averagingInt(Rating::getStars)))
            ;
    }
    
    /**
     * For each average star rating returns a list of
     * the products that have such score.
     * 
     * Ratings are sorted in descending order.
     * 
     * @return the map linking the average stars to the list of products
     */
    public SortedMap<Double, List<String>> getProductsPerStars () {
        return 
                products.values().stream()
                .filter(p -> p.getRatings().size()>0)
                .sorted(comparing(Product::getName))
                .collect(groupingBy(Product::getStars,
                                    () -> new TreeMap<Double, List<String>>(reverseOrder()),
                                    mapping(Product::getName,toList())
                                    ))
                ;
    }

}