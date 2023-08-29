package view;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class GameHeader extends StackPane {
	
	
	public static GameHeader gameHeader;
	
	private GameHeader() {
		Scene scene = BaseScene.getInstance();
		this.setPrefHeight(view.Item.ITEM_HEIGHT * 2);
		this.setPrefWidth(scene.getWidth());
	}
	
	public static GameHeader getInstance() {
		if (gameHeader == null) {
			gameHeader = new GameHeader();
		}
		return gameHeader;
	}
}
