package sports;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Activity {

    private String name;
    
    private List<Category> categories=new LinkedList<>();
    private List<Product> products=new LinkedList<>();

    public Activity(String name) {
        this.name = name;
    }
    
    String getName() {
        return name;
    }

    public Stream<Category> catStream() {
        return categories.stream();
    }

    public void addCategory(Category cat) {
        categories.add(cat);
    }

    public void addProduct(Product product) {
       this.products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
    

    
}
