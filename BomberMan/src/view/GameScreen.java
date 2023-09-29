package view;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

/**
 * view of the entire game screen. includes game header and game board
 * @author Matteo
 *
 */
public class GameScreen extends StackPane {
	
	private static GameScreen gameScreen;
	
	private GameScreen() {
		this.setPrefHeight(BaseScene.getInstance().getHeight());
		this.setPrefWidth(BaseScene.getInstance().getWidth());
		
		
		this.getChildren().add(GameHeader.getInstance());
		this.getChildren().add(GameBoard.getInstance());
		GameScreen.setMargin(GameBoard.getInstance(), new Insets(Item.ITEM_HEIGHT * 2, 0, 0, 0));
	}
	
	/**
	 * singleton pattern
	 * @return the only existing instance of game screen, or creates one
	 */
	public static GameScreen getInstance() {
		if (gameScreen == null) {
			gameScreen = new GameScreen();
		}
		return gameScreen;
	}
	
}
