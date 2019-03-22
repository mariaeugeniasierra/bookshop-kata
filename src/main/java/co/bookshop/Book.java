package co.bookshop;

import java.math.BigDecimal;

public class Book {

	private final String title;
	private final BigDecimal price;

	public Book(final String title, final BigDecimal price) {
		this.title = title;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

}
