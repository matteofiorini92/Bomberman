package view;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

public class GameBoard extends StackPane {
	
	public static GameBoard gameBoard;
	
	private GameBoard() {
		this.setPrefHeight(view.BaseScene.getInstance().getHeight());
		this.setPrefWidth(view.BaseScene.getInstance().getWidth());
		
		
		this.getChildren().add(GameHeader.getInstance());
		this.getChildren().add(GameBody.getInstance());
		GameBoard.setMargin(GameBody.getInstance(), new Insets(view.Item.ITEM_HEIGHT * 2, 0, 0, 0));
	}
	
	public static GameBoard getInstance() {
		if (gameBoard == null) {
			gameBoard = new GameBoard();
		}
		return gameBoard;
	}
	
}
