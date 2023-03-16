package blackjack.model;

import java.util.*;

public class Card {

	private char suit;
	private int face;

	public Card(char suit, int face) {
		if (suit == 'S' || suit == 'H' || suit == 'D' || suit == 'C') {
			this.suit = suit;
		} else {
			throw new IllegalArgumentException("Invalid suit!");
		}
		if (face >= 1 && face <= 13) {
			this.face = face;
		} 
		else {
			throw new IllegalArgumentException("Invalid face value!");
		}
	}

	public char getSuit() {
		return suit;
	}

	public int getFace() {
		return face;
	}
	
	public int getValue() {
		if(face == 11 || face == 12 || face == 13) {
			return 10;
		}
		if(face == 1) {
			return 11;
		}
		else {
			return face;
		}
	}
	
	public String toString() { 
		List<Integer> val = Arrays.asList(1, 13, 12, 11);
		char highface[] = new char[]{'A', 'K', 'Q', 'J'}; 
		
		for(int e = 0; e < 4; e++) {
			if(face == val.get(e)) {
				return "" + suit + highface[e];
			}
		}
		return "" + suit + face;
	}
	
	public String Hide() {
		return "--";
	}

}

