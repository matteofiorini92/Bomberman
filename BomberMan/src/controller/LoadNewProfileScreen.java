package controller;

import javafx.scene.Node;
import view.BaseGroup;

public class LoadNewProfileScreen {
	public LoadNewProfileScreen() {
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		baseGroup.getChildren().removeAll(baseGroup.getChildren());
		baseGroup.getChildren().add(new view.BoardNewProfile());
		
		Node startNewGame = baseGroup.lookup("#NEW_GAME");
		startNewGame.setDisable(true);
		
		Node saveProfile = baseGroup.lookup("#SAVE_PROFILE");
		saveProfile.setOnMouseClicked(event -> new controller.SaveNewProfile());
	}
}
