package co.bookshop;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import co.bookshop.Book;

public class BookTest {

	@Test
	void bookHashNameAndPrice() {
		Book book = new Book("Harry Potter and the Philosopher Stone", new BigDecimal("8.00"));
		assertThat(book)
			.hasFieldOrPropertyWithValue("title", "Harry Potter and the Philosopher Stone")
			.hasFieldOrPropertyWithValue("price", new BigDecimal("8.00"));		
	}

}
