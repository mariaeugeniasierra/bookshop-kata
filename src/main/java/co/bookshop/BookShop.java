package co.bookshop;


import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import co.bookshop.MultiBuyDiscount.Result;

public class BookShop {
		
	
	private Map<String, Book> items = ImmutableSet.of(
			new Book("Harry Potter and the Philosopher's Stone", new BigDecimal("8.00")),
			new Book("Harry Potter and the Chamber of Secrets", new BigDecimal("8.00")),
			new Book("Harry Potter and the Prisoner of Azkaban", new BigDecimal("8.00")),
			new Book("Harry Potter and the Globlet of Fire", new BigDecimal("8.00")),
			new Book("Harry Potter and the Order of Phoenix", new BigDecimal("8.00"))
	).stream().collect(Collectors.toMap(
			(b) -> b.getTitle(),
			(book) -> book
	));
	
	private List<MultiBuyDiscount> discounts = ImmutableList.of(
			new MultiBuyDiscount(5, 25),
			new MultiBuyDiscount(4, 20),
			new MultiBuyDiscount(3, 10),
			new MultiBuyDiscount(2, 5),
			new MultiBuyDiscount(1, 0) //Nominal price
	);
	
	private List<Book> cart = new ArrayList<>();
	

	private BigDecimal getPrice(List<MultiBuyDiscount> discountsApplied) {
		return discountsApplied.stream().reduce(
				new ImmutablePair<>(BigDecimal.ZERO, this.cart),
				(pair, discount) -> {
					final Result result = discount.apply(pair.right);
					return new ImmutablePair<>(pair.left.add(result.getPrice()), result.getRemainder());
				}, (pair1, pair2) -> {
					return pair2;
				}
		).left;
	}

	public Set<Book> inventory() {
		return new HashSet<>(this.items.values());
	}

	public Book get(String title) {
		return items.get(title);
	}
	

	public BigDecimal getPrice() {
		final List<List<MultiBuyDiscount>> allDiscountCombinations = discounts.stream()				
				.reduce(
				new ArrayList<List<MultiBuyDiscount>>(), 
				(all, d) -> {
					all.forEach(l -> l.add(d));
					all.add(new ArrayList<>(asList(d)));
					return all;
				}, (d1, d2) -> {
					return d2;
				}
		);
		return allDiscountCombinations.stream()
				.map(this::getPrice)
				.min(Comparator.comparing((p) -> p))
				.orElse(BigDecimal.ZERO);
	}

	public void addToCart(final Book book) {
		this.cart.add(book);
	}
}
