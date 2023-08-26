package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BoardNewProfile extends StackPane {
	
	public static final int AVATAR_SIZE = 84;
	
	public BoardNewProfile() {
		Scene scene = controller.Main.getScene();
		this.setPrefHeight(scene.getHeight());
		this.setPrefWidth(scene.getWidth());
		Double prefHeight = this.getPrefHeight();
		Double prefWidth = this.getPrefWidth();
		this.setAlignment(Pos.TOP_LEFT);
		
		Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 50);
		
		
		createText("Nickname", new Insets(prefHeight * 0.1, 0, 0, prefWidth * 0.05), 20, Color.WHITE);
		createText("Avatar", new Insets(prefHeight * 0.1, 0, 0, prefWidth * 0.6), 20, Color.WHITE);
		createText("Played", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.05), 20, Color.WHITE);
		createText("Wins", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.3), 20, Color.GREEN);
		createText("Losses", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.5), 20, Color.RED);
		createText("Total Score", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.7), 20, Color.WHITE);
		
		createAvatar("black", new Insets(prefHeight * 0.2, 0 , 0, prefWidth * 0.6));
		createAvatar("blue", new Insets(prefHeight * 0.2, 0, 0, prefWidth * 0.6 + AVATAR_SIZE * 1.5));
		createAvatar("red", new Insets(prefHeight * 0.2 + AVATAR_SIZE * 1.5, 0, 0, prefWidth * 0.6));
		createAvatar("white", new Insets(prefHeight * 0.2 + AVATAR_SIZE * 1.5, 0, 0, prefWidth * 0.6 + AVATAR_SIZE * 1.5));
		
		
	}
	
	private void createText(String content, Insets insets, double size, Color color) {
		Text text = new Text();
		Font textFont = Font.font("Press Start 2P", size);
		
		text.setText(content);
		text.setFont(textFont);
		text.setFill(color);
		this.getChildren().add(text);
		BoardNewProfile.setMargin(text, insets);
		
	}
	
	private void createAvatar(String name, Insets insets) {
		Image avatar = new Image("images/-avatars/" + name + ".png");
		ImageView iv = new ImageView(avatar);
		this.getChildren().add(iv);
		BoardNewProfile.setMargin(iv, insets);
		
	}
	
	
	

}
