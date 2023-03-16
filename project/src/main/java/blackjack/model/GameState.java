package blackjack.model;

import java.util.*;

public class GameState {
	
	private boolean gameover;
	private boolean won;
	private boolean even;
	private boolean bust;
	private boolean blackjack;
	private int wincount;
	private int losscount;

	public GameState(boolean gameover, boolean won, boolean even, boolean bust, boolean blackjack, int wincount, int losscount) {
		this.gameover = gameover;
		this.won = won;
		this.even = even;
		this.bust = bust;
		this.blackjack = blackjack;
		
		if(wincount >= 0 && losscount >= 0) {
			this.wincount = wincount;
			this.losscount = losscount;
		}
		else {
			throw new IllegalArgumentException("Cannot win or loose negative times!");
		}
	}

	public List<String> getStateList() {
		List<String> state = new ArrayList<>();
		state.add(String.valueOf(gameover));  
		state.add(String.valueOf(won)); 
		state.add(String.valueOf(even)); 
		state.add(String.valueOf(bust));
		state.add(String.valueOf(blackjack));
		state.add(Integer.toString(wincount));
		state.add(Integer.toString(losscount));  
		
		return state;
	}
	
	public void setGameover(boolean gameover) {
		this.gameover = gameover;
	}
	
	public void setWon(boolean won) {
		if(won == false) {
			this.won = won;
			losscount++;
		} 
		else {
			wincount++;
			this.won = won;
		}
	}
	
	public void setEven(boolean even) {
		this.even = even;
	}
	
	public void setBust(boolean bust) {
		this.bust = bust;
	}

	public void setBlackjack(boolean blackjack) {
		this.blackjack = blackjack;
	}
	
	public boolean isGameover() {
		return gameover;
	}
	
	public boolean isEven() {
		return even;
	}

	public boolean isBust() {
		return bust;
	}
	
	public boolean isBlackjack() {
		return blackjack;
	}
	
	public boolean isWon() {
		return won;
	}

	public int getWincount() { 
		return wincount;
	}

	public int getLosscount() { 
		return losscount;
	}
	
	public String getInfo() {
		if(gameover == true && won == true && even == false && bust == false && blackjack == false) {
			return "Player won this round!";
		}
		else if(gameover == true && won == false && even == false && bust == false && blackjack == false) {
			return "Dealer won this round!";
		}
		else if(gameover == true && won == false && even == false && bust == true && blackjack == false) {
			return "You are bust!";
		}
		else if(gameover == true && even == true && bust == false && blackjack == false){
			return "The play was even!";
		}
		else if(blackjack == true) {
			return "Blackjack!";
		}
		else {
			return "";
		}
	}
	
	public String getStatistics() {
		if(wincount > 0 || losscount > 0) {
			return "W: " + wincount + "   L: " + losscount + "   lvl: " + getLevel();
		}
		else {
			return "Play more for statistics.";
		}
	}
	
	private String getLevel() {
		if(wincount > 0 && losscount > 0) {
			int kd = wincount / losscount;
			if(kd >= 3) {
				return "Greek God";
			}
			else if(kd > 1 && kd < 3) {
				return "Pimp";
			}
			else if(kd <= 1 && kd > 0.5) {
				return "Normie";
			}
			else {
				return "Peasant";
			}
		}
		else {
			return "--";
		}
	}
}
