package blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CardDeckTest {
	
	private CardDeck deck;
	private List<Card> empty = new ArrayList<Card>();
	
	@BeforeEach
	public void setup() {
		//The deck is: [SA, HA, DA, CA]
		deck = new CardDeck(1);
	}
	
	@Test
	public void testConstructor() {
		List<Card> smalldeck = new ArrayList<Card>(List.of(new Card('S', 1)
			, new Card('H', 1), new Card('D', 1), new Card('C', 1)));
		assertTrue(deckComparison(smalldeck, deck.getCardDeck()));
		deck = new CardDeck(13);
		assertTrue(deckComparison(getFulldeck(), deck.getCardDeck()));
		deck = new CardDeck(0);
		assertTrue(deckComparison(empty, deck.getCardDeck()));
		assertThrows(IllegalArgumentException.class, () -> {
			new CardDeck(14);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new CardDeck(-1);
		});
	}
	
	@Test
	public void testAddCardManually() {
		IllegalArgumentException thrown = assertThrows(
		           IllegalArgumentException.class,
		           () -> deck.addCardManually(new Card('S', 1)) 
		);
		assertTrue(thrown.getMessage().contains("You cannot have duplicate cards in a carddeck!"));
		deck.addCardManually(new Card('D', 7));
		List<Card> test = new ArrayList<Card>(List.of(new Card('S',1)
			, new Card('H', 1), new Card('D', 1), new Card('C', 1)
			, new Card('D', 7)));
		assertTrue(deckComparison(test, deck.getCardDeck()));
	}
	
	@Test
	public void testGetCard() {
		assertEquals(new Card('D', 1).toString(), deck.getCard(2).toString());
		IndexOutOfBoundsException thrown = assertThrows(
		           IndexOutOfBoundsException.class,
		           () -> deck.getCard(4) 
		);
		assertTrue(thrown.getMessage().contains("There is no card at this index!"));
		deck = new CardDeck(0);
		IllegalArgumentException thrown1 = assertThrows(
		           IllegalArgumentException.class,
		           () -> deck.getCard(2) 
		);
		assertTrue(thrown1.getMessage().contains("You have an empty deck!"));
	}
	
	@Test
	public void testShufflePerfectly() {
		deck.shufflePerfectly();
		
		assertTrue(deck.getCardDeck().indexOf(new Card('S', 1)) != 0);
		assertTrue(deck.getCardDeck().indexOf(new Card('H', 1)) != 1);
		assertTrue(deck.getCardDeck().indexOf(new Card('D', 1)) != 2);
		assertTrue(deck.getCardDeck().indexOf(new Card('C', 1)) != 3);
	}
	
	private List<Card> getFulldeck() {
		List<Card> fulldeck = new ArrayList<Card>();
		char suits[] = new char[]{'S', 'H', 'D', 'C'}; 
		for(char suit : suits) {
			for(int i = 0; i < 13; i++) {
				fulldeck.add(new Card(suit, i + 1));
			}
		}
		return fulldeck;
	}
	
	private boolean deckComparison(List<Card> d1, List<Card> d2) { 
		if(d1.toString().equals(d2.toString())) {
			return true;
		}
		else {
			return false;
		}
	}
}
