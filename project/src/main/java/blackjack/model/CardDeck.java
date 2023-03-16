package blackjack.model;

import java.util.*;

public class CardDeck{
	
	private List<Card> deckofcards = new ArrayList<Card>(); 
	private char suits[] = new char[]{'S', 'H', 'D', 'C'}; 
	
	
	public CardDeck(int n) { 
		 if(n > 13 || n < 0) {
			 throw new IllegalArgumentException("Cannot have negative cards or more than 13 for each suit."); 
		 }
		 else if(n == 0) {
			 List<Card> emptydeck = new ArrayList<Card>();
			 deckofcards = emptydeck;
		 }
		 else {
		 for (char suit : suits) { 
	            for (int i = 0; i < n; i++) { 
	                deckofcards.add(new Card(suit, i + 1)); 
	            }
	        }
		 }
	}
	
	public List<Card> getCardDeck(){
		return deckofcards;
	}

	public int getCardCount() {
		return deckofcards.size(); 
	}
	
	public void addCardManually(Card card){ 
		List<String> s = new ArrayList<String>();
		s.add(card.toString());
		for (Card e : deckofcards) {
			s.add(e.toString());
		}
		Set<String> sorted = new HashSet<String>(s);
		if(sorted.size() == s.size()) {
			deckofcards.add(card);
		}
		else {
			throw new IllegalArgumentException("You cannot have duplicate cards in a carddeck!");
		}
	}
	
	//I'm aware that I don't need to specify these exceptions, since it is included in the get-method.
	// I just found these exceptions to be practical when coding the game.
	public Card getCard(int n) {
		if((n >= 0 && n < getCardCount()) && !deckofcards.isEmpty()) {
			return deckofcards.get(n); 
		}
		else if(deckofcards.isEmpty()) {
			throw new IllegalArgumentException("You have an empty deck!");
		}
		else {
			throw new IndexOutOfBoundsException("There is no card at this index!"); 
		}
	}
	
	public List<Card> shufflePerfectly() { 
		Collections.shuffle(deckofcards);
		return deckofcards;		
	}
	
}
