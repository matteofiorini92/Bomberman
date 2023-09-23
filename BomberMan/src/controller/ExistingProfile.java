package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import view.BaseGroup;

/**
 * manage an existing profile
 * @author Matteo
 *
 */
public abstract class ExistingProfile {
	
	/**
	 * load the existing profile screen
	 */
	public static void load(String nickname) {
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		
		if (!nickname.equals("")) {
			
			File existingProfile = new File("resources/playerProfiles/" + nickname.toLowerCase() + ".txt");
			try {
				
				String[] playerStats = Files.readString(existingProfile.toPath()).split("\\s+");
				// 0 > avatar color
				// 1 > wins
				// 2 > losses
				// 3 > total score
				
				baseGroup.getChildren().removeAll(baseGroup.getChildren());
				model.Player player = model.Player.getInstance();
				player.setName(nickname);
				player.setAvatar(model.Avatar.valueOf(playerStats[0]));
				player.setWins(Integer.parseInt(playerStats[1]));
				player.setLosses(Integer.parseInt(playerStats[2]));
				player.setScore(Integer.parseInt(playerStats[3]));
				
				
				
				view.Boards.ExistingProfile existingProfileBoard = new view.Boards.ExistingProfile(
						Integer.parseInt(playerStats[1]),
						Integer.parseInt(playerStats[2]),
						Integer.parseInt(playerStats[3]),
						nickname,
						model.Avatar.valueOf(playerStats[0])
						);
				baseGroup.getChildren().add(existingProfileBoard);
				
				Node saveProfile = baseGroup.lookup("#SAVE_PROFILE");
				saveProfile.setOnMouseClicked(event -> ExistingProfile.save());
				
			} catch (IOException e) {
				baseGroup.lookup("#ERR_NICKNAME_DOES_NOT_EXIST").setVisible(true);
			}
		}
	}
	
	
	/**
	 * save changes to the existing profile
	 */
	
	public static void save() {
		
		view.BaseGroup baseGroup = view.BaseGroup.getInstance();
		
		String nickname = ((TextField) baseGroup.lookup("#NICKNAME")).getText().toLowerCase();
		
		Button selectedAvatar = (Button) baseGroup.lookup("#SELECTED_AVATAR");
		String selectedAvatarText = selectedAvatar.getUserData().toString().toUpperCase();
		
		model.Player player = model.Player.getInstance();

		
		try {
			
			File existingProfile = new File("resources/playerProfiles/" + player.getName() + ".txt");
			String[] playerStats = Files.readString(existingProfile.toPath()).split("\\s+");
			playerStats[0] = selectedAvatarText;
			
			FileWriter fileWriter = new FileWriter(existingProfile);
			fileWriter.write(
				selectedAvatarText + " " +
				player.getWins() + " " +
				player.getLosses() + " " +
				player.getScore()
			);
			
			fileWriter.close();

			existingProfile.renameTo(new File("resources/playerProfiles/" + nickname + ".txt"));
			
			player.setName(nickname);
			player.setAvatar(model.Avatar.valueOf(selectedAvatarText));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * save the updated stats of the player in the relevant file
	 */
	public static void updateStats() {
		model.Player player = model.Player.getInstance();
		try {
			
			File existingProfile = new File("resources/playerProfiles/" + player.getName() + ".txt");
			
			FileWriter fileWriter = new FileWriter(existingProfile);
			fileWriter.write(
				player.getAvatar().toString() + " " +
				player.getWins() + " " +
				player.getLosses() + " " +
				player.getScore()
			);
			
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
