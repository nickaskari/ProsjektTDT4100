package blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class BlackJackTest {
	
	private BlackJack game;
	
	@BeforeEach
	public void setup() {
		game = new BlackJack();
	}
	
	@Test
	public void testConstructor() {
		assertFalse(game.isStand());
		assertEquals(0, game.getHitcount());
		assertEquals(52, game.getDeck().getCardCount());
		assertEquals(0, game.getPlayer().getHandsize());
		assertEquals(0, game.getDealer().getHandsize());
		List<String> initialstate = new ArrayList<>(List.of("true", "true"
				, "true", "true", "true", "0", "0"));
		assertEquals(game.getState().getStateList(), initialstate);
	}
	
	@Test
	public void testDeal() {
		game.getState().setGameover(false);
		IllegalStateException thrown = assertThrows(
		           IllegalStateException.class,
		           () -> game.deal()
		);
		assertTrue(thrown.getMessage().contains("You have to finish your game!"));
		
		game.getState().setGameover(true);
		game.deal();
		
		assertEquals(0, game.getHitcount());
		assertFalse(game.isStand());
		assertFalse(game.getState().isEven());
		assertFalse(game.getState().isGameover());
		assertFalse(game.getState().isBust());
		assertFalse(listComparison(unShuffledDeck(), game.getDeck().getCardDeck()));
		assertEquals(2, game.getPlayer().getHandsize());
		assertEquals(2, game.getDealer().getHandsize());
		//Checking that the dealer and player do not have any of the same cards.
		assertFalse(elementComparison(game.getPlayer().getHand(), game.getDealer().getHand()));
	}
	
	@Test 
	public void testDeal_isRealBlackJack() {
		game.deal();
		//Only a real Blackjack gives the player a value of 21 after pressing deal.
		if(game.getPlayer().handValue() == 21) {
			assertTrue(game.getState().isBlackjack());
		}
		else {
			assertFalse(game.getState().isBlackjack());
		}
	}
	
	@Test
	public void testHit_outofGame() {
		//gameover is initiated to be true.
		IllegalStateException thrown = assertThrows( 
		           IllegalStateException.class,
		           () -> game.hit()
		);
		assertTrue(thrown.getMessage().contains("Your hit is invalid, because the game is not in session."));
	}
	
	@Test
	public void testHit_getsCard() {
		game.getState().setGameover(false);

		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 2)
				, new Card('D', 3)));
		
		game.setPlayer(new Hand(player));
		game.hit(); 
		assertEquals(3, game.getPlayer().getHandsize()); 
		assertEquals(1, game.getHitcount());
		assertFalse(game.getState().isBlackjack());
	}
	
	@Test
	public void testHit_whenBust() {
		game.getState().setGameover(false);
		
		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 1)
				, new Card('D', 1), new Card('C', 10), new Card('S', 9)));
		
		game.setPlayer(new Hand(player));
		
		//Player busts.
		game.hit(); 
		assertTrue(game.getState().isGameover());
		assertFalse(game.getState().isWon());
		assertTrue(game.getState().isBust());
		
		IllegalStateException thrown = assertThrows( 
		           IllegalStateException.class,
		           () -> game.hit() 
		);
		assertTrue(thrown.getMessage().contains("Your hit is invalid, because the game is not in session."));
	}

	@Test
	public void testStand() {
		game.getState().setGameover(false);
		game.Stand();
		assertTrue(game.isStand());
		assertFalse(game.getState().isBlackjack());
		game.getState().setGameover(true);
		IllegalStateException thrown = assertThrows(
		           IllegalStateException.class,
		           () -> game.Stand()
		);
		assertTrue(thrown.getMessage().contains("You cannot stand when the game is not in session!"));
	}
	
	@Test
	public void testDealerPlays_Exceptions() { 
		//gameover is initiated to be true.
		IllegalStateException thrown = assertThrows( 
		           IllegalStateException.class,
		           () -> game.dealerPlays()
		);
		assertTrue(thrown.getMessage().contains("Dealer cannot play when the game is not in session!"));
		
		game.getState().setGameover(false);
		IllegalStateException thrown1 = assertThrows( 
		           IllegalStateException.class,
		           () -> game.dealerPlays()
		);
		assertTrue(thrown1.getMessage().contains("Player has not pressed stand yet!"));
		
	}
	
	//When dealer has 17 or more, he must stand.
	
	@Test
	public void testDealerPlays_dealerStands_1(){ 
		//Player wins.
		
		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 10)
				, new Card('D', 11)));
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 10)
				, new Card('C', 7)));
		
		game.getState().setGameover(false);
		game.Stand();
		game.setPlayer(new Hand(player));
		game.setDealer(new Hand(dealer));
		game.dealerPlays();
		assertFalse(game.isStand());
		assertTrue(game.getState().isGameover());
		assertTrue(game.getState().isWon()); 
	}
	
	@Test
	public void testDealerPlays_dealerStands_2() {
		//Dealer has more than player, dealer wins.
		
		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 3)
				, new Card('D', 8)));
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 10)
				, new Card('C', 7)));
		
		game.getState().setGameover(false);
		game.Stand();
		game.setPlayer(new Hand(player));
		game.setDealer(new Hand(dealer));
		game.dealerPlays();
		assertFalse(game.isStand());
		assertTrue(game.getState().isGameover());
		assertFalse(game.getState().isWon()); 
	}
		
	@Test
	public void testDealerPlays_dealerStands_3() {
		//Player and dealer have the same values, the game is even.
		
		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 10)
				, new Card('D', 7)));
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 10)
				, new Card('C', 7)));
		
		game.getState().setGameover(false);
		game.Stand();
		game.setPlayer(new Hand(player));
		game.setDealer(new Hand(dealer));
		game.dealerPlays();
		assertFalse(game.isStand());
		assertTrue(game.getState().isGameover());
		assertTrue(game.getState().isEven()); 
	}
	
	@Test
	public void testDealerPlays_dealerStands_4() {
		//The player and the dealer has 21, but the player has a real Blackjack
		//, so the player wins. This privilege is only for the player.
		
		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 10)
				, new Card('D', 1)));
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 10)
				, new Card('C', 5), new Card('D', 6)));
		
		game.getState().setGameover(false);
		game.Stand();
		game.setPlayer(new Hand(player));
		game.setDealer(new Hand(dealer));
		game.dealerPlays();
		assertFalse(game.isStand());
		assertTrue(game.getState().isGameover());
		assertTrue(game.getState().isWon()); 
	}
	
	//Dealer has less than 17, and must hit.
	
	@Test
	public void testDealerPlays_dealerHits() {
		//Player has the same or more than dealer.
		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 10)
				, new Card('D', 2)));
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 13)
				, new Card('C', 6)));
		
		game.getState().setGameover(false);
		game.Stand();
		game.setPlayer(new Hand(player));
		game.setDealer(new Hand(dealer));
		game.dealerPlays();
		assertTrue(game.getHitcount() > 0);
		assertTrue(game.getDealer().getHandsize() >= 3);	
	}

	@Test
	public void testDealerPlays_playerisBust() {
		//Dealer is not bust, player is bust.
		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 10)
				, new Card('D', 10), new Card('D', 8)));
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 13)
				, new Card('C', 6)));
		
		game.getState().setGameover(false);
		game.Stand();
		game.setPlayer(new Hand(player));
		game.setDealer(new Hand(dealer));
		game.dealerPlays();
		assertFalse(game.isStand());
		assertTrue(game.getState().isGameover());
		assertFalse(game.getState().isWon());	
	}	
	
	@Test 
	public void testDealerPlays_dealerisBust_1() {
		//Player and dealer are bust, player looses.
		
		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 10)
				, new Card('D', 10), new Card('D', 8)));
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 13)
				, new Card('C', 6), new Card('C', 12)));
		
		game.getState().setGameover(false);
		game.Stand();
		game.setPlayer(new Hand(player));
		game.setDealer(new Hand(dealer));
		game.dealerPlays();
		assertFalse(game.isStand());
		assertTrue(game.getState().isGameover());
		assertFalse(game.getState().isWon());
	}
		
	@Test
	public void testDealerPlays_dealerisBust_2() {
		//Player is not bust, player wins.
		List<Card> player = new ArrayList<Card>(List.of(new Card('S', 10)
				, new Card('D', 10)));
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 13)
				, new Card('C', 6), new Card('C', 12)));
		
		game.getState().setGameover(false);
		game.Stand();
		game.setPlayer(new Hand(player));
		game.setDealer(new Hand(dealer));
		game.dealerPlays();
		assertFalse(game.isStand());
		assertTrue(game.getState().isGameover());
		assertTrue(game.getState().isWon());
	}
	
	@Test
	public void testDealerhand() {
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 13)
				, new Card('C', 6)));
		game.setDealer(new Hand(dealer));
		String before = " HK --";
		String after = " HK C6";
		
		//Stand is initialized in the constructor to be false.
		game.getState().setGameover(false);
		assertEquals(before, game.dealerHand());
		game.setStand(true);
		assertEquals(after, game.dealerHand());
		game.setStand(false);
		game.getState().setGameover(true);
		assertEquals(after, game.dealerHand());
	}
	
	@Test
	public void testPlayerValueisString() {
		List<Card> player = new ArrayList<Card>(List.of(new Card('H', 13)
				, new Card('C', 6)));
		game.setPlayer(new Hand(player));
		String value = "16";
		assertEquals(value, game.playerValue());
	}
	
	@Test
	public void testDealerValueisString() {
		List<Card> dealer = new ArrayList<Card>(List.of(new Card('H', 13)
				, new Card('C', 6)));
		game.setDealer(new Hand(dealer));
		String value = "16";
		assertEquals(value, game.dealerValue());
		//Stand is initialized in the constructor to be false.
		game.getState().setGameover(false);
		assertEquals("--", game.dealerValue());
		game.setStand(true);
		assertEquals(value, game.dealerValue());
	}
	
	@Test
	public void testSetHitcount() {
		game.setHitcount(0);
		assertEquals(0, game.getHitcount());
		game.setHitcount(2);
		assertEquals(2, game.getHitcount());
		
		IllegalArgumentException thrown = assertThrows( 
		           IllegalArgumentException.class,
		           () -> game.setHitcount(-1)
		);
		assertTrue(thrown.getMessage().contains("Cannot have a negative hitcount!"));
	}
	
	private boolean listComparison(List<Card> d1, List<Card> d2) { 
		if(d1.toString().equals(d2.toString())) {
			return true;
		}
		else {
			return false;
		}	
	}
	
	private boolean elementComparison(List<Card> d1, List<Card> d2) {
		Collection<String> s1 = new ArrayList<String>();
		Collection<String> s2 = new ArrayList<String>();
		for(Card c : d1) {
			s1.add(c.toString());
		}
		for(Card c : d2) {
			s2.add(c.toString());
		}
		if(s1.containsAll(s2)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private List<Card> unShuffledDeck() {
		CardDeck deck = new CardDeck(13);
		return deck.getCardDeck();
	}
}
