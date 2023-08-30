package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameHeader extends StackPane {
	
	public static GameHeader gameHeader;
	public static final int INITIAL_LIVES = 5;
	
	private ImageView baseHeader = new ImageView(new Image("images/-board-header/header-start.png"));
	private ImageView lives = new ImageView();
	
	private GameHeader() {
		Scene scene = BaseScene.getInstance();
		this.setPrefHeight(view.Item.ITEM_HEIGHT * 2);
		this.setPrefWidth(scene.getWidth());
		this.setAlignment(Pos.TOP_LEFT);
		this.getChildren().add(baseHeader);
		
		// set initial lives
		
		setLives(INITIAL_LIVES);
		GameHeader.setMargin(lives, new Insets(27,0,0,110));
		this.getChildren().add(lives);
		
		
	}
	
	public static GameHeader getInstance() {
		if (gameHeader == null) {
			gameHeader = new GameHeader();
		}
		return gameHeader;
	}

	public void setLives(int lives) {
		ImageView iv = new ImageView(new Image("images/-board-header/-numbers/" + lives + ".png"));
		this.lives = iv;
	}
}
