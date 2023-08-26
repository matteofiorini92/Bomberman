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
	private model.Avatar selectedAvatar = model.Avatar.WHITE;
	
	
	public BoardNewProfile(int played, int wins, int losses, int totalScore) {
		
		Scene scene = controller.Main.getScene();
		this.setPrefHeight(scene.getHeight());
		this.setPrefWidth(scene.getWidth());
		prefHeight = this.getPrefHeight();
		prefWidth = this.getPrefWidth();
		this.setAlignment(Pos.TOP_LEFT);
		Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 50);
		
		
		createProfileTexts(wins, losses, totalScore);
		createAvatar();
		createButtons();

		
		TextField nicknameTextField = new TextField();
		nicknameTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		nicknameTextField.setMaxWidth(TEXT_FIELD_WIDTH);
		nicknameTextField.setPrefHeight(TEXT_FIELD_HEIGHT);
		Font nicknameFont = Font.font("Press Start 2P", 20);
		nicknameTextField.setFont(nicknameFont);
		nicknameTextField.setId("NICKNAME");
		
		this.getChildren().add(nicknameTextField);
		BoardNewProfile.setMargin(nicknameTextField, new Insets(prefHeight * 0.2, 0, 0, prefWidth * 0.05));
		
	}
	
	public model.Avatar getSelectedAvatar() { return selectedAvatar; }
	
	
	private void createProfileTexts(int wins, int losses, int totalScore) {
		createText("Nickname", new Insets(prefHeight * 0.1, 0, 0, prefWidth * 0.05), 20, Color.WHITE);
		createText("Avatar", new Insets(prefHeight * 0.1, 0, 0, prefWidth * 0.6), 20, Color.WHITE);
		createText("Played", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.05), 20, Color.WHITE);
		createText(Integer.toString(wins + losses), new Insets(prefHeight * 0.7, 0, 0, prefWidth * 0.05), 20, Color.WHITE);
		createText("Wins", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.3), 20, Color.GREEN);
		createText(Integer.toString(wins), new Insets(prefHeight * 0.7, 0, 0, prefWidth * 0.3), 20, Color.GREEN);
		createText("Losses", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.5), 20, Color.RED);
		createText(Integer.toString(losses), new Insets(prefHeight * 0.7, 0, 0, prefWidth * 0.5), 20, Color.RED);
		createText("Total Score", new Insets(prefHeight * 0.65, 0, 0, prefWidth * 0.7), 20, Color.WHITE);
		createText(Integer.toString(totalScore), new Insets(prefHeight * 0.7, 0, 0, prefWidth * 0.7), 20, Color.WHITE);
		
		
		Text errorMessage = createText("This nickname is taken. Please choose a different one", new Insets(prefHeight * 0.3, 0, 0, prefWidth * 0.05), 7, Color.PALEVIOLETRED);
		errorMessage.setVisible(false);
		errorMessage.setId("ERR_NICKNAME_TAKEN");
	}
	
	private void createAvatar() {
		Button whiteAvatar = createButtonWithImageBackground("white", new Insets(prefHeight * 0.2, 0 , 0, prefWidth * 0.6), "WHITE");
		createButtonWithImageBackground("blue", new Insets(prefHeight * 0.2, 0, 0, prefWidth * 0.6 + AVATAR_SIZE * 1.5), "BLUE");
		createButtonWithImageBackground("red", new Insets(prefHeight * 0.2 + AVATAR_SIZE * 1.5, 0, 0, prefWidth * 0.6), "RED");
		createButtonWithImageBackground("black", new Insets(prefHeight * 0.2 + AVATAR_SIZE * 1.5, 0, 0, prefWidth * 0.6 + AVATAR_SIZE * 1.5), "BLACK");
		whiteAvatar.setOpacity(1);
	}
	
	private void createButtons() {
		createProfileButton("Save", 0, "SAVE_PROFILE");
		Button newGameButton = createProfileButton("Start New Game", 1.5, "NEW_GAME");
		newGameButton.setDisable(true);
	}
	
	private Text createText(String content, Insets insets, double size, Color color) {
		Text text = new Text();
		Font textFont = Font.font("Press Start 2P", size);
		text.setText(content);
		text.setFont(textFont);
		text.setFill(color);
		this.getChildren().add(text);
		BoardNewProfile.setMargin(text, insets);
		return text;
	}
	
	private Button createButtonWithImageBackground(String name, Insets insets, String id) { 
		Button button = new Button();
		Image avatar = new Image("images/-avatars/" + name + ".png");
		ImageView imageView = new ImageView(avatar);
		button.setGraphic(imageView);
		button.setOpacity(0.5);
		button.setId(id);
		BoardNewProfile.setMargin(button, insets);
		this.getChildren().add(button);
		
		
		button.setOnMouseClicked(event -> {
			this.getChildren().stream()
				.filter(node -> node instanceof Button)
				.filter(bttn -> ((Button) bttn).getId().matches("BLACK|BLUE|RED|WHITE"))
				.forEach(avatarButton -> avatarButton.setOpacity(0.5));
			button.setOpacity(1);
			selectedAvatar = model.Avatar.valueOf(id);
		});
		
		return button;
		
	}
 	
	private Button createProfileButton(String text, double spacing, String id) {
    	Button profileButton = new Button();
    	profileButton.setPrefWidth(PROFILE_BUTTON_WIDTH);
    	profileButton.setPrefHeight(PROFILE_BUTTON_HEIGHT);
    	
    	
        Font profileFont = Font.font("Press Start 2P", 15);
        profileButton.setFont(profileFont);
    	profileButton.setText(text);
    	profileButton.setId(id);
    	
    	BoardNewProfile.setMargin(profileButton, new Insets(prefHeight * 0.8, 0, 0, prefWidth * 0.1 + PROFILE_BUTTON_WIDTH * spacing));  
    	this.getChildren().add(profileButton);
    	return profileButton;
	}



	
	
	

}
