package blackjack.fxui;

import java.io.IOException;

import blackjack.model.BlackJack;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BlackJackController {
	
	@FXML private MenuButton Menu;
	@FXML private TextField Handvalueplayer, Handvaluedealer, Info;
	@FXML private TextArea Playerfield, Dealerfield;
	@FXML private Label Player, Dealer, Valuep, Valued;
	
	private final IBlackJackFileReading filesupport = new BlackJackFileSupport();
	private BlackJack game = new BlackJack();
	
	@FXML
	private void deal() {
		game.deal(); 
		Playerfield.setText(game.playerHand());
		Dealerfield.setText(game.dealerHand());
		Handvalueplayer.setText(game.playerValue());
		Handvaluedealer.setText(game.dealerValue());
		Info.setText(game.getInfo());
	}
	
	@FXML
	private void hit() {
		game.hit();
		Playerfield.setText(game.playerHand());
		Handvalueplayer.setText(game.playerValue());
		Info.setText(game.getInfo());
	}
	
	@FXML
	private void stand() {
		game.Stand();
		Dealerfield.setText(game.dealerHand());
		game.dealerPlays();
		Dealerfield.setText(game.dealerHand());
		Handvaluedealer.setText(game.dealerValue());
		Info.setText(game.getInfo());
	}
	
	@FXML
	private void statistics() {
		Info.setText(game.getStatistics());
	}
	
	@FXML
	private void saveGame() {
		try {
			filesupport.saveGame(game);
			Info.setText("Your game has been saved!");
		} catch (final IOException e) {
			e.getMessage();
			Info.setText("Your game failed to save!");
		}
	}
	
	@FXML 
	private void loadGame() {
		try {
			game = filesupport.loadGame();
			Info.setText(game.getInfo());
			Dealerfield.setText(game.dealerHand());
			Playerfield.setText(game.playerHand());
			Handvaluedealer.setText(game.dealerValue());
			Handvalueplayer.setText(game.playerValue());
		} catch (final IOException e) {
			e.getMessage();
			Info.setText("Your game failed to load!");
		}
	}
	
	@FXML 
	private void clearFile() {
		try {
			filesupport.clearFile(game);
			Info.setText("File has been cleared!");
		} catch (final IOException e) {
			e.getMessage();
			Info.setText("Failed to clear file!");
		}
	}
}
	