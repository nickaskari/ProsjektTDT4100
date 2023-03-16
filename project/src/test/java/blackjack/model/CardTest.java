package blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardTest{
	
	private Card card;
	
	@BeforeEach
	public void setup() {
		card = new Card('D', 8);
	}
	
	@Test
	public void testConstructor() {
		assertEquals('D', card.getSuit());
		assertEquals(8, card.getFace());
		assertThrows(IllegalArgumentException.class, () -> {
			new Card('Y', 5);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Card('D', 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Card('Y', 14);
		});
	}
	
	@Test
	public void testGetValue() {
		assertEquals(8, card.getValue());
		card = new Card('S', 1);
		assertEquals(11, card.getValue());
		card = new Card('C', 11);
		assertEquals(10, card.getValue());
		card = new Card('H', 12);
		assertEquals(10, card.getValue());
		card = new Card('C', 13);
		assertEquals(10, card.getValue());
	}
	
	@Test
	public void testToString() {
		card = new Card('S', 11);
		assertEquals("SJ", card.toString());
		card = new Card('C', 12);
		assertEquals("CQ", card.toString());
		card = new Card('D', 13);
		assertEquals("DK", card.toString());
		card = new Card('H', 1);
		assertEquals("HA", card.toString());
		card = new Card('C', 8);
		assertEquals("C8", card.toString());
	}
	
	@Test 
	public void testHide() {
		assertEquals("--", card.Hide());
	}
}
