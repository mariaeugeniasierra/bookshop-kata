package co.bookshop;

import static java.util.Collections.emptyList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

public class MultiBuyDiscount {
	
	public class Result {
		private final boolean applied;
		private final List<Book> items;
		private final List<Book> remainder;
		private final BigDecimal price;
		
		private Result(boolean applied, List<Book> items, List<Book> remainder, BigDecimal price) {
			this.applied = applied;
			this.items = items;
			this.remainder = remainder;
			this.price = price;
		}
		
		public boolean isApplied() {
			return applied;
		}
		public List<Book> getItems() {
			return items;
		}
		public BigDecimal getPrice() {
			return price;
		}

		public List<Book> getRemainder() {
			return remainder;
		}
		
	}

	private final int numberRequired;
	private final BigDecimal discount;

	public MultiBuyDiscount(final int numberRequired, final int discount) {
		this.numberRequired = numberRequired;
		this.discount = new BigDecimal(BigInteger.valueOf(100 - discount), 2);
	}

	private Result withAppliedDiscount(final List<Book> matched, final List<Book> notMatched) {
		return new Result(
				true, 
				matched, 
				notMatched,
				matched.stream()
					    .map(Book::getPrice)
					    .reduce(BigDecimal.ZERO, (a, b) -> a.add(b))
					    .multiply(discount, MathContext.DECIMAL32)
					    
		);
	}

	private Result withoutAppledDiscount(final List<Book> notMatched) {
		return new Result(false, emptyList(), notMatched, BigDecimal.ZERO);
	}
	
	private Result apply(Result previous) {
		Set<Book> matched = new HashSet<>();
		List<Book> notMatched = new ArrayList<>();
		for (Book item : previous.remainder) {
			if (matched.size() < this.numberRequired && !matched.contains(item)) {
				matched.add(item);
			} else {
				notMatched.add(item);
			}
		}
		final Result result = matched.size() == this.numberRequired ? 
				withAppliedDiscount((List)ImmutableList.builder().addAll(matched).addAll(previous.items).build(), notMatched) : 
				withoutAppledDiscount(previous.items);
				
		return result.applied ? apply(result) : previous;
		
	}
	
	public Result apply(final List<Book> items) {
		return apply(withoutAppledDiscount(items));
		
	}
}
