package sports;

public class Rating {
    private Product product;
    private int numStars;
    private String comment;

    public Rating(Product product, int numStars, String comment) {
        this.product=product;
        this.numStars=numStars;
        this.comment=comment;
    }
    
    Product getProduct() {
        return product;
    }

    public int getStars() {
        return numStars;
    }
    
    @Override
    public String toString() {
        return numStars + " : " + comment;
    }
}
