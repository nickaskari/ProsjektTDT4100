package blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class HandTest {
	
	private Hand hand;
	
	@BeforeEach
	public void setup() {
		hand = new Hand(new ArrayList<>(List.of(new Card('D', 7)
				, new Card('S', 1))));
	}
	
	@Test
	public void testConstructor() {
		List<Card> test = new ArrayList<>(List.of(new Card('D', 7)
				, new Card('S', 1), new Card('S', 10)));
		hand = new Hand(test);
		assertTrue(handComparison(test, hand.getHand()));
		List<Card> test2 = new ArrayList<>(List.of(new Card('D', 7)
				, new Card('S', 1), new Card('S', 1)));
		assertThrows(IllegalArgumentException.class, () -> {
			new Hand(test2);
		});
	}
	
	@Test
	public void testShowHand() {
		String s = " D7 SA";
		assertEquals(s, hand.showHand());
	}
	
	@Test
	public void testShowCard() {
		assertEquals(new Card('S', 1).toString(), hand.showCard(1).toString());
		IndexOutOfBoundsException thrown = assertThrows(
		           IndexOutOfBoundsException.class,
		           () -> hand.showCard(3) 
		);
		assertTrue(thrown.getMessage().contains("There is no card at this index!"));
		hand = new Hand(new ArrayList<Card>());
		IllegalArgumentException thrown1 = assertThrows(
		           IllegalArgumentException.class,
		           () -> hand.showCard(5) 
		);
		assertTrue(thrown1.getMessage().contains("You have an empty hand!"));
	}
	
	@Test
	public void testAddCard() {
		hand.addCard(new Card('C', 9));
		List<Card> test = new ArrayList<>(List.of(new Card('D', 7)
				, new Card('S', 1), new Card('C', 9)));
		assertTrue(handComparison(test, hand.getHand()));
		IllegalArgumentException thrown = assertThrows(
		           IllegalArgumentException.class,
		           () -> hand.addCard(new Card('D', 7)) 
		);
		assertTrue(thrown.getMessage().contains("This card is already in the hand!"));
	}
	
	@Test
	public void tesetHandValue() {
		assertEquals(18, hand.handValue());
		List<Card> ace1 = new ArrayList<>(List.of(new Card('D', 7)
				, new Card('S', 1), new Card('S', 12)));
		List<Card> ace2 = new ArrayList<>(List.of(new Card('D', 1)
				, new Card('S', 1)));
		List<Card> ace2a = new ArrayList<>(List.of(new Card('D', 1)
				, new Card('S', 1), new Card('C', 12)));
		List<Card> ace3 = new ArrayList<>(List.of(new Card('D', 1)
				, new Card('S', 1), new Card('C', 1)));
		List<Card> ace3a = new ArrayList<>(List.of(new Card('D', 1)
				, new Card('S', 1), new Card('C', 1)
				, new Card('S', 9)));
		List<Card> ace4 = new ArrayList<>(List.of(new Card('D', 1)
				, new Card('S', 1), new Card('C', 1)
				, new Card('H', 1)));
		List<Card> ace4a = new ArrayList<>(List.of(new Card('D', 1)
				, new Card('S', 1), new Card('C', 1)
				, new Card('H', 1), new Card('S', 8)));
		
		hand = new Hand(ace1);
		assertEquals(18, hand.handValue());
		hand = new Hand(ace2);
		assertEquals(12, hand.handValue());
		hand = new Hand(ace2a);
		assertEquals(12, hand.handValue());
		hand = new Hand(ace3);
		assertEquals(13, hand.handValue());
		hand = new Hand(ace3a);
		assertEquals(12, hand.handValue());
		hand = new Hand(ace4);
		assertEquals(14, hand.handValue());
		hand = new Hand(ace4a);
		assertEquals(12, hand.handValue());	
	}
	
	private boolean handComparison(List<Card> d1, List<Card> d2) { 
		if(d1.toString().equals(d2.toString())) {
			return true;
		}
		else {
			return false;
		}
	}
}
