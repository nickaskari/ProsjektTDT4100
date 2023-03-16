package blackjack.model;

import java.util.*;

public class Hand {
	
	private List<Card> hand = new ArrayList<Card>();
	
	public Hand(List<Card> hand) { 
		List<String> actual = new ArrayList<String>();
		for (Card e : hand) {
			actual.add(e.toString());
		}
		Set<String> sorted = new HashSet<String>(actual);
		if(sorted.size() == actual.size()) {
			this.hand = hand;
		}
		else {
			throw new IllegalArgumentException("You cannot have duplicate cards in a hand!");
		}
	}
	
	public List<Card> getHand(){
		return hand;
	}
	
	public int getHandsize() {
		return hand.size();
	}
	
	public String showHand() {
		String s = "";
		for(int i = 0; i < hand.size(); i++) {
			s = s + " " + hand.get(i).toString();
		}
		return s;
	}
	
	//Again, I found these specified exceptions to be practical
	public Card showCard(int n) {
		if((n >= 0 && n < hand.size()) && !hand.isEmpty()) {
			return hand.get(n); 
		}
		else if(hand.isEmpty()) {
			throw new IllegalArgumentException("You have an empty hand!");
		}
		else {
			throw new IndexOutOfBoundsException("There is no card at this index!"); 
		}
	}
	
	public void addCard(Card card) {
		if(!hand.toString().contains(card.toString())) {
			hand.add(card);
		}
		else {
			throw new IllegalArgumentException("This card is already in the hand!");
		}
	}
	
	public void clear() {
		hand.clear();
	}
	
	public int handValue() {		
		int value = 0;
		int aces = 0;
		
		for(Card i : hand) {
			value += i.getValue();
		}
		for(Card i : hand) {
			if(i.getFace() == 1) {
				aces++;
			}
		}
		if(value > 21) { 
			if(aces == 1) {
				value = value - 10;
			}
			else if(aces == 2) {
				if(value > 31) {
					value = value - 20;
				}
				else {
					value = value- 10;
				}
			}
			else if(aces == 3) {
				if(value > 41) {
					value = value - 30;
				}
				else {
					value = value - 20;
				}
			}
			else if(aces == 4) {
				if(value > 51) {
					value = value - 40;
				}
				else {
					value = value - 30;
				}
			}
		}
		return value;	
	}
}
