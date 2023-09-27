package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import view.BaseGroup;

/**
 * manage a new profile
 * @author Matteo
 */
public abstract class NewProfile {
	
	
	/**
	 * load the new profile screen
	 */
	public static void load() {
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		baseGroup.getChildren().removeAll(baseGroup.getChildren());
		baseGroup.getChildren().add(new view.boards.NewProfile());
		
		Node startNewGame = baseGroup.lookup("#NEW_GAME");
		startNewGame.setDisable(true);
		
		Node saveProfile = baseGroup.lookup("#SAVE_PROFILE");
		saveProfile.setOnMouseClicked(event -> NewProfile.save());
		
	}
	
	/**
	 * save the new profile if nickname is not already in use
	 */
	public static void save() {
		view.BaseGroup baseGroup = view.BaseGroup.getInstance();
		
		// get nickname from newProfileBoard
		String nickname = ((TextField) baseGroup.lookup("#NICKNAME")).getText().toLowerCase();
		
		if (!nickname.equals("")) {
			Object[] alreadyExisting = {};
			
			// check existing files and filter by nickname selected by user
			try (Stream<Path> stream = Files.list(Path.of("resources/playerProfiles"))) {
				alreadyExisting = stream
						.filter(file -> file.getFileName().toString().equals(nickname + ".txt"))
						.toArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			// if nickname already exists display an error
			if (alreadyExisting.length > 0) {
				baseGroup.lookup("#ERR_NICKNAME_TAKEN").setVisible(true);
			}
			
			// else create new player instance and new playerProfile file and activate new game button
			else {
				baseGroup.lookup("#ERR_NICKNAME_TAKEN").setVisible(false);
				baseGroup.lookup("#NEW_GAME").setDisable(false);
				model.Player player = model.Player.getInstance();
				player.setName(nickname);
				Button selectedAvatar = (Button) baseGroup.lookup("#SELECTED_AVATAR");
				String selectedAvatarText = selectedAvatar.getUserData().toString().toUpperCase();
				player.setAvatar(model.Avatar.valueOf(selectedAvatarText));
				
				
				
				File playerProfile = new File("resources/playerProfiles/" + nickname + ".txt");
				try {
					playerProfile.createNewFile();
					FileWriter fileWriter = new FileWriter(playerProfile);
					fileWriter.write(
							player.getAvatar() + " " +
									player.getWins() + " " +
									player.getLosses() + " " +
									player.getScore()
							);
					fileWriter.close();
					
					// SHOULD BECOME AN EXISTING PROFILE ENTITY AT THIS POINT
					baseGroup.lookup("#SAVE_PROFILE").setOnMouseClicked(event -> ExistingProfile.save());
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
