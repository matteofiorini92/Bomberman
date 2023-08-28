package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import javafx.scene.Node;
import view.BaseGroup;

public class LoadExistingProfileScreen {
	public LoadExistingProfileScreen(String nickname) {
		
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
								
				
				
				view.BoardExistingProfile existingProfileBoard = new view.BoardExistingProfile(
						Integer.parseInt(playerStats[1]),
						Integer.parseInt(playerStats[2]),
						Integer.parseInt(playerStats[3]),
						nickname,
						model.Avatar.valueOf(playerStats[0])
						);
				baseGroup.getChildren().add(existingProfileBoard);
				
				Node saveProfile = baseGroup.lookup("#SAVE_PROFILE");
				saveProfile.setOnMouseClicked(event -> new controller.SaveExistingProfile());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				baseGroup.lookup("#ERR_NICKNAME_DOES_NOT_EXIST").setVisible(true);
			}
		}
	}
}
