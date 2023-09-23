package view.Boards;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.BaseScene;

/**
 * view of a final board (e.g. continue, defeat, victory)
 * @author Matteo
 *
 */
public abstract class End extends StackPane {

	
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 40;

	private Double prefHeight;
	private Double prefWidth;
	
	public End(
			String mainText,
			String firstButtonText,
			int firstButtonSize,
			EventHandler<MouseEvent> firstButtonEventHandler,
			String secondButtonText,
			int secondButtonSize,
			EventHandler<MouseEvent> secondButtonEventHandler
			) {
	
		Scene scene = BaseScene.getInstance();
		this.setPrefHeight(scene.getHeight());
		this.setPrefWidth(scene.getWidth());
		prefHeight = this.getPrefHeight();
		prefWidth = this.getPrefWidth();
		this.setAlignment(Pos.TOP_CENTER);
		Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 50);
		
		
		Text text = new Text();
		Font textFont = Font.font("Press Start 2P", 40);
		text.setText(mainText);
		text.setFont(textFont);
		text.setFill(Color.WHITE);
		this.getChildren().add(text);
		Insets insets = new Insets(prefHeight * 0.3, 0, 0, 0);
		NewProfile.setMargin(text, insets);
		
		
    	Button firstButton = new Button();
    	firstButton.setPrefWidth(BUTTON_WIDTH);
    	firstButton.setPrefHeight(BUTTON_HEIGHT);
    	
    	
        Font buttonFont = Font.font("Press Start 2P", firstButtonSize);
        firstButton.setFont(buttonFont);
        firstButton.setText(firstButtonText);
    	
    	NewProfile.setMargin(firstButton, new Insets(prefHeight * 0.6, 0, 0, -BUTTON_WIDTH*1.5));  
    	this.getChildren().add(firstButton);
    	
    	firstButton.setOnMouseClicked(firstButtonEventHandler);
    	
    	
    	
    	
    	Button secondButton = new Button();
    	secondButton.setPrefWidth(BUTTON_WIDTH);
    	secondButton.setPrefHeight(BUTTON_HEIGHT);
    	
    	buttonFont = Font.font("Press Start 2P", secondButtonSize);
    	secondButton.setFont(buttonFont);
    	secondButton.setText(secondButtonText);
    	
    	NewProfile.setMargin(secondButton, new Insets(prefHeight * 0.6, 0, 0, BUTTON_WIDTH*1.5));  
    	this.getChildren().add(secondButton);
    	
    	secondButton.setOnMouseClicked(secondButtonEventHandler);
		
		
		
	}
}
