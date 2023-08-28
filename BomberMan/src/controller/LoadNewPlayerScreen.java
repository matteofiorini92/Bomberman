package controller;

import view.BaseGroup;

public class LoadNewPlayerScreen {
	public LoadNewPlayerScreen() {
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		baseGroup.getChildren().removeAll(baseGroup.getChildren());
		baseGroup.getChildren().add(new view.BoardNewProfile());
	}
	
}
