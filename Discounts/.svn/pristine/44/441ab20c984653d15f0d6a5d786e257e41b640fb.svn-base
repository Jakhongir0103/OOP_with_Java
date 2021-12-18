package discounts;
import java.util.*;
import static java.util.stream.Collectors.*;

public class Discounts {
	private int cardN = 0;
	private int purchaseN = 0;
	private SortedMap<Integer, Card> cards = new TreeMap<>();
	private SortedMap<String, Category> categories = new TreeMap<>();
	private SortedMap<String, Product> products = new TreeMap<>();
	private SortedMap<Integer, Purchase> purchases = new TreeMap<>();
	
	
	public Discounts() {
		cards.put(0, new Card(0,"Dummy"));
	}
	
	
	//R1
	public int issueCard(String name) {
		cardN++; cards.put(cardN, new Card(cardN, name));
		return cardN;
	}
	
    public String cardHolder(int cardN) {
        return cards.get(cardN).getName();
    }
    

	public int nOfCards() {return cards.size() - 1;}
	
	//R2
	public void addProduct(String categoryId, String productId, double price) 
			throws DiscountsException {//if productId duplicated
		if (products.keySet().contains(productId)) 
			throw new DiscountsException ("productId duplicated");
		Category category = categories.get(categoryId);
		if (category == null) {
			category = new Category(categoryId); 
			categories.put(categoryId, category);
		}
		Product product = new Product(productId, price, category);
		products.put(productId, product);
		category.addProduct(product);
	}
	
	public double getPrice(String productId) 
			throws DiscountsException {//if productId undefined
		Product p = products.get(productId);
		if (p == null) throw new DiscountsException ("productId undefined");
		//return (int) Math.round(p.getPrice());
		return p.getPrice();
	}

	public int getAveragePrice(String categoryId) throws DiscountsException {
		Category c = categories.get(categoryId);
		if (c == null) throw new DiscountsException ("categoryId undefined");
		return (int) Math.round(c.getAveragePrice());
	}
	
	//R3
	public void setDiscount(String categoryId, int percentage) throws DiscountsException {
		if (percentage < 0 ||  percentage > 50) throw new DiscountsException ("wrong discount");
		Category c = categories.get(categoryId);
		if (c == null) throw new DiscountsException ("categoryId undefined");
		c.setPercentage(percentage);
	}

	public int getDiscount(String categoryId) {
		return categories.get(categoryId).getPercentage();
	}

	//R4
	public int addPurchase(int cardId, String... items) throws DiscountsException {
		Purchase purchase = new Purchase();
		//def.linee
		for (String item: items) {
			//System.out.println(item);
			String[] l = item.split(":"); //productId:nOfUnits
			Product product = products.get(l[0]);
			if (product == null) throw new DiscountsException ("productId undefined");
			int nOfUnits = Integer.parseInt(l[1]);
			Line line = new Line(nOfUnits, product);
			purchase.addLine(line); product.addLine(line);
		}
		cards.get(cardId).addPurchase(purchase);
		purchaseN++; purchase.setId(purchaseN);
		purchases.put(purchaseN, purchase);
		boolean yesDiscount = cardId != 0;
		//System.out.println(yesDiscount);
		purchase.setAmount(yesDiscount);
		return purchaseN;
	}

	public int addPurchase(String... items) throws DiscountsException {
		return addPurchase(0, items);
	}
	
	public double getAmount(int purchaseCode) {
		Purchase p = purchases.get(purchaseCode);
		return p.getPurchaseAmount();
	}
	
	public double getDiscount(int purchaseCode)  {
		Purchase p = purchases.get(purchaseCode);
		return p.getPurchaseDiscount();
	}
	
	public int getNofUnits(int purchaseCode) {
		Purchase p = purchases.get(purchaseCode);
		return p.getNofUnits();
	}
	
	//R5
	public SortedMap<Integer, List<String>> productIdsPerNofUnits() {
		return products.values().stream()
		.filter(p -> p.getNofUnits() > 0)
		.collect(groupingBy(Product::getNofUnits, TreeMap::new,
		mapping(Product::getId, toList())));
	}
	
	public SortedMap<Integer, Integer> totalPurchasePerCard() {
		return cards.values().stream()
		.filter(card -> card.getId() != 0 && card.getNofPurchases() > 0 )
		.collect(toMap(Card::getId, Card::getTotalPurchase, (p1, p2) -> p1, TreeMap::new));
	}
	
	public int totalPurchaseWithoutCard() {
		return cards.get(0).getTotalPurchase();
	}
	
	public SortedMap<Integer, Integer> totalDiscountPerCard() {
		return cards.values().stream()
		.filter(card -> card.getId() != 0 && card.getNofPurchases() > 0 )
		.collect(toMap(Card::getId, Card::getTotalDiscount, (d1, d2) -> d1, TreeMap::new));
	}

}
