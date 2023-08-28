package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SaveExistingProfile {
	
	public SaveExistingProfile() {
		
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

}
