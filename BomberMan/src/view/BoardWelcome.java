package view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BoardWelcome extends StackPane {
	
    private static Text welcomeText = new Text();
    public static final Path PLAYER_PROFILE_FOLDER = Path.of("resources/playerProfiles");
    public static final double PROFILE_BUTTON_HEIGHT = 50.0;
    public static final double PROFILE_BUTTON_WIDTH = 300.0;
    
    

    public BoardWelcome() {
    	
        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 50);
        Font welcomeFont = Font.font("Press Start 2P", 50);
        welcomeText.setFont(welcomeFont);
        welcomeText.setFill(Color.WHITE);
        
		Scene scene = controller.Main.getScene();
		this.setPrefHeight(scene.getHeight());
		this.setPrefWidth(scene.getWidth());
		Double prefHeight = this.getPrefHeight();
		Double prefWidth = this.getPrefWidth();
		
    	this.setAlignment(Pos.TOP_CENTER);
    	
    	welcomeText.setText("Welcome!");
    	BoardWelcome.setMargin(welcomeText, new Insets(prefHeight * 0.25, 0, 0, 0));
    	this.getChildren().add(welcomeText); 
		
    	createProfileButton(prefHeight, "New Player", 0.0, "NEW_PLAYER");

    	if (!isEmpty()) { 
    		createProfileButton(prefHeight, "Existing Player", 1.5, "EXISTING_PLAYER");
    	}
        
    }
    
    
	private void createProfileButton(Double prefHeight, String text, Double spacing, String id) {
    	Button profileButton = new Button();
    	profileButton.setPrefWidth(PROFILE_BUTTON_WIDTH);
    	profileButton.setPrefHeight(PROFILE_BUTTON_HEIGHT);
    	
    	
        Font profileFont = Font.font("Press Start 2P", 15);
        profileButton.setFont(profileFont);
    	profileButton.setText(text);
    	
    	profileButton.setId(id);
    	
    	BoardWelcome.setMargin(profileButton, new Insets(prefHeight * 0.6 + PROFILE_BUTTON_HEIGHT * spacing, 0, 0, 0));  
    	this.getChildren().add(profileButton);
	}
    

	/**
	 * Checks if there are existing player profiles
	 * @return true if there are no player profiles, false otherwise
	 */
	private boolean isEmpty() {
		try (Stream<Path> entries = Files.list(PLAYER_PROFILE_FOLDER)) {
			return !entries.findFirst().isPresent();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
