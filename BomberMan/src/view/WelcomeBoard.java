package view;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class WelcomeBoard extends StackPane {
    private static Text welcomeText = new Text();


    public WelcomeBoard() {
    	
        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 50);
        Font font = Font.font("Press Start 2P", 20);
        welcomeText.setFont(font);
        welcomeText.setFill(Color.WHITE);
        
    }
    
    public void greet() {
    	model.Player player = model.Player.getInstance();
    	if (player.getName() != null) {
    		welcomeText.setText("Welcome back,\n" + player.getName());
    	} else {
    		welcomeText.setText("Welcome!");
    	}
    	this.setAlignment(Pos.TOP_CENTER);
    	this.setLayoutY(this.getPrefHeight() * 0.3);
    	this.getChildren().add(welcomeText);    	
    }
}
