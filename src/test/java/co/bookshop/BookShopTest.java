package co.bookshop;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.bookshop.Book;
import co.bookshop.BookShop;

public class BookShopTest {
	
	private BookShop bookShop;
	private Book book1;
	private Book book2;
	private Book book3;
	private Book book4;
	private Book book5;
	
	
	@BeforeEach
	public void setUp() {
		this.bookShop = new BookShop();
		this.book1 = this.bookShop.get("Harry Potter and the Philosopher's Stone"); 
		this.book2 = this.bookShop.get("Harry Potter and the Chamber of Secrets");
		this.book3 = this.bookShop.get("Harry Potter and the Prisoner of Azkaban");
		this.book4 = this.bookShop.get("Harry Potter and the Globlet of Fire");
		this.book5 = this.bookShop.get("Harry Potter and the Order of Phoenix");
		
	}

	@Test
	public void emptyCartHas0Price() {
		assertThat(this.bookShop.getPrice()).isEqualTo(BigDecimal.ZERO);
	}
	
	@Test
	public void all5BooksShouldCost8() {
		assertThat(this.bookShop.inventory())
			.hasSize(5)
			.extracting("price").allMatch((price) -> price.equals(new BigDecimal("8.00")));
	}
	
	@Test
	public void getBookByTitle() {
		String title = "Harry Potter and the Philosopher's Stone";
		assertThat(this.bookShop.get(title)).hasFieldOrPropertyWithValue("title", title);
	}
	
	
	@Test
	public void oneBookInCartHasNominaPrice() {
		this.bookShop.addToCart(book1);
		assertThat(this.bookShop.getPrice()).isEqualByComparingTo("8.00");
	}

	@Test
	public void twoCopiesSameBookInCartHasNominaPrice() {
		this.bookShop.addToCart(book1);
		this.bookShop.addToCart(book1);
		assertThat(this.bookShop.getPrice()).isEqualByComparingTo("16.00");
	}	

	@Test
	public void twoDifferentBooksHave5PercentDiscount() {
		this.bookShop.addToCart(book1);
		this.bookShop.addToCart(book2);
		assertThat(this.bookShop.getPrice()).isEqualByComparingTo("15.2");
	}	
	
	@Test
	public void onlyOneCopyGetsThDiscountAplied() {
		this.bookShop.addToCart(book1);
		this.bookShop.addToCart(book1);
		this.bookShop.addToCart(book2);
		assertThat(this.bookShop.getPrice()).isEqualByComparingTo("23.2");
	}
	
	@Test
	public void threeDifferentBooksHave10PercentDiscount() {
		this.bookShop.addToCart(book1);
		this.bookShop.addToCart(book2);
		this.bookShop.addToCart(book3);
		assertThat(this.bookShop.getPrice()).isEqualByComparingTo("21.6");
	}	
	
	@Test
	public void fourDifferentBooksHave20PercentDiscount() {
		this.bookShop.addToCart(book1);
		this.bookShop.addToCart(book2);
		this.bookShop.addToCart(book3);
		this.bookShop.addToCart(book4);
		assertThat(this.bookShop.getPrice()).isEqualByComparingTo("25.6");
	}	

	@Test
	public void fiveDifferentBooksHave25PercentDiscount() {
		this.bookShop.addToCart(book1);
		this.bookShop.addToCart(book2);
		this.bookShop.addToCart(book3);
		this.bookShop.addToCart(book4);
		this.bookShop.addToCart(book5);
		assertThat(this.bookShop.getPrice()).isEqualByComparingTo("30.0");
	}		
	
	@Test
	public void bigDiscountPossibleIsApplied() {
		this.bookShop.addToCart(book1);
		this.bookShop.addToCart(book1);
		this.bookShop.addToCart(book2);
		this.bookShop.addToCart(book2);
		this.bookShop.addToCart(book3);
		this.bookShop.addToCart(book3);
		this.bookShop.addToCart(book4);
		this.bookShop.addToCart(book5);
		assertThat(this.bookShop.getPrice()).isEqualByComparingTo("51.20");
	}	
}