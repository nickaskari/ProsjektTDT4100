package blackjack.fxui;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class BlackJackApp extends Application{

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Blackjack");
		primaryStage.setScene(new Scene(FXMLLoader.load(BlackJackApp.class.getResource("blackjack_ui.fxml"))));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		BlackJackApp.launch(args);
	}

}
