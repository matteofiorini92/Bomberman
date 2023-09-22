package view.Boards;

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
import view.BaseScene;

/**
 * welcome board to select existing or new player
 * @author Matteo
 *
 */
public class Welcome extends StackPane {
	
    public static final Path PLAYER_PROFILE_FOLDER = Path.of("resources/playerProfiles");
    public static final double PROFILE_BUTTON_HEIGHT = 50.0;
    public static final double PROFILE_BUTTON_WIDTH = 300.0;
    
    

    public Welcome() {
    	
    	
    	/**
    	 *  big welcome greeting
    	 */
    	Text welcomeText = new Text();
        Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"), 50);
        Font welcomeFont = Font.font("Press Start 2P", 50);
        welcomeText.setFont(welcomeFont);
        welcomeText.setFill(Color.WHITE);
        
		Scene scene = BaseScene.getInstance();
		this.setPrefHeight(scene.getHeight());
		this.setPrefWidth(scene.getWidth());
		Double prefHeight = this.getPrefHeight();
		
    	this.setAlignment(Pos.TOP_CENTER);
    	
    	welcomeText.setText("Welcome!");
    	Welcome.setMargin(welcomeText, new Insets(prefHeight * 0.25, 0, 0, 0));
    	this.getChildren().add(welcomeText); 
		
    	
    	/**
    	 * buttons
    	 * new player always
    	 * existing player only if player profiles already exist
    	 */
    	createProfileButton(prefHeight, "New Player", 0.0, "NEW_PLAYER", controller.NewProfile::load);

    	if (existingPlayers()) { 
    		createProfileButton(prefHeight, "Existing Player", 1.5, "EXISTING_PLAYER", controller.ProfileLookUp::load);
    	}
        
    }
    
    
	private void createProfileButton(Double prefHeight, String text, Double spacing, String id, Runnable runnable) {
    	Button profileButton = new Button();
    	profileButton.setPrefWidth(PROFILE_BUTTON_WIDTH);
    	profileButton.setPrefHeight(PROFILE_BUTTON_HEIGHT);
    	
    	
        Font profileFont = Font.font("Press Start 2P", 15);
        profileButton.setFont(profileFont);
    	profileButton.setText(text);
    	
    	profileButton.setId(id);
    	
    	Welcome.setMargin(profileButton, new Insets(prefHeight * 0.6 + PROFILE_BUTTON_HEIGHT * spacing, 0, 0, 0));  
    	this.getChildren().add(profileButton);
    	
    	profileButton.setOnMouseClicked(event -> runnable.run());
	}
    

	/**
	 * Checks if there are existing player profiles
	 * @return true if there are player profiles, false otherwise
	 */
	private boolean existingPlayers() {
		try (Stream<Path> entries = Files.list(PLAYER_PROFILE_FOLDER)) {
			return entries.findFirst().isPresent();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
