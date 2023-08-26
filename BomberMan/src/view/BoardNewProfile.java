package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BoardNewProfile extends StackPane {
	
	public static final int AVATAR_SIZE = 84;
	public static final double TEXT_FIELD_WIDTH = 400.0;
	public static final double TEXT_FIELD_HEIGHT = 50.0;
    public static final double PROFILE_BUTTON_HEIGHT = 50.0;
    public static final double PROFILE_BUTTON_WIDTH = 250.0;
	
	private Double prefHeight;
	private Double prefWidth;
	
	
	public BoardNewProfile(int played, int wins, int losses, int totalScore) {
		
		Scene scene = controller.Main.getScene();
		this.setPrefHeight(scene.getHeight());
		this.setPrefWidth(scene.getWidth());
		prefHeight = this.getPrefHeight();
		prefWidth = this.getPrefWidth();
		this.setAlignment(Pos.TOP_LEFT);
		Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 50);
		
		
		createProfileTexts(played, wins, losses, totalScore);
		createAvatar();
		createButtons();

		
		TextField nicknameTextField = new TextField();
		nicknameTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		nicknameTextField.setMaxWidth(TEXT_FIELD_WIDTH);
		nicknameTextField.setPrefHeight(TEXT_FIELD_HEIGHT);
		Font nicknameFont = Font.font("Press Start 2P", 20);
		nicknameTextField.setFont(nicknameFont);
		
		this.getChildren().add(nicknameTextField);
		BoardNewProfile.setMargin(nicknameTextField, new Insets(prefHeight * 0.2, 0, 0, prefWidth * 0.05));
		
		
		
	}
	
	
	private void createProfileTexts(int played, int wins, int losses, int totalScore) {
		createText("Nickname", new Insets(prefHeight * 0.1, 0, 0, prefWidth * 0.05), 20, Color.WHITE);
		createText("Avatar", new Insets(prefHeight * 0.1, 0, 0, prefWidth * 0.6), 20, Color.WHITE);
		createText("Played", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.05), 20, Color.WHITE);
		createText(Integer.toString(played), new Insets(prefHeight * 0.7, 0, 0, prefWidth * 0.05), 20, Color.WHITE);
		createText("Wins", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.3), 20, Color.GREEN);
		createText(Integer.toString(wins), new Insets(prefHeight * 0.7, 0, 0, prefWidth * 0.3), 20, Color.GREEN);
		createText("Losses", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.5), 20, Color.RED);
		createText(Integer.toString(losses), new Insets(prefHeight * 0.7, 0, 0, prefWidth * 0.5), 20, Color.RED);
		createText("Total Score", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.7), 20, Color.WHITE);
		createText(Integer.toString(totalScore), new Insets(prefHeight * 0.7, 0, 0, prefWidth * 0.7), 20, Color.WHITE);
	}
	
	private void createAvatar() {
		createButtonWithImageBackground("black", new Insets(prefHeight * 0.2, 0 , 0, prefWidth * 0.6));
		createButtonWithImageBackground("blue", new Insets(prefHeight * 0.2, 0, 0, prefWidth * 0.6 + AVATAR_SIZE * 1.5));
		createButtonWithImageBackground("red", new Insets(prefHeight * 0.2 + AVATAR_SIZE * 1.5, 0, 0, prefWidth * 0.6));
		createButtonWithImageBackground("white", new Insets(prefHeight * 0.2 + AVATAR_SIZE * 1.5, 0, 0, prefWidth * 0.6 + AVATAR_SIZE * 1.5));
	}
	
	private void createButtons() {
		createProfileButton("Save", 0);
		createProfileButton("Start New Game", 1.5);
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
	
	private void createButtonWithImageBackground(String name, Insets insets) { 
		Button button = new Button();
		Image avatar = new Image("images/-avatars/" + name + ".png");
		ImageView imageView = new ImageView(avatar);
		button.setGraphic(imageView);
		button.setOpacity(0.5);
		BoardNewProfile.setMargin(button, insets);
		this.getChildren().add(button);		
	}
 	
	private void createProfileButton(String text, double spacing) {
    	Button profileButton = new Button();
    	profileButton.setPrefWidth(PROFILE_BUTTON_WIDTH);
    	profileButton.setPrefHeight(PROFILE_BUTTON_HEIGHT);
    	
    	
        Font profileFont = Font.font("Press Start 2P", 15);
        profileButton.setFont(profileFont);
    	profileButton.setText(text);
    	
    	BoardNewProfile.setMargin(profileButton, new Insets(prefHeight * 0.8, 0, 0, prefWidth * 0.1 + PROFILE_BUTTON_WIDTH * spacing));  
    	this.getChildren().add(profileButton);
	}
	
	
	

}
