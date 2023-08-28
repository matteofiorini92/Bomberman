package view;

import javafx.scene.Node;

public class BoardNewProfile extends BoardProfile {
	
	public BoardNewProfile() {
		super(0, 0, 0, null, model.Avatar.WHITE);
		
		view.BaseGroup baseGroup = view.BaseGroup.getInstance();
		Node startNewGame = baseGroup.lookup("NEW_GAME");
		startNewGame.setDisable(true);
		
		Node saveProfile = baseGroup.lookup("SAVE_PROFILE");
		saveProfile.setOnMouseClicked(event -> new controller.SaveNewProfile());
	}
}
