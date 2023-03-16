package blackjack.fxui;

import java.io.*;

import blackjack.model.BlackJack;

public interface IBlackJackFileReading {
	
	void saveGame(BlackJack game, OutputStream out);
	
	void saveGame(BlackJack game) throws IOException;
	
	BlackJack loadGame(InputStream in);
	
	BlackJack loadGame() throws IOException;
	
	void clearFile(BlackJack game, OutputStream output);
	
	void clearFile(BlackJack game) throws IOException;
	
}
