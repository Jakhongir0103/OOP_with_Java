package discounts;

class Line {
	private int nOfUnits;
	private Product product;
 
	Line(int nOfUnits, Product product) {
	 this.nOfUnits = nOfUnits; this.product = product;
	}

	int getnOfUnits() {return nOfUnits;}

	Product getProduct() {return product;}
}
