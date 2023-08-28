package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BoardProfileLookUp extends StackPane  {

	public static final double TEXT_FIELD_WIDTH = 400.0;
	public static final double TEXT_FIELD_HEIGHT = 50.0;
	public static final double BUTTON_HEIGHT = 50.0;
	
	private Double prefHeight;
	private Double prefWidth;
	
	public BoardProfileLookUp() {

		Scene scene = BaseScene.getInstance();
		this.setPrefHeight(scene.getHeight());
		this.setPrefWidth(scene.getWidth());
		prefHeight = this.getPrefHeight();
		prefWidth = this.getPrefWidth();
		this.setAlignment(Pos.TOP_LEFT);
		Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 50);
		
		// Nickname text
		
		Text text = new Text();
		Font textFont = Font.font("Press Start 2P", 20);
		text.setText("Nickname");
		text.setFont(textFont);
		text.setFill(Color.WHITE);
		this.getChildren().add(text);
		BoardProfileLookUp.setMargin(text, new Insets(prefHeight * 0.1, 0, 0, prefWidth * 0.05));
		
		// error message
		
		Text errorMessage = new Text();
		Font errorFont = Font.font("Press Start 2P", 7);
		errorMessage.setText("This nickname does not exist.");
		errorMessage.setFont(errorFont);
		errorMessage.setFill(Color.PALEVIOLETRED);
		this.getChildren().add(errorMessage);
		errorMessage.setVisible(false);
		errorMessage.setId("ERR_NICKNAME_DOES_NOT_EXIST");
		BoardProfileLookUp.setMargin(errorMessage, new Insets(prefHeight * 0.3, 0, 0, prefWidth * 0.05));
		
		// nickname text field
		
		TextField nicknameTextField = new TextField();
		nicknameTextField.setPrefWidth(TEXT_FIELD_WIDTH);
		nicknameTextField.setMaxWidth(TEXT_FIELD_WIDTH);
		nicknameTextField.setPrefHeight(TEXT_FIELD_HEIGHT);
		Font nicknameFont = Font.font("Press Start 2P", 20);
		nicknameTextField.setFont(nicknameFont);
		nicknameTextField.setId("NICKNAME_LOOKUP");
		
		this.getChildren().add(nicknameTextField);
		BoardProfileLookUp.setMargin(nicknameTextField, new Insets(prefHeight * 0.2, 0, 0, prefWidth * 0.05));
		
		// search button
		
    	Button searchButton = new Button();
    	searchButton.setPrefHeight(BUTTON_HEIGHT);
        Font profileFont = Font.font("Press Start 2P", 15);
        searchButton.setFont(profileFont);
    	searchButton.setText("Search");
    	searchButton.setId("SEARCH");
    	BoardProfileLookUp.setMargin(searchButton, new Insets(prefHeight * 0.2, 0, 0, prefWidth * 0.7));  
    	this.getChildren().add(searchButton);
    	
    	searchButton.setOnMouseClicked(event -> new controller.LoadExistingPlayerScreen(nicknameTextField.getText().toLowerCase()));
		
	}


	
}
