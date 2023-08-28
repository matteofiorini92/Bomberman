package view;

import javafx.scene.Node;

public class BoardExistingProfile extends BoardProfile {

	public BoardExistingProfile(int wins, int losses, int totalScore, String nickname, model.Avatar avatar)
	{
		super(wins, losses, totalScore, nickname, avatar);
		view.BaseGroup baseGroup = view.BaseGroup.getInstance();
		Node nicknameTextField = baseGroup.lookup("NICKNAME");
		
		nicknameTextField.setDisable(true);
		
		Node saveProfile = baseGroup.lookup("SAVE_PROFILE");
		saveProfile.setOnMouseClicked(event -> new controller.SaveExistingProfile());
	}

}
