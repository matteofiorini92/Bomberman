package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import view.BaseGroup;

public class LoadExistingProfileScreen {
	public LoadExistingProfileScreen(String nickname) {
		
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		
		if (!nickname.equals("")) {
			Path[] existing = {};

			// check existing files and filter by nickname selected by user
			try (Stream<Path> stream = Files.list(Path.of("resources/playerProfiles"))) {
				existing = stream
						.filter(file -> file.getFileName().toString().equals(nickname + ".txt"))
						.toArray(Path[]::new);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// if nickname doesn't exist >> ERROR
			if (existing.length == 0) {
				baseGroup.lookup("#ERR_NICKNAME_DOES_NOT_EXIST").setVisible(true);
			}
			
			// else load player's information
			else {
				try {
//					baseGroup.lookup("#ERR_NICKNAME_DOES_NOT_EXIST").setVisible(false);
					baseGroup.getChildren().removeAll(baseGroup.getChildren());
					String[] playerStats = Files.readString(existing[0]).split("\\s+");
					// 0 > avatar color
					// 1 > wins
					// 2 > losses
					// 3 > total score
					view.BoardExistingProfile newProfileBoard = new view.BoardExistingProfile(
							Integer.parseInt(playerStats[1]),
							Integer.parseInt(playerStats[2]),
							Integer.parseInt(playerStats[3]),
							nickname,
							model.Avatar.valueOf(playerStats[0])
							);
					baseGroup.getChildren().add(newProfileBoard);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
