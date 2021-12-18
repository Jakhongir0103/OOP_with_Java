package supermarket;
import java.util.*;
import java.util.stream.Collectors;

public class Supermarket {

	////R1
	
	private Map<String, List<Product>> products = new HashMap<>();
	private String prevCat = null;
	public int addProducts (String categoryName, String productNames, String productPrices) throws SMException {
		String[] pN = productNames.split(",");
		String[] pP = productPrices.split(",");
		
		if(products.containsKey(categoryName)) {
			throw new SMException("defined");
		}
		if(pN.length != pP.length) {
			throw new SMException("not same");
		}
				
		products.put(categoryName, new ArrayList<>());
		for(int i = 0; i < pN.length; i++) {
			int j = i; 
			if(prevCat != null) {
				if(products.get(prevCat).stream().filter(p -> p.getProductNames().equals(pN[j])).findFirst().isPresent()) {
					products.remove(categoryName);
					throw new SMException("defined");
				}
			}
			products.get(categoryName).add(new Product(categoryName, pN[i], Double.parseDouble(pP[i])));
		}
		prevCat = categoryName;
		return pN.length;
	}

	public double getPrice (String productName) throws SMException {
		for(List<Product> p: products.values()) {
			for(Product pn: p) {
				if(pn.getProductNames().equals(productName)) {
					return pn.getProductPrices();
				}
			}
		}
		throw new SMException("not defined");
	}

	public SortedMap<String,String> mostExpensiveProductPerCategory () {
		SortedMap<String,String> result = new TreeMap<String,String>();
		for(String c: products.keySet()) {
			result.put(c, this.products.get(c).stream().max(Comparator.comparing(Product::getProductPrices)).get().getProductNames());
		}
		return result;
	}

	//R2
	private Map<String, List<Integer>> catDis = new HashMap<>();
	public void setDiscount (String categoryName, int percentage) throws SMException {
		if(!products.containsKey(categoryName)) {
			throw new SMException("not defined");
		}
		if(percentage > 40) {
			throw new SMException("not defined");
		}
		if(catDis.containsKey(categoryName)) {
			catDis.get(categoryName).add(percentage);
		}else {
			catDis.put(categoryName, new ArrayList<>());
			catDis.get(categoryName).add(0);
			catDis.get(categoryName).add(percentage);
		}
		products.get(categoryName).forEach(p -> {
			p.setDiscount(percentage);
		});
	}

	public void setDiscount (int percentage, String... productNames) throws SMException {
		for(String p1: productNames) {
			products.values().stream().flatMap(List::stream).filter(pn -> pn.getProductNames().equals(p1)).forEach(pn -> {pn.setDiscount(percentage);});
		}
	}

	public List<Integer> getDiscountHistoryForCategory(String categoryName) {
		if(!catDis.containsKey(categoryName)) {
			return new ArrayList<>(0);
		}
		return catDis.get(categoryName);
	}

	public List<Integer> getDiscountHistoryForProduct(String productName) {
		return products.values().stream().flatMap(List::stream).filter(p -> p.getProductNames().equals(productName)).findFirst().get().getDiscount();
	}

	//R3
	private Map<Integer, Card> card = new HashMap<>();
	private int code = 1000;
	public int issuePointsCard (String name, String dateOfBirth) throws SMException {
		if(card.values().stream().filter(c -> c.getName().equals(name) && c.getDateOfBirth().equals(dateOfBirth)).findFirst().isPresent()) {
			throw new SMException("same");
		}
		
		card.put(code, new Card(code, name, dateOfBirth));
		return code++;
	}

	private SortedMap<Integer, Integer> discounts = new TreeMap<Integer, Integer>();
	public void fromPointsToDiscounts (String points, String discounts) throws SMException {
		String[] p = points.split(",");
		String[] d = discounts.split(",");
		if(p.length != d.length) {
			throw new SMException("not same");
		}
		
		for(int i = 0; i < p.length; i++) {
			this.discounts.put(Integer.parseInt(p[i]), Integer.parseInt(d[i]));
		}
	}

	public SortedMap<Integer, Integer>  getMapPointsDiscounts() {
		return discounts;
	}

	public int getDiscountFromPoints (int points) {
		if(!discounts.containsKey(points)) {
			return 0;
		}
		return discounts.get(points);
	}

	//R4
	
	public int getCurrentPoints (int pointsCardCode) throws SMException {
		if(!card.containsKey(pointsCardCode)) {
			throw new SMException("not same");
		}
		return card.get(pointsCardCode).getAvlblPoints();
	}

	public int getTotalPoints (int pointsCardCode) throws SMException {
		if(!card.containsKey(pointsCardCode)) {
			throw new SMException("not same");
		}
		return card.get(pointsCardCode).getTotalPoints();
	}

	private int pCode = 100;
	private Map<Integer, Purchase> purchases = new HashMap<>();
	public int addPurchase (int pointsCardCode, int pointsRedeemed, String ... productNames) throws SMException {
		if(pointsRedeemed > card.get(pointsCardCode).getAvlblPoints()) {
			throw new SMException("error");
		}
		purchases.put(pCode, new Purchase(pointsCardCode, pointsRedeemed, productNames));
		double totPrice = 0, totDiscount = 0;
		for(String p: productNames) {
			Product pc = products.values().stream().flatMap(List::stream).filter(p1 -> p1.getProductNames().equals(p)).findFirst().get();
			totDiscount += pc.getProductPrices() * pc.getDiscount().get(pc.getDiscount().size() - 1) / (double) 100;
			totPrice += pc.getProductPrices() * (100 - pc.getDiscount().get(pc.getDiscount().size() - 1)) / (double) 100;
		}
		int redeemed = 0;
		if(discounts.containsKey(pointsRedeemed)) {
			redeemed = discounts.get(pointsRedeemed);
		}
		purchases.get(pCode).setPrice(totPrice - redeemed);
		purchases.get(pCode).setDiscount(totDiscount + redeemed);
		card.get(pointsCardCode).addTotalPoints((int) Math.round(totPrice - redeemed));
		if(redeemed == 0) {
			card.get(pointsCardCode).setAvlblPoints((int) Math.round(card.get(pointsCardCode).getTotalPoints()));
		}else {
			card.get(pointsCardCode).setAvlblPoints((int) Math.round(card.get(pointsCardCode).getTotalPoints() - pointsRedeemed));
		}
		return pCode++;
	}

	public double getPurchasePrice (int purchaseCode) throws SMException {
		if(!purchases.containsKey(purchaseCode)) {
			throw new SMException("not same");
		}
		return purchases.get(purchaseCode).getPrice();
	}

	public double getPurchaseDiscount (int purchaseCode) throws SMException {
		if(!purchases.containsKey(purchaseCode)) {
			throw new SMException("not same");
		}
		return purchases.get(purchaseCode).getDiscount();
	}
	
	//R5

	public SortedMap<Integer, List<Integer>> pointsCardsPerTotalPoints () {
		return card.values().stream().filter(c -> c.getTotalPoints() != 0).sorted(Comparator.comparing(Card::getCode)).collect(Collectors.groupingBy(Card::getTotalPoints, () -> new TreeMap<Integer, List<Integer>>(), Collectors.mapping(Card::getCode, Collectors.toList())));
	}

	public SortedMap<String, SortedSet<String>> customersPerCategory () {
		SortedMap<String, SortedSet<String>> result = new TreeMap<String, SortedSet<String>>();
		for(String cat: products.keySet()) {
			for(Product p: products.get(cat)) {
				purchases.values().stream().filter(pr -> Arrays.asList(pr.getProductNames()).contains(p.getProductNames())).forEach(pr -> {
					if(!result.containsKey(cat)) {
						result.put(cat, new TreeSet<>());
					}
					result.get(cat).add(card.get(pr.getPointsCardCode()).getName());
				});
			}
		}
		return result;
	}
	public SortedMap<Integer, List<String>> productsPerDiscount() {
		return products.values().stream().flatMap(List::stream).filter(p -> Collections.max(p.getDiscount()) != 0).sorted(Comparator.comparing(Product::getProductNames)).collect(Collectors.groupingBy(p -> Collections.max(p.getDiscount()), () -> new TreeMap<Integer, List<String>>(Comparator.reverseOrder()), Collectors.mapping(p1 -> p1.getProductNames(), Collectors.toList())));
	}
	
	// R6

	private int receipt = 0;
	private List<Integer> receipts = new ArrayList<>();
	public int newReceipt() { // return code of new receipt
		receipts.add(receipt);
		return receipt++;
	}

	private Map<Integer, Receipt> receiptCard = new HashMap<>();
	public void receiptAddCard(int receiptCode, int PointsCard)  throws SMException { // add the points card info to the receipt
		if(receipts.get(receipts.size() - 1) != receiptCode) {
			throw new SMException("not same");
		}
		if(!card.containsKey(PointsCard) || closedRec.contains(receiptCode)) {
			throw new SMException("error");
		}
		if(!receiptCard.containsKey(receiptCode)) {
			receiptCard.put(receiptCode, new Receipt(receiptCode));
		}
		receiptCard.get(receiptCode).setCard(PointsCard);
	}

	public int receiptGetPoints(int receiptCode)  throws SMException { // return available points on points card if added before
		if(receipts.get(receipts.size() - 1) != receiptCode) {
			throw new SMException("not same");
		}
		if(closedRec.contains(receiptCode) || !receiptCard.containsKey(receiptCode)) {
			throw new SMException("error");
		}
		return card.get(receiptCard.get(receiptCode).getCard()).getAvlblPoints();
	}

	public void receiptAddProduct(int receiptCode, String product)  throws SMException { // add a new product to the receipt
		if(receipts.get(receipts.size() - 1) != receiptCode) {
			throw new SMException("not same");
		}
		if(closedRec.contains(receiptCode) || !products.values().stream().flatMap(List::stream).filter(p -> p.getProductNames().equals(product)).findFirst().isPresent()) {
			throw new SMException("error");
		}
		if(!receiptCard.containsKey(receiptCode)) {
			receiptCard.put(receiptCode, new Receipt(receiptCode));
		}
		receiptCard.get(receiptCode).setProduct(product);
	}

	public double receiptGetTotal(int receiptCode)  throws SMException { // return current receipt code
		if(receipts.get(receipts.size() - 1) != receiptCode) {
			throw new SMException("not same");
		}
		if(closedRec.contains(receiptCode) || !receiptCard.containsKey(receiptCode)) {
			throw new SMException("not same");
		}
		double totalPr = 0;
		for(String p: receiptCard.get(receiptCode).getProducts()) {
			for(Product pc: products.values().stream().flatMap(List::stream).filter(p1 -> p1.getProductNames().equals(p)).collect(Collectors.toList())){
				totalPr += pc.getProductPrices() * (100 - pc.getDiscount().get(pc.getDiscount().size() - 1)) / (double) 100;
			}
		}
		if(receiptCard.get(receiptCode).getPoints() != 0) {
			receiptCard.get(receiptCode).setTotal(totalPr - discounts.get(receiptCard.get(receiptCode).getPoints()));
		}else {
			receiptCard.get(receiptCode).setTotal(totalPr);
		}
		return receiptCard.get(receiptCode).getTotal();
	}

	public void receiptSetRedeem(int receiptCode, int points)  throws SMException { // sets the amount of points to be redeemed
		if(receipts.get(receipts.size() - 1) != receiptCode) {
			throw new SMException("not same");
		}
		if(!receiptCard.containsKey(receiptCode) || closedRec.contains(receiptCode) || points > card.get(receiptCard.get(receiptCode).getCard()).getAvlblPoints() || !discounts.containsKey(points)) {
			throw new SMException("error");
		}
		receiptCard.get(receiptCode).setTotal(receiptCard.get(receiptCode).getTotal() - discounts.get(points));
		receiptCard.get(receiptCode).setPoints(points);
	}

	private List<Integer> closedRec = new ArrayList<>();
	public int closeReceipt(int receiptCode)  throws SMException { // close the receipt and add the purchase (calls addPurchase() ) and return purchase code (could be the same as receipt code)
		if(receipts.get(receipts.size() - 1) != receiptCode) {
			throw new SMException("not same");
		}
		String[] prs = new String[receiptCard.get(receiptCode).getProducts().size()];
		for(int i = 0; i < receiptCard.get(receiptCode).getProducts().size(); i++) {
			prs[i] = receiptCard.get(receiptCode).getProducts().get(i);
		}
		closedRec.add(receiptCode);
		return addPurchase(receiptCard.get(receiptCode).getCard(), receiptCard.get(receiptCode).getPoints(), prs);
	}
}