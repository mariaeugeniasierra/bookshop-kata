package co.bookshop;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import co.bookshop.Book;
import co.bookshop.MultiBuyDiscount;
import co.bookshop.MultiBuyDiscount.Result;

public class MultiBuyDiscountTest {
	
	private final static Book BOOK_1 = new Book("Harry Potter and the Philosopher's Stone", new BigDecimal("8.00"));
	private final static Book BOOK_2 = new Book("Harry Potter and the Chamber of Secrets", new BigDecimal("8.00"));
	private final static Book BOOK_3 = new Book("Harry Potter and the Prisoner of Azkaban", new BigDecimal("8.00"));
	
	@Test
	public void discountIsAppliedIfRequiredNumberIsPresent() {
		final MultiBuyDiscount discount = new MultiBuyDiscount(2, 5);
		Result result = discount.apply(asList(BOOK_1, BOOK_1, BOOK_2));
		assertThat(result.isApplied()).isTrue();
		assertThat(result.getItems()).containsExactlyInAnyOrder(BOOK_1, BOOK_2);
		assertThat(result.getRemainder()).containsExactlyInAnyOrder(BOOK_1);
		assertThat(result.getPrice()).isEqualByComparingTo("15.2");
	}

	@Test
	public void discountIsNotAppliedIfRequiredNumberIsNotPresent() {
		final MultiBuyDiscount discount = new MultiBuyDiscount(5, 5);
		Result result = discount.apply(asList(BOOK_1, BOOK_2, BOOK_3));
		assertThat(result.isApplied()).isFalse();
		assertThat(result.getItems()).isEmpty();
		assertThat(result.getRemainder()).containsExactlyInAnyOrder(BOOK_1, BOOK_2, BOOK_3);
		assertThat(result.getPrice()).isEqualByComparingTo("0");
	}

	@Test
	public void discountIsAppliedIMultipleTimesIfPresent() {
		final MultiBuyDiscount discount = new MultiBuyDiscount(2, 5);
		Result result = discount.apply(asList(BOOK_1, BOOK_1, BOOK_2, BOOK_3));
		assertThat(result.isApplied()).isTrue();
		assertThat(result.getItems()).containsExactlyInAnyOrder(BOOK_1, BOOK_1, BOOK_2, BOOK_3);
		assertThat(result.getRemainder()).isEmpty();
		assertThat(result.getPrice()).isEqualByComparingTo("30.4");
	}

}
