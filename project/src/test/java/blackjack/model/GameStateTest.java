package blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GameStateTest {
	
	GameState state;
	
	@BeforeEach
	public void setup() {
		
		state = new GameState(true, false, true, false, false, 3, 0);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(true, state.isGameover());
		assertEquals(false, state.isWon());
		assertEquals(true, state.isEven());
		assertEquals(false, state.isBust());
		assertEquals(false, state.isBlackjack());
		assertEquals(3, state.getWincount());
		assertEquals(0, state.getLosscount());
		
		assertThrows(IllegalArgumentException.class, () -> {
			new GameState(true, false, true, false, false, -3, 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new GameState(true, false, true, false, false, 3, -9);
		});
	}
	
	@Test
	public void testGetState() { 
		List<String> statelist = new ArrayList<>(List.of("true", "false", "true", "false"
				, "false", "3", "0"));
		assertTrue(listComparison(statelist, state.getStateList()));
		List<String> statelist2 = new ArrayList<>(List.of("true", "true", "true", "false"
				, "true", "3", "99"));
		assertFalse(listComparison(statelist2, state.getStateList()));
	}
	
	@Test
	public void testSetWon(){
		state.setWon(true);
		assertEquals(true, state.isWon());
		assertEquals(4, state.getWincount());
		
		state.setWon(false);
		assertEquals(false, state.isWon());
		assertEquals(1, state.getLosscount());
	}
	
	@Test
	public void testGetInfo() {
		assertEquals("The play was even!", state.getInfo());
		
		state = new GameState(true, false, false, true, false, 3, 0);
		assertEquals("You are bust!", state.getInfo());
		
		state = new GameState(true, false, false, false, false, 3, 0);
		assertEquals("Dealer won this round!", state.getInfo());
		
		state = new GameState(true, true, false, false, false, 3, 0);
		assertEquals("Player won this round!", state.getInfo());
		
		state = new GameState(true, true, false, false, true, 3, 0);
		assertEquals("Blackjack!", state.getInfo());
		
		state = new GameState(false, false, false, true, false, 3, 0);
		assertEquals("", state.getInfo());
	}
	
	@Test
	public void testGetStatistics() {
		state = new GameState(true, false, true, false, false, 3, 0);
		assertEquals("W: 3   L: 0   lvl: --", state.getStatistics());
		
		state = new GameState(true, false, true, false, false, 3, 1);
		assertEquals("W: 3   L: 1   lvl: Greek God", state.getStatistics());
		
		state = new GameState(true, false, true, false, false, 2, 1);
		assertEquals("W: 2   L: 1   lvl: Pimp", state.getStatistics());
		
		state = new GameState(true, false, true, false, false, 3, 3);
		assertEquals("W: 3   L: 3   lvl: Normie", state.getStatistics());
		
		state = new GameState(true, false, true, false, false, 1, 6);
		assertEquals("W: 1   L: 6   lvl: Peasant", state.getStatistics());
		
		state = new GameState(true, false, true, false, false, 0, 0);
		assertEquals("Play more for statistics.", state.getStatistics());
	}
	
	private boolean listComparison(List<String> d1, List<String> d2) { 
		if(d1.equals(d2)) {
			return true;
		}
		return false;
	}
}
