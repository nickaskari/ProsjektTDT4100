package blackjack.fxui;

import java.io.*;
import java.util.*;
import java.nio.file.*;

import blackjack.model.*;

public class BlackJackFileSupport implements IBlackJackFileReading {
	
	public final static String Save_Folder = "blackjack";
	
	private Path getFolderPath() {
		return Path.of(System.getProperty("user.home"), "Save game", "blackjack");
	}
	
	private boolean ensureSaveFolder() {
		try {
			Files.createDirectories(getFolderPath());
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	private Path getPath() {
		return getFolderPath().resolve(Save_Folder);
	}
	
	public void saveGame(BlackJack game, OutputStream output) {
		List<String> invalidstate = new ArrayList<>(List.of("true", "true", "true", "true", "true"
				, "0", "0"));
		if(!game.getState().getStateList().equals(invalidstate)) {
			try (var writer = new PrintWriter(output)) {
				writer.println(game.getDeck().getCardDeck());
				writer.println(game.getHitcount());
				writer.println(game.isStand());
				writer.println(game.getDealer().getHand());
				writer.println(game.getPlayer().getHand());
				writer.println(game.getState().getStateList());
			}
		}
		else {
			throw new IllegalStateException("You cannot save, when you have not played!");
		}
	}
	
	public void saveGame(BlackJack game) throws IOException {
		ensureSaveFolder();
		try (var output = new FileOutputStream(getPath().toFile())) {
			saveGame(game, output);
		}
	}

	public BlackJack loadGame(InputStream input) {
		BlackJack game = null;
		try (Scanner scanner = new Scanner(input)) { 
			game = new BlackJack();
			
			CardDeck deck = new CardDeck(0);
			String[] loaddeck = scanner.nextLine().replaceAll("\\[", "").replaceAll("\\]","").replaceAll(" ", "").split(","); 
			HashMap<String, Integer> val = new HashMap<String, Integer>();
			val.put("A", 1);
			val.put("K", 13);
			val.put("Q", 12); 
			val.put("J", 11);
			
			int face = 0;
			for(int i = 0; i < loaddeck.length; i++) {
				char suit = loaddeck[i].charAt(0);
				String facestring = loaddeck[i].substring(1, loaddeck[i].length());
					
				if(val.containsKey(facestring)) { 
					face = val.get(facestring); 
					Card newcard = new Card(suit, face);
					deck.addCardManually(newcard); 
					}
				else {		
					face = Integer.parseInt(facestring);
					Card newcard = new Card(suit, face);
					deck.addCardManually(newcard);
				}		 	
			} 

			game.setDeck(deck);
			game.setHitcount(Integer.parseInt(scanner.nextLine()));
			game.setStand(Boolean.parseBoolean(scanner.nextLine()));
			
			String[] loaddealer = scanner.nextLine().replaceAll("\\[", "").replaceAll("\\]","").replaceAll(" ", "").split(",");
			String[] loadplayer = scanner.nextLine().replaceAll("\\[", "").replaceAll("\\]","").replaceAll(" ", "").split(",");
			
			int dealerface = 0, playerface = 0;
			Hand dealer = new Hand(new ArrayList<Card>()), player = new Hand(new ArrayList<Card>());
			
				for(int i = 0; i < loaddealer.length; i++) {
					char dealersuit = loaddealer[i].charAt(0);
					String dfs = loaddealer[i].substring(1, loaddealer[i].length()); 
					
					if(val.containsKey(dfs)) {
						dealerface = val.get(dfs);
						Card dealercard = new Card(dealersuit, dealerface);
						dealer.addCard(dealercard);
						}
					else {
						dealerface = Integer.parseInt(dfs);
						Card dealercard = new Card(dealersuit, dealerface);
						dealer.addCard(dealercard); 	
					}
				}
					
				for(int i = 0; i < loadplayer.length; i++) {
					char playersuit = loadplayer[i].charAt(0);
					String pfs = loadplayer[i].substring(1, loadplayer[i].length());
					
					if(val.containsKey(pfs)) {
						playerface = val.get(pfs);
						Card playercard = new Card(playersuit, playerface);
						player.addCard(playercard); 
						}
					else {
						playerface = Integer.parseInt(pfs);
						Card playercard = new Card(playersuit, playerface);
						player.addCard(playercard); 	
					}
				}	
			game.setDealer(dealer);
			game.setPlayer(player);
			
			String[] scannerstate = scanner.nextLine().replaceAll("\\[", "").replaceAll("\\]","").replaceAll(" ", "").split(",");
			
			boolean gameover = Boolean.parseBoolean(scannerstate[0]); 
			boolean won = Boolean.parseBoolean(scannerstate[1]);
			boolean even = Boolean.parseBoolean(scannerstate[2]);
			boolean bust = Boolean.parseBoolean(scannerstate[3]);
			boolean blackjack = Boolean.parseBoolean(scannerstate[4]);
			int wincount = Integer.parseInt(scannerstate[5]);
			int losscount = Integer.parseInt(scannerstate[6]);
			
			game.setState(new GameState(gameover, won, even, bust, blackjack, wincount, losscount));
		}
		return game;
	}

	public BlackJack loadGame() throws IOException {
		var blackjackPath = getPath();
		try (var input = new FileInputStream(blackjackPath.toFile())){
			return loadGame(input);
		}
	}
	
	public void clearFile(BlackJack game, OutputStream output) {
			try (var writer = new PrintWriter(output)) {
				writer.println("");
			}
	}
	
	public void clearFile(BlackJack game) throws IOException {
		ensureSaveFolder();
		try (var output = new FileOutputStream(getPath().toFile())) {
			clearFile(game, output);
		}
	}
}
