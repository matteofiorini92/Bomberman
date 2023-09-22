package view;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

public class GameScreen extends StackPane {
	
	public static GameScreen gameScreen;
	
	private GameScreen() {
		this.setPrefHeight(view.BaseScene.getInstance().getHeight());
		this.setPrefWidth(view.BaseScene.getInstance().getWidth());
		
		
		this.getChildren().add(GameHeader.getInstance());
		this.getChildren().add(GameBoard.getInstance());
		GameScreen.setMargin(GameBoard.getInstance(), new Insets(view.Item.ITEM_HEIGHT * 2, 0, 0, 0));
	}
	
	public static GameScreen getInstance() {
		if (gameScreen == null) {
			gameScreen = new GameScreen();
		}
		return gameScreen;
	}
	
}
