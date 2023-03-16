package blackjack.model;

import java.util.*;

public class BlackJack {
	
	private CardDeck deck; 
	private Hand player;
	private Hand dealer;
	private GameState state;
	
	private boolean stand;
	private int hitcount;
	 
	public BlackJack() { 
		this.stand = false;
		this.hitcount = 0;
		this.deck = new CardDeck(13);
		this.player = new Hand(new ArrayList<Card>());
		this.dealer = new Hand(new ArrayList<Card>());
		this.state = new GameState(true, true, true, true, true, 0, 0);
	}
	
	public void deal() {
		if(state.isGameover()) {
			hitcount = 0;
			stand = false;
			state.setEven(false);
			state.setGameover(false);
			state.setBust(false);
			state.setBlackjack(false);
			for(int i = 1; i < 69; i++) {
				deck.shufflePerfectly();
			}
			player.clear();
			dealer.clear();
			
			player.addCard(deck.getCard(0));
			player.addCard(deck.getCard(1));
			dealer.addCard(deck.getCard(2));
			dealer.addCard(deck.getCard(3));
			
			if(isRealBlackJack(player)) {
				state.setBlackjack(true);
			}
		}
		else {
			throw new IllegalStateException("You have to finish your game!");
		}
	}
	
	public void hit() {
		if(!state.isGameover()) {
			if(isValidhitPlayer()) {
				state.setBlackjack(false);
				player.addCard(deck.getCard(4 + hitcount)); 
				hitcount++;
				// If the player hits and busts.
				if(!isValidhitPlayer()) { 
					state.setGameover(true);
					state.setWon(false);
					state.setBust(true);
				}
			}
		}
		else {
			throw new IllegalStateException("Your hit is invalid, because the game is not in session.");
		}
	}
	public void Stand() {
		if(!state.isGameover()) {
			this.stand = true;
			state.setBlackjack(false);
		}
		else {
			throw new IllegalStateException("You cannot stand when the game is not in session!");
		}
	}
	public void dealerPlays() {
		if(!state.isGameover()) {
			//Neither the dealer or the player are bust.
			if(stand == true && dealer.handValue() <= 21 && isValidhitPlayer()) { 
				//The dealer cannot hit with a value of 17 or more.
				if(!isValidhitDealer()) { 
					if(player.handValue() > dealer.handValue()) {
						stand = false;
						state.setGameover(true);
						state.setWon(true);
					}
					else if(player.handValue() < dealer.handValue()) {
						stand = false;
						state.setGameover(true);
						state.setWon(false);
					}
					else if(player.handValue() == dealer.handValue()) {
						// If the player has Blackjack, and not the dealer, player wins.
						// This advantage is only reserved for the player.
						if(!isRealBlackJack(dealer) && isRealBlackJack(player)) {
							stand = false;
							state.setGameover(true);
							state.setWon(true);
						}
						else
						stand = false;
						state.setGameover(true);
						state.setEven(true);
					}
				}
				//The dealer must hit when the dealer-value is under 17
				else if(isValidhitDealer()) { 
						dealer.addCard(deck.getCard(4 + hitcount));
						hitcount++;
						dealerPlays();	
				}
			}
			//The player is bust, and the dealer is not, so the player looses.
			else if(stand == true && dealer.handValue() <= 21 && !isValidhitPlayer()) {
				stand = false;
				state.setWon(false);
				state.setGameover(true);
				}
			
			//The dealer and the player are bust, and the player looses.
			else if(stand == true && dealer.handValue() > 21 && !isValidhitPlayer()) {
				stand = false;
				state.setWon(false);
				state.setGameover(true);
			}
			
			//Only the dealer is bust, so the player wins.
			else if(stand == true && dealer.handValue() > 21 && isValidhitPlayer()) {
				stand = false;
				state.setWon(true);
				state.setGameover(true);
			}
			
			else {
				throw new IllegalStateException("Player has not pressed stand yet!");
			}
		}
		else {
			throw new IllegalStateException("Dealer cannot play when the game is not in session!");
		}
	}
	
	//A real Blackjack is when the player gets an ace and a 10/a picture-card.
	private boolean isRealBlackJack(Hand hand) {
		List<Card> aces = new ArrayList<Card>(List.of(new Card('S', 1)
				, new Card('H', 1), new Card('D', 1), new Card('C', 1)));
		List<Card> highcards = new ArrayList<Card>();
			
		List<Integer> val = Arrays.asList(10, 11, 12, 13);
		char suits[] = new char[]{'S', 'H', 'D', 'C'}; 
		for(char suit : suits) {
			for(int face : val) {
				highcards.add(new Card(suit, face));
			}
		}
			
		int ace = 0;
		int highcard = 0;
		for(Card a : aces) {
			if(hand.getHand().toString().contains(a.toString())) {
				ace++;
			}
		}
		for(Card b : highcards) {
			if(hand.getHand().toString().contains(b.toString())) {
				highcard++;
			}
		}
		if(ace == 1 && highcard == 1 && hand.getHandsize() == 2) {
			return true;
		}
		else {
			return false;
		}
	}
 	
	public String playerHand() {
		return player.showHand();
	}
	public String dealerHand() {
		if(stand == false && !state.isGameover()) {
			return " " + dealer.showCard(0).toString() + " " + dealer.showCard(1).Hide();
		}
		else {
			return dealer.showHand();
		}
	}
	public String playerValue() {
		return Integer.toString(player.handValue());
	}
	public String dealerValue() {
		if(stand == false && !state.isGameover()) {
			return "--";
		}
		else {
			return Integer.toString(dealer.handValue());
		}
	}
	public String getInfo() {
		return state.getInfo();
	}
	public String getStatistics() {
		return state.getStatistics();
	}
	public CardDeck getDeck() {
		return deck;
	}
	public void setDeck(CardDeck deck) {
		this.deck = deck;
	}
	public Hand getPlayer() {
		return player;
	}
	public void setPlayer(Hand player) {
		this.player = player;
	}
	public Hand getDealer() {
		return dealer;
	}
	public void setDealer(Hand dealer) {
		this.dealer = dealer;
	}
	public GameState getState() {
		return state;
	}
	public void setState(GameState state) {
		this.state = state;
	}
	public boolean isStand() {
		return stand;
	}
	public void setStand(boolean stand) {
		this.stand = stand;
	}
	
	public int getHitcount() {
		return hitcount;
	}
	
	public void setHitcount(int hitcount) {
		if (hitcount >= 0) {
			this.hitcount = hitcount;
		}
		else {
			throw new IllegalArgumentException("Cannot have a negative hitcount!");
		}
	}

	private boolean isValidhitPlayer() {
		if(player.handValue() > 21) {
			return false;
		}
		return true;
	}
	private boolean isValidhitDealer() {
		if(dealer.handValue() >= 17) {
			return false;
		}
		return true;
	}
}
