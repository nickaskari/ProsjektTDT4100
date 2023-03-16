package blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import blackjack.fxui.BlackJackFileSupport;

public class FileSupportTest{
	
	private BlackJack getGame() {
		BlackJack game = new BlackJack();
		game.setDeck(new CardDeck(1));
		game.setHitcount(0);
		game.setStand(false);
		List<Card> dealer = new ArrayList<>(List.of(new Card('S', 1)
				, new Card('H', 1)));
		game.setDealer(new Hand(dealer));
		List<Card> player = new ArrayList<>(List.of(new Card('D', 1)
				, new Card('C', 1)));
		game.setPlayer(new Hand(player));
		game.setState(new GameState(false, false, false, false, false, 2, 3));
		return game;
	}
	
	private String getStringRep() {
		return "[SA, HA, DA, CA]\n0\nfalse\n[SA, HA]\n[DA, CA]\n[false, false, false, false, false, 2, 3]\n";
	}
	
	private Iterator<String> iterator(BlackJack game) {
		String deck = game.getDeck().getCardDeck().toString();
		String hitcount = Integer.toString(game.getHitcount());
		String stand = Boolean.toString(game.isStand());
		String dealer = game.getDealer().getHand().toString();
		String player = game.getPlayer().getHand().toString();
		String state = game.getState().getStateList().toString();
		
		List<String> filelist = new ArrayList<String>(List.of(deck
				, hitcount, stand, dealer, player, state));
		return filelist.iterator();
	}
	
	private String removeWhiteSpaces(String input) {
	    return input.replaceAll("\\s+", "");
	}
	
	@Test
	public void testSaveGame() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BlackJack game = new BlackJack();
		BlackJackFileSupport fs = new BlackJackFileSupport();
		IllegalStateException thrown = assertThrows(
		           IllegalStateException.class,
		           () -> fs.saveGame(game, os)
		);
		assertTrue(thrown.getMessage().contains("You cannot save, when you have not played!"));
		BlackJack game1 = getGame();
		fs.saveGame(game1, os);
		
		String actual = new String(os.toByteArray());
		String expected = getStringRep();
		
		assertEquals(removeWhiteSpaces(expected), removeWhiteSpaces(actual));
	}
	
	@Test
	public void testLoadGame() throws UnsupportedEncodingException{
		InputStream is = new ByteArrayInputStream(getStringRep().getBytes("UTF-8"));
		
		BlackJackFileSupport fs = new BlackJackFileSupport();
		BlackJack actual = fs.loadGame(is);
		BlackJack expected = getGame();
		
		var actualIterator = iterator(actual);
		var expectedIterator = iterator(expected);
		
		int i = 0;
		while (expectedIterator.hasNext()) {
			try {
				assertEquals(expectedIterator.next(), actualIterator.next(), "Element did not match at: " + i);
			} catch(IndexOutOfBoundsException e) {
				fail("List has wrong number of elememnts!");
			}
			i++;
		}
	}
	
	@Test
	public void testClearFile() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BlackJack game = getGame();
		BlackJackFileSupport fs = new BlackJackFileSupport();
		fs.clearFile(game, os);
		
		String actual = new String(os.toByteArray());
		String expected = "";
		
		assertEquals(removeWhiteSpaces(expected), removeWhiteSpaces(actual));
	}

}
