package controller;

import view.BaseGroup;

public class LoadNewProfileScreen {
	public LoadNewProfileScreen() {
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		baseGroup.getChildren().removeAll(baseGroup.getChildren());
		baseGroup.getChildren().add(new view.BoardNewProfile());
	}
}
